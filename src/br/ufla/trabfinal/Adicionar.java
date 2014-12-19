package br.ufla.trabfinal;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

public class Adicionar extends Activity {
	private Button home;
	private Button add;
	private Button cancelar;
	private Button salvar;
	private Button search;
	private Button foto;
	private EditText item;
	private EditText valor;
	private EditText data;
	private RatingBar nota;
	private ImageView img;
	private ByteArrayOutputStream stream;
	private byte[] byteArray;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adicionar);
		home = (Button) findViewById(R.id.btnHome);
        add = (Button) findViewById(R.id.btnAdd);
        search = (Button) findViewById(R.id.btnSearch);
        salvar = (Button) findViewById(R.id.btnSAVE);
        cancelar = (Button) findViewById(R.id.btnCANCEL);
        foto = (Button) findViewById(R.id.btnCAMERA);
        item = (EditText) findViewById(R.id.fieldITEM);
        valor = (EditText) findViewById(R.id.fieldVALOR);
        data = (EditText) findViewById(R.id.fieldDATA);
        nota = (RatingBar) findViewById(R.id.ratingBar1);
        img = (ImageView) findViewById(R.id.imageViewAdicionar);
        
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
				limparCampos(item, valor, data, nota, img);
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
							String.valueOf(nota.getRating()), byteArray);
					Toast.makeText(Adicionar.this, R.string.Success,Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					Toast.makeText(Adicionar.this, R.string.Fail,Toast.LENGTH_LONG).show();
				}
				limparCampos(item, valor, data, nota, img);
			}
		});
        
        //Ao clicar no botão foto
        foto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent pic = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(pic, 0);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null){
			Bundle bundle = data.getExtras();
			if (bundle != null){
				Bitmap bitmap = (Bitmap) bundle.get("data");
				img.setImageBitmap(bitmap);
				
				 stream = new ByteArrayOutputStream();
				 bitmap.compress(CompressFormat.JPEG, 70, stream);
				 byteArray = stream.toByteArray();
			}
		}
	}
	
	private void limparCampos(EditText t1,EditText t2,EditText t3, RatingBar rt, ImageView i1){
		t1.setText("");
		t2.setText("");
		t3.setText("");
		rt.setRating(0);
		i1.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
	}
}