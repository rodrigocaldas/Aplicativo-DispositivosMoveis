package br.ufla.trabfinal;

import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class Adicionar extends Activity {
	final int TIRAR_FOTO =1;
	Button home;
	Button add;
	Button cancelar;
	Button salvar;
	Button search;
	EditText item;
	EditText valor;
	EditText data;
	RatingBar nota;
	TextToSpeech tts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adicionar);
		home = (Button) findViewById(R.id.btnHome);
        add = (Button) findViewById(R.id.btnAdd);
        search = (Button) findViewById(R.id.btnSearch);
        salvar = (Button) findViewById(R.id.btnSAVE);
        cancelar = (Button) findViewById(R.id.btnCANCEL);
        item = (EditText) findViewById(R.id.fieldITEM);
        valor = (EditText) findViewById(R.id.fieldVALOR);
        data = (EditText) findViewById(R.id.fieldDATA);
        nota = (RatingBar) findViewById(R.id.ratingBar1);

        //TextToSpeech
        tts = new TextToSpeech(this, new OnInitListener() {
			@Override
			public void onInit(int status) {
				tts.setLanguage(Locale.getDefault());
			}
		});
        
        //Ao clicar no botão Home
        home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Adicionar.this, Inicio.class);
				startActivity(i);
				finish();
			}
		});
        
        //Ao clicar no botão Search
        search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Adicionar.this, Buscar.class);
				startActivity(i);
				finish();
				
			}
		});
        
      //Ao clicar no botão Cancelar
        cancelar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				limparCampos(item, valor, data, nota);
			}
		});
        
      //Ao clicar no botão Salvar
        salvar.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View v) {
				try {
					DataBaseOperations dbo= new DataBaseOperations(Adicionar.this);
					dbo.persistirInfo(dbo, item.getText().toString(), 
							valor.getText().toString(), data.getText().toString(),
							String.valueOf(nota.getRating()));
					Toast.makeText(Adicionar.this, R.string.Success,Toast.LENGTH_LONG).show();
					tts.speak(getResources().getString(R.string.Success), TextToSpeech.QUEUE_FLUSH, null);
				} catch (Exception e) {
					Toast.makeText(Adicionar.this, R.string.Fail,Toast.LENGTH_LONG).show();
					tts.speak(getResources().getString(R.string.Fail), TextToSpeech.QUEUE_FLUSH, null);
				}
				limparCampos(item, valor, data, nota);
			}
		});
	}
	
	private void limparCampos(EditText t1,EditText t2,EditText t3, RatingBar rt){
		t1.setText("");
		t2.setText("");
		t3.setText("");
		rt.setRating(0);
	}
}