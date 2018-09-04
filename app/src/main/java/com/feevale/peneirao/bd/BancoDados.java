package com.feevale.peneirao.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.feevale.peneirao.Usuario;

import java.util.ArrayList;

public class BancoDados<T extends IPersistente> {
    private final Context ctx;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Class<T> tipoClasse;

    public BancoDados(Context ctx, Class<T> tipoClasse) throws Exception {
        // TODO Auto-generated constructor stub
        this.ctx = ctx;
        this.tipoClasse = tipoClasse;
        dbHelper = new DBHelper(ctx, "Peneirao", null, 15, criarInstancia());
    }

    public BancoDados<T> abrir(){
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void fechar(){
        db.close();
    }

    private T criarInstancia() throws Exception  {
        return tipoClasse.newInstance();
    }

    public int inserir(T pPersistente){
        abrir();
        ContentValues valores = pPersistente.inserir();
        int resultado = (int) db.insert(pPersistente.getNomeTabela(), null, valores);
        fechar();
        return resultado;
    }

    public IPersistente obter(int pCodigo) throws Exception{
        T persistente = criarInstancia();
        Cursor resultados = db.query(persistente.getNomeTabela(), persistente.colunas(),
                "Codigo = " + pCodigo, null, null, null, "CODIGO");
        persistente.carregar(resultados);

        return persistente;
    }
    public IPersistente obter(String pFiltro, String[] pValores) throws Exception{
        abrir();
        T persistente = criarInstancia();
        Cursor resultados = db.query(persistente.getNomeTabela(), persistente.colunas(),
                pFiltro, pValores, null, null, "CODIGO");
        persistente.carregar(resultados);
        fechar();
        return persistente.getCodigo() != 0 ? persistente : null;
    }
    public ArrayList<T> obter() throws Exception {

        ArrayList<T> lista = new ArrayList<T>();
        T persistente = criarInstancia();
        Cursor resultados = db.query(persistente.getNomeTabela(), persistente.colunas(), null, null, null, null, "CODIGO");
        if(resultados.getCount() > 0){
            resultados.moveToFirst();
            do{
                persistente = criarInstancia();
                persistente.carregar(resultados);

                lista.add(persistente);
            }while(resultados.moveToNext());
        }

        return lista;
    }

    public int editar(int pCodigo, T pPersistente){
        return db.update(pPersistente.getNomeTabela(), pPersistente.editar(), "CODIGO = " + pCodigo, null);
    }

    public int removerTodos() throws Exception {
        T persistente = criarInstancia();
        return db.delete(persistente.getNomeTabela(), null, null);
    }

    public int remover(int pCodigo) throws Exception {
        T persistente = criarInstancia();
        return db.delete(persistente.getNomeTabela(), "CODIGO = " + pCodigo, null);
    }

    private static class DBHelper extends SQLiteOpenHelper {

        IPersistente iPersistente;

        public DBHelper(Context context, String name,
                                SQLiteDatabase.CursorFactory factory, int version, IPersistente iPersistente) {
            super(context, name, factory, version);
            // TODO Auto-generated constructor stub

            this.iPersistente=iPersistente;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            Log.d("DATABASE","onCreate!");
            db.execSQL("create table USUARIO( CODIGO integer primary key autoincrement, NOME text not null, LOGIN text not null unique, SENHA text not null, CLUBE integer not null)");
            db.execSQL("create table POSICAO (CODIGO integer primary key autoincrement, DESCRICAO text not null)");

            //db.execSQL(iPersistente.getCreateTable());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            Log.d("DATABASE","onUpgrade!");
            db.execSQL("DROP TABLE IF EXISTS USUARIO");
            db.execSQL("DROP TABLE IF EXISTS POSICAO");
            onCreate(db);
        }

    }
}
