package br.ufla.JaBebi;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import br.ufla.trabfinal.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TableRow;
import android.widget.Toast;

public class Informacoes extends Activity implements OnDateSetListener {
	private EditText itemPassado;
	private EditText precoPassado;
	private EditText dataPassada;
	private RatingBar notaPassada;
	private Button voltar;
	private Button editar;
	private Button excluir;
	private Button cancelar;
	private Button confirmar;
	private Button foto;
	private TableRow primeiro;
	private TableRow segundo;
	private ImageView img;
	private byte[] byteArray;
	private byte[] byteArrayAtual = null;
	private Cursor cr;
	private ByteArrayOutputStream stream;
	
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
		foto = (Button) findViewById(R.id.btnCAMERA);
		primeiro = (TableRow) findViewById(R.id.rowEsconder);
		segundo = (TableRow) findViewById(R.id.rowMostrar);
		img = (ImageView) findViewById(R.id.imageViewInformacoes);
		
		Intent it = getIntent();
        final Bundle params = it.getExtras();
        
        preencheCamposPassados(params, itemPassado, precoPassado, dataPassada, notaPassada, img);
        
        //Ao Clicar no botão Voltar OK COMPLETO
        voltar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Informacoes.this, Buscar.class);
				Bundle paramsBuscar = new Bundle();
				paramsBuscar.putString("lista", params.getString("listagem"));
				i.putExtras(paramsBuscar);
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
				dataPassada.setEnabled(true);
				notaPassada.setIsIndicator(false);
				foto.setEnabled(true);
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
							Intent i = new Intent(Informacoes.this, Buscar.class);
							Bundle paramsBuscar = new Bundle();
							paramsBuscar.putString("lista", params.getString("listagem"));
							i.putExtras(paramsBuscar);
							startActivity(i);
							finish();
						} catch (Exception e) {
							Toast.makeText(Informacoes.this, R.string.Fail3, Toast.LENGTH_LONG).show();
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
				dataPassada.setEnabled(false);
				notaPassada.setIsIndicator(true);
				foto.setEnabled(false);
				preencheCamposPassados(params, itemPassado, precoPassado, dataPassada, notaPassada, img);
				primeiro.setVisibility(View.VISIBLE);
				segundo.setVisibility(View.INVISIBLE);
			}
		});
        
        //Ao Clicar no botão Confirmar - OK COMPLETO
        confirmar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					DataBaseOperations dbo= new DataBaseOperations(Informacoes.this);
					if(byteArrayAtual != null){
						dbo.editarInfo2(dbo, cr.getString(0), 
								itemPassado.getText().toString(),
								Float.parseFloat(precoPassado.getText().toString()),
								dataPassada.getText().toString(),
								String.valueOf(notaPassada.getRating()),
								byteArrayAtual);
					}
					else{
						dbo.editarInfo(dbo, cr.getString(0), 
								itemPassado.getText().toString(),
								Float.parseFloat(precoPassado.getText().toString()),
								dataPassada.getText().toString(),
								String.valueOf(notaPassada.getRating()));
					}
					
					Toast.makeText(Informacoes.this, R.string.Success2, Toast.LENGTH_LONG).show();
					Intent i = new Intent(Informacoes.this, Buscar.class);
					Bundle paramsBuscar = new Bundle();
					paramsBuscar.putString("lista", params.getString("listagem"));
					i.putExtras(paramsBuscar);
					startActivity(i);
					finish();
					
				} catch (Exception e) {
					Toast.makeText(Informacoes.this, R.string.Fail2, Toast.LENGTH_LONG).show();
				}
				
			}
		});
        
        //Ao clicar no botão foto - OK COMPLETO
        foto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent pic = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(pic, 0);
			}
		});
        
        //Ao clicar no dataPassada - OK COMPLETO
        dataPassada.setOnClickListener(new OnClickListener() {
    	   	@Override
			public void onClick(View v) {
    	   		
    	   		final Calendar c = Calendar.getInstance();
    	   		int ano = c.get(Calendar.YEAR);
    	   	    int mes = c.get(Calendar.MONTH);
    	   	    int dia = c.get(Calendar.DAY_OF_MONTH);
    	   		DatePickerDialog dialog = new DatePickerDialog(Informacoes.this, Informacoes.this, ano, mes, dia);
    	        dialog.show();
			}
       	});
	}
	
	@Override
	public void onDateSet(DatePicker view, int ano, int mes,
			int dia) {
		dataPassada.setText(ano+"."+mes+"."+dia);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null){
			Bundle bundle = data.getExtras();
			if (bundle != null){
				Bitmap bitmapAtual = (Bitmap) bundle.get("data");
				img.setImageBitmap(bitmapAtual);
				
				 stream = new ByteArrayOutputStream();
				 bitmapAtual.compress(CompressFormat.JPEG, 70, stream);
				 byteArrayAtual = stream.toByteArray();
			}
		}
	}
	
	public void preencheCamposPassados(Bundle params, EditText e1, EditText e2, EditText e3, RatingBar r1, ImageView i1){
		DataBaseOperations dbo = new DataBaseOperations(Informacoes.this);
		switch (Integer.parseInt(params.getString("listagem"))) {
		case 1:
			cr = dbo.recuperarInfo1(dbo);
			break;
		case 2:
			cr = dbo.recuperarInfo2(dbo);
			break;
		case 3:
			cr = dbo.recuperarInfo3(dbo);
			break;
		}
		cr.moveToPosition(params.getInt("posicao"));
		e1.setText(cr.getString(0));
        e2.setText(cr.getString(1));
        e3.setText(cr.getString(2));
        float nota_aux = Float.parseFloat(cr.getString(3));
        r1.setRating(nota_aux);
        
        byteArray = cr.getBlob(4);
        if(byteArray != null){
        	Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray , 0, byteArray.length);
            i1.setImageBitmap(bitmap);
        }
        
	}
}