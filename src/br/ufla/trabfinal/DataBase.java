package br.ufla.trabfinal;

import android.provider.BaseColumns;

public class DataBase {
	
	public DataBase(){
		
	}
	
	public static abstract class Table implements BaseColumns{
		public static final String ITEM = "item";
		public static final String VALOR = "valor";
		public static final String DMA = "dma";
		public static final String NOTA = "nota";
		public static final String DATABASENAME = "colecao";
		public static final String TABLENAME = "cerva";
	}

}
