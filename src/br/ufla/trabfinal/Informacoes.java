package br.ufla.trabfinal;

import java.util.Locale;

import br.ufla.trabfinal.R.string;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TableRow;
import android.widget.Toast;

public class Informacoes extends Activity {
	private EditText itemPassado;
	private EditText precoPassado;
	private EditText dataPassada;
	private RatingBar notaPassada;
	private Button voltar;
	private Button editar;
	private Button excluir;
	private Button cancelar;
	private Button confirmar;
	private TableRow primeiro;
	private TableRow segundo;
	TextToSpeech tts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_informacoes);
		itemPassado = (EditText) findViewById(R.id.fieldITEM2);
		precoPassado = (EditText) findViewById(R.id.fieldVALOR2);
		dataPassada = (EditText) findViewById(R.id.fieldDATA2);
		notaPassada = (RatingBar) findViewById(R.id.ratingBar2);
		voltar = (Button) findViewById(R.id.btnVOLTAR2);
		editar = (Button) findViewById(R.id.btnEDITAR);
		excluir = (Button) findViewById(R.id.btnEXCLUIR);
		cancelar = (Button) findViewById(R.id.btnCANCELAR);
		confirmar = (Button) findViewById(R.id.btnCONFIRMAR);
		primeiro = (TableRow) findViewById(R.id.rowEsconder);
		segundo = (TableRow) findViewById(R.id.rowMostrar);
		
		//TextToSpeech
        tts = new TextToSpeech(this, new OnInitListener() {
			@Override
			public void onInit(int status) {
				tts.setLanguage(Locale.getDefault());
			}
		});
		
		Intent it = getIntent();
        final Bundle params = it.getExtras();
        
        preencheCamposPassados(params, itemPassado, precoPassado, dataPassada, notaPassada);
        
        //Ao Clicar no botão Voltar OK COMPLETO
        voltar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Informacoes.this, Buscar.class);
				startActivity(i);
				finish();
			}
		});
        
      //Ao Clicar no botão Editar OK COMPLETO
        editar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				itemPassado.setFocusableInTouchMode(true);
				precoPassado.setFocusableInTouchMode(true);
				dataPassada.setFocusableInTouchMode(true);
				notaPassada.setIsIndicator(false);
				primeiro.setVisibility(View.INVISIBLE);
				segundo.setVisibility(View.VISIBLE);
			}
		});
        
      //Ao Clicar no botão Excluir OK COMPLETO
        excluir.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(Informacoes.this)
				.setTitle(R.string.alertTITLE)
				.setMessage(R.string.alertMSG)
				.setPositiveButton(R.string.alertYES, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							DataBaseOperations dbo = new DataBaseOperations(Informacoes.this);
							String[] argumentos = {itemPassado.getText().toString()};
							dbo.deletarInfo(dbo, argumentos);
							Toast.makeText(Informacoes.this, R.string.Success3, Toast.LENGTH_LONG).show();
							tts.speak(getResources().getString(R.string.Success3), TextToSpeech.QUEUE_FLUSH, null);
							do {
								//espera a fala terminar para encerrar a atividade.
							} while (tts.isSpeaking());
							Intent i = new Intent(Informacoes.this, Buscar.class);
							startActivity(i);
							finish();
						} catch (Exception e) {
							Toast.makeText(Informacoes.this, R.string.Fail3, Toast.LENGTH_LONG).show();
							tts.speak(getResources().getString(R.string.Fail3), TextToSpeech.QUEUE_FLUSH, null);
						}
					}
				})
				.setNegativeButton(R.string.alertNo, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Faz nada, somente fecha o AlertDialog
					}
				})
				.setIcon(android.R.drawable.ic_dialog_alert)
				.show();
			}
		});
        
        
      //Ao Clicar no botão Cancelar - OK COMPLETO
        cancelar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				itemPassado.setFocusableInTouchMode(false);
				itemPassado.setFocusable(false);
				precoPassado.setFocusableInTouchMode(false);
				precoPassado.setFocusable(false);
				dataPassada.setFocusableInTouchMode(false);
				dataPassada.setFocusable(false);
				notaPassada.setIsIndicator(true);
				preencheCamposPassados(params, itemPassado, precoPassado, dataPassada, notaPassada);
				primeiro.setVisibility(View.VISIBLE);
				segundo.setVisibility(View.INVISIBLE);
			}
		});
        
        //Ao Clicar no botão Confirmar -
        confirmar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					DataBaseOperations dbo= new DataBaseOperations(Informacoes.this);
					dbo.editarInfo(dbo, params.getString("item"), 
							itemPassado.getText().toString(),
							precoPassado.getText().toString(),
							dataPassada.getText().toString(),
							String.valueOf(notaPassada.getRating()));
					Toast.makeText(Informacoes.this, R.string.Success2, Toast.LENGTH_LONG).show();
					tts.speak(getResources().getString(R.string.Success2), TextToSpeech.QUEUE_FLUSH, null);
					do {
						//espera a fala terminar para encerrar a atividade.
					} while (tts.isSpeaking());
					Intent i = new Intent(Informacoes.this, Buscar.class);
					startActivity(i);
					finish();
					
				} catch (Exception e) {
					Toast.makeText(Informacoes.this, R.string.Fail2, Toast.LENGTH_LONG).show();
					tts.speak(getResources().getString(R.string.Fail2), TextToSpeech.QUEUE_FLUSH, null);
				}
				
			}
		});
        
	}
	
	public void preencheCamposPassados(Bundle params, EditText e1, EditText e2, EditText e3, RatingBar r1){
		e1.setText(params.getString("item"));
        e2.setText(params.getString("preco"));
        e3.setText(params.getString("data"));
        float nota_aux = Float.parseFloat(params.getString("nota"));
        r1.setRating(nota_aux);
	}
}