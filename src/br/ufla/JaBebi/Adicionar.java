package br.ufla.JaBebi;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import br.ufla.trabfinal.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

public class Adicionar extends Activity implements OnGestureListener, OnDateSetListener {
	private Button home;
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
	private GestureDetector detector = null;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adicionar);
		this.detector = new GestureDetector(this);
		home = (Button) findViewById(R.id.btnHome);
        search = (Button) findViewById(R.id.btnSearch);
        salvar = (Button) findViewById(R.id.btnSAVE);
        cancelar = (Button) findViewById(R.id.btnCANCEL);
        foto = (Button) findViewById(R.id.btnCAMERA);
        item = (EditText) findViewById(R.id.fieldITEM);
        valor = (EditText) findViewById(R.id.fieldVALOR);
        data = (EditText) findViewById(R.id.fieldDATA);
        nota = (RatingBar) findViewById(R.id.ratingBar1);
        img = (ImageView) findViewById(R.id.imageViewAdicionar);
        
        //Ao clicar no bot�o Home
        home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Adicionar.this, Inicio.class);
				startActivity(i);
				finish();
			}
		});
        
        //Ao clicar no bot�o Search
        search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Adicionar.this, Buscar.class);
				Bundle paramsBuscar = new Bundle();
				paramsBuscar.putString("lista", "1");
				i.putExtras(paramsBuscar);
				startActivity(i);
				finish();
				
			}
		});
        
      //Ao clicar no bot�o Cancelar
        cancelar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				limparCampos(item, valor, data, nota, img);
			}
		});
        
      //Ao clicar no bot�o Salvar
        salvar.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View v) {
        		if (item.getText().toString().equals("") || (valor.getText().toString().equals(""))){
        			Toast.makeText(Adicionar.this, R.string.alertEMPTY, Toast.LENGTH_LONG).show();
        		}
        		else{
					try {
						DataBaseOperations dbo= new DataBaseOperations(Adicionar.this);
						dbo.persistirInfo(dbo, 
								item.getText().toString(), 
								Float.parseFloat(valor.getText().toString()), 
								data.getText().toString(),
								String.valueOf(nota.getRating()), 
								byteArray);
						Toast.makeText(Adicionar.this, R.string.Success,Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						Toast.makeText(Adicionar.this, R.string.Fail,Toast.LENGTH_LONG).show();
					}
					limparCampos(item, valor, data, nota, img);
        		}
			}
		});
        
        //Ao clicar no bot�o foto
        foto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent pic = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(pic, 0);
			}
		});
        
       //Ao clicar em data
       data.setOnClickListener(new OnClickListener() {
    	   	@Override
			public void onClick(View v) {
    	   		
    	   		final Calendar c = Calendar.getInstance();
    	   		int ano = c.get(Calendar.YEAR);
    	   	    int mes = c.get(Calendar.MONTH);
    	   	    int dia = c.get(Calendar.DAY_OF_MONTH);
    	   		DatePickerDialog dialog = new DatePickerDialog(Adicionar.this, Adicionar.this, ano, mes, dia);
    	        dialog.show();
			}
       	});
	}
	
	@Override
	public void onDateSet(DatePicker view, int ano, int mes,
			int dia) {
		data.setText(ano+"."+mes+"."+dia);
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
	
	//A partir daqui � respons�vel pelo swipe
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(this.detector.onTouchEvent(event)) {
             return true;
        }
         return super.onTouchEvent(event);
    }

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (Math.abs(e1.getY() - e2.getY()) > 250) {
            return false;
		} 
		// Movimento da direita para esquerda
	    if(e1.getX() - e2.getX() > 100) {
	    	Intent i = new Intent(Adicionar.this, Buscar.class);
	    	Bundle paramsBuscar2 = new Bundle();
	    	paramsBuscar2.putString("lista", "1");
			i.putExtras(paramsBuscar2);
			startActivity(i);
			finish();
	    }
	    if (e2.getX() - e1.getX() > 100) {
	    	Intent i = new Intent(Adicionar.this, Inicio.class);
			startActivity(i);
			finish();         
	    }
		return true;
	}
}