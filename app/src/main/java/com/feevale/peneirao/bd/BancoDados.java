package com.feevale.peneirao.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class BancoDados<T extends IPersistente> {
    private final Context ctx;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Class<T> tipoClasse;

    public BancoDados(Context ctx, Class<T> tipoClasse) {
        // TODO Auto-generated constructor stub
        this.ctx = ctx;
        this.tipoClasse = tipoClasse;
        try {
            dbHelper = new DBHelper(ctx, "Peneirao", null, 22, criarInstancia());
        }
        catch(Exception ex) {
        }
    }

    public BancoDados<T> abrir(){
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void fechar(){
        db.close();
    }

    private T criarInstancia()  {
        try {
            return tipoClasse.newInstance();
        }
        catch (Exception ex){
            return null;
        }
    }

    public int inserir(T pPersistente){
        abrir();
        ContentValues valores = pPersistente.inserir();
        int resultado = (int) db.insert(pPersistente.getNomeTabela(), null, valores);
        if (resultado > 0){
            pPersistente.setCodigo(resultado);
        }
        fechar();
        return resultado;
    }

    public IPersistente obter(int pCodigo){
        try {
            abrir();
            T persistente = criarInstancia();
            Cursor resultados = db.query(persistente.getNomeTabela(), persistente.colunas(),
                    "CODIGO = " + pCodigo, null, null, null, "CODIGO");
            if (resultados != null && resultados.getCount() > 0) {
                resultados.moveToFirst();
                persistente.carregar(resultados, ctx);
            }
            fechar();
            return persistente;
        }
        catch (Exception ex){
            return null;
        }
    }
    public IPersistente obter(String pFiltro, String[] pValores){
        try {
            abrir();
            T persistente = criarInstancia();
            Cursor resultados = db.query(persistente.getNomeTabela(), persistente.colunas(),
                    pFiltro, pValores, null, null, "CODIGO");
            if (resultados != null && resultados.getCount() > 0) {
                resultados.moveToFirst();
                persistente.carregar(resultados, ctx);
            }
            fechar();
            return persistente.getCodigo() != 0 ? persistente : null;
        }
        catch (Exception ex) {
            return null;
        }
    }
    public ArrayList<T> obter() {
        try {
            abrir();
            ArrayList<T> lista = new ArrayList<T>();
            T persistente = criarInstancia();
            Cursor resultados = db.query(persistente.getNomeTabela(), persistente.colunas(), null, null, null, null, "CODIGO");
            if (resultados != null && resultados.getCount() > 0) {
                resultados.moveToFirst();
                do {
                    persistente = criarInstancia();
                    persistente.carregar(resultados, ctx);

                    lista.add(persistente);
                } while (resultados.moveToNext());
            }
            fechar();
            return lista;
        }
        catch (Exception ex) {
            return null;
        }
    }

    public int editar(T pPersistente){
        abrir();
        int result = db.update(pPersistente.getNomeTabela(), pPersistente.editar(), "CODIGO = " + pPersistente.getCodigo(), null);
        fechar();
        return result;
    }

    public int removerTodos() {
        try {
            abrir();
            T persistente = criarInstancia();
            int result = db.delete(persistente.getNomeTabela(), null, null);
            fechar();
            return result;
        }
        catch (Exception ex) {
            return -1;
        }
    }

    public int remover(int pCodigo) {
        try {
            abrir();
            T persistente = criarInstancia();
            int result = db.delete(persistente.getNomeTabela(), "CODIGO = " + pCodigo, null);
            fechar();
            return result;
        }
        catch (Exception ex) {
            return -1;
        }
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
            db.execSQL("create table POSICAO (CODIGO integer primary key autoincrement, DESCRICAO text not null unique)");
            db.execSQL("create table CLUBE (CODIGO integer primary key autoincrement, NOME text not null unique, ABREVIACAO text not null unique, IMAGEM blob)");
            db.execSQL("create table AVALIACAO (CODIGO integer primary key autoincrement, DESCRICAO text not null unique, POSICAO integer not null, FOREIGN KEY(POSICAO) REFERENCES POSICAO(CODIGO))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            Log.d("DATABASE","onUpgrade!");
            db.execSQL("DROP TABLE IF EXISTS USUARIO");
            db.execSQL("DROP TABLE IF EXISTS POSICAO");
            db.execSQL("DROP TABLE IF EXISTS CLUBE");
            db.execSQL("DROP TABLE IF EXISTS AVALIACAO");
            onCreate(db);
        }

    }
}
