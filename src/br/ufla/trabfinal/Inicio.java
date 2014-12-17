package br.ufla.trabfinal;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class Inicio extends Activity{
	private Button add;
	private Button search;
	private ImageButton help;
	private TextView var1;
	private TextView var2;
	private Cursor cr;
	private int tamanho;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        add = (Button) findViewById(R.id.btnAdd);
        search = (Button) findViewById(R.id.btnSearch);
        help = (ImageButton) findViewById(R.id.imageButton1);
        var1 = (TextView) findViewById(R.id.variavel1);
        var2 = (TextView) findViewById(R.id.variavel2);
        
        
        DataBaseOperations dbo = new DataBaseOperations(Inicio.this);
		cr = dbo.recuperarInfo2(dbo);
		tamanho = cr.getCount();
		if (tamanho > 0){
			cr.moveToLast();
			var1.setText(cr.getString(0));
			var2.setText(String.valueOf(tamanho) + " itens");
		}
        
        //Ao clicar no botão Add
        add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Inicio.this, Adicionar.class);
				startActivity(i);
				finish();
			}
		});
        
        //Ao clicar no botão Search
        search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Inicio.this, Buscar.class);
				startActivity(i);
				finish();
			}
		});
        
        //Ao clicar no botão Help
        help.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Inicio.this, Ajuda.class);
				startActivity(i);
				finish();
			}
		});
    }
}