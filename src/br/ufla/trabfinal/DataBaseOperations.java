package br.ufla.trabfinal;

import br.ufla.trabfinal.DataBase.Table;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseOperations extends SQLiteOpenHelper {
	public static final int database_version = 1;
	public String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS "+Table.TABLENAME+"("+
			Table.ITEM+" Text, "+Table.VALOR+" TEXT, "+Table.DMA+" TEXT, "+
			Table.NOTA+" TEXT);";

	public DataBaseOperations(Context context) {
		super(context, Table.DATABASENAME, null, database_version);
		Log.d("Database operations","Database created");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {		
		db.execSQL(CREATE_QUERY);
		Log.d("Database operations","Table created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
	
	public void persistirInfo(DataBaseOperations dob,String item, String valor, String data, String nota) {
		SQLiteDatabase SQ = dob.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(Table.ITEM, item);
		cv.put(Table.VALOR, valor);
		cv.put(Table.DMA, data);
		cv.put(Table.NOTA, nota);
		SQ.insert(Table.TABLENAME, null, cv);
		Log.d("Database operations","One raw inserted");
	}
	
	public Cursor recuperarInfo(DataBaseOperations dop){
		SQLiteDatabase SQ = dop.getReadableDatabase();
		String[] colunas = {Table.ITEM, Table.VALOR, Table.DMA, Table.NOTA};
		Cursor CR = SQ.query(Table.TABLENAME, colunas, null, null, null, null, Table.ITEM);
		return CR;
	}
	
	public Cursor recuperarInfo2(DataBaseOperations dop){
		SQLiteDatabase SQ = dop.getReadableDatabase();
		String[] colunas = {Table.ITEM, Table.VALOR, Table.DMA, Table.NOTA};
		Cursor CR = SQ.query(Table.TABLENAME, colunas, null, null, null, null, null);
		return CR;
	}
	
	public void deletarInfo(DataBaseOperations dop, String[] argumentos){
		String selecao = Table.ITEM+" = ?";
		SQLiteDatabase SQ = dop.getWritableDatabase();
		SQ.delete(Table.TABLENAME, selecao, argumentos);
	}
	
	public void editarInfo(DataBaseOperations dob,String referencia, String item, String valor, String data, String nota){
		SQLiteDatabase SQ = dob.getWritableDatabase();
		String[] ref = {referencia};
		String selecao = Table.ITEM+" = ?";
		ContentValues cv = new ContentValues();
		cv.put(Table.ITEM, item);
		cv.put(Table.VALOR, valor);
		cv.put(Table.DMA, data);
		cv.put(Table.NOTA, nota);
		SQ.update(Table.TABLENAME, cv, selecao, ref);
	}
}