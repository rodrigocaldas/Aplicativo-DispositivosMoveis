package br.ufla.trabfinal;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Buscar extends Activity {
	private Button home;
	private Button add;
	private ListView lv;
	private Cursor cr;
	private String[] preenchida;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar);
		home = (Button) findViewById(R.id.btnHome);
        add = (Button) findViewById(R.id.btnAdd);
        lv = (ListView) findViewById(R.id.listView1);
        
        DataBaseOperations dbo = new DataBaseOperations(Buscar.this);
		cr = dbo.recuperarInfo(dbo);
		if (cr.getCount() > 0){
			preenchida = cursorToArray(cr);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2,android.R.id.text2, preenchida);
        	lv.setAdapter(adapter);
		}
		else{
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
        
        //Ao clicar no listView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(Buscar.this, Informacoes.class);
				cr.moveToPosition(position);
				Bundle params = new Bundle();
                params.putString("item", cr.getString(0));
                params.putString("preco", cr.getString(1));
                params.putString("data", cr.getString(2));
                params.putString("nota", cr.getString(3));
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

}