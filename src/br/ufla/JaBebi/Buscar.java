package br.ufla.JaBebi;

import java.util.ArrayList;

import br.ufla.trabfinal.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Buscar extends Activity implements OnGestureListener {
	private Button home;
	private Button add;
	private Button listarNome;
	private Button listarPreco;
	private Button listarData;
	private ListView lv;
	private Cursor cr;
	private String[] preenchida;
	private GestureDetector detector = null;
	private int _id = 1;
	private DataBaseOperations dbo = new DataBaseOperations(Buscar.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar);
		this.detector = new GestureDetector(this);
		home = (Button) findViewById(R.id.btnHome);
        add = (Button) findViewById(R.id.btnAdd);
        listarNome = (Button) findViewById(R.id.btnNOME);
        listarPreco = (Button) findViewById(R.id.btnPRECO);
        listarData= (Button) findViewById(R.id.btnDATA);
        lv = (ListView) findViewById(R.id.listView1);
        
        
		cr = dbo.recuperarInfo1(dbo);
		if (cr.getCount() > 0){
			preenchida = cursorToArray(cr);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2,android.R.id.text2, preenchida);
        	lv.setAdapter(adapter);
        	listarNome.setEnabled(true);
        	listarPreco.setEnabled(true);
        	listarData.setEnabled(true);
		}
		else{
			Toast.makeText(Buscar.this, R.string.txtMSG, Toast.LENGTH_SHORT).show();
		}
        
        //Ao clicar no botão Home
        home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Buscar.this, Inicio.class);
				startActivity(i);
				cr.close();
				finish();
			}
		});
        
        //Ao clicar no botão Search
        add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Buscar.this, Adicionar.class);
				startActivity(i);
				cr.close();
				finish();
				
			}
		});
        
        //Ao clicar no botão Nome
        listarNome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_id = 1;
				preencherLista(_id);
			}
		});
        
        //Ao clicar no botão Preco
        listarPreco.setOnClickListener(new OnClickListener() {
			@SuppressLint("ResourceAsColor") @Override
			public void onClick(View v) {
				_id = 2;
				preencherLista(_id);
			}
		});
        
        //Ao clicar no botão Data
        listarData.setOnClickListener(new OnClickListener() {
			@SuppressLint("ResourceAsColor") @Override
			public void onClick(View v) {
				_id = 3;
				preencherLista(_id);
			}
		});
        
        //Ao clicar no listView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(Buscar.this, Informacoes.class);
				Bundle params = new Bundle();
				params.putInt("listagem", _id);
				params.putInt("posicao", position);
                i.putExtras(params);
				startActivity(i);
				cr.close();
				finish();				
			}
		});
	}
	
	private String[] cursorToArray(Cursor cr){
		ArrayList<String> listaBd = new ArrayList<String>();
		cr.moveToFirst();
		for (int i = 0; i < cr.getCount(); i++){
			listaBd.add(cr.getString(0));
			cr.moveToNext();
		}		
		return listaBd.toArray(new String[listaBd.size()]);
	}
	
	private void preencherLista(int id){
		switch (id) {
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
		preenchida = cursorToArray(cr);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(Buscar.this,android.R.layout.simple_list_item_2,android.R.id.text2, preenchida);
    	lv.setAdapter(adapter);
	}
	
	//A partir daqui é responsável pelo swipe
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
	    	// faz nada.
	    }
	    if (e2.getX() - e1.getX() > 100) {
	    	Intent i = new Intent(Buscar.this, Adicionar.class);
			startActivity(i);
			finish();       
	    }
		return true;
	}

}