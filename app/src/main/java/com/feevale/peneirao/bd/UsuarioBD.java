package com.feevale.peneirao.bd;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.feevale.peneirao.Usuario;

public class UsuarioBD {
    private final Context ctx;
    private usuariosDBHelper dbHelper;
    private SQLiteDatabase db;

    private static final String NOME_BANCO = "usuarios.db";
    private static final String NOME_TABELA = "USUARIO";
    private static final int VERSAO_BANCO = 1;

    private static final String COL_CODIGO = "CODIGO";
    private static final String COL_NOME = "NOME";
    private static final String COL_LOGIN = "LOGIN";
    private static final String COL_SENHA = "SENHA";
    private static final String COL_CLUBE= "CD_CLUBE";

    private static final String SQL_TABELA = "create table " + NOME_TABELA +
            "(" + COL_CODIGO + " integer primary key autoincrement," +
            COL_NOME + " text not null," +
            COL_LOGIN + " text not null," +
            COL_SENHA + " text not null," +
            COL_CLUBE + " integer not null)";

    public UsuarioBD(Context ctx) {
        // TODO Auto-generated constructor stub
        this.ctx = ctx;
        dbHelper = new usuariosDBHelper(ctx, NOME_BANCO, null, VERSAO_BANCO);
    }

    public UsuarioBD abrir(){
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void fechar(){
        db.close();
    }

    public int inserir(Usuario pUsuario){
        abrir();
        ContentValues valores = new ContentValues();
        valores.put(COL_NOME, pUsuario.getNome());
        valores.put(COL_LOGIN, pUsuario.getLogin());
        valores.put(COL_SENHA, pUsuario.getSenha());
        valores.put(COL_CLUBE, pUsuario.getClube());
        int resultado = (int) db.insert(NOME_TABELA, null, valores);
        fechar();
        return resultado;
    }

    public Usuario obter(int pCodigo){
        Usuario usuario = new Usuario();

        String[] colunas = {COL_CODIGO, COL_NOME, COL_LOGIN, COL_SENHA, COL_CLUBE};
        Cursor resultados = db.query(NOME_TABELA, colunas,
                COL_CODIGO + " = " + pCodigo, null, null, null, COL_CODIGO);

        resultados.moveToFirst();
        CarregarUsuario(usuario, resultados);
        return usuario;
    }
    public Usuario obter(String pLogin, String pSenha){
        abrir();
        Usuario usuario = new Usuario();

        String[] colunas = {COL_CODIGO, COL_NOME, COL_LOGIN, COL_SENHA, COL_CLUBE};
        Cursor resultados = db.query(NOME_TABELA, colunas,
                COL_LOGIN + " = ? and " + COL_SENHA + " = ?", new String[] { pLogin, pSenha}, null, null, COL_CODIGO);

        resultados.moveToFirst();
        CarregarUsuario(usuario, resultados);
        fechar();
        return usuario.getCodigo() != 0 ? usuario : null;
    }
    public ArrayList<Usuario> obter(){
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        String[] colunas = {COL_CODIGO, COL_NOME, COL_LOGIN, COL_SENHA, COL_CLUBE};
        Cursor resultados = db.query(NOME_TABELA, colunas, null, null, null, null, COL_CODIGO);
        Usuario usuario;
        if(resultados.getCount() > 0){
            resultados.moveToFirst();
            do{
                usuario = new Usuario();
                CarregarUsuario(usuario, resultados);

                lista.add(usuario);
            }while(resultados.moveToNext());
        }

        return lista;
    }

    public int editar(int pCodigo, Usuario pUsuario){
        ContentValues valores = new ContentValues();
        valores.put(COL_NOME, pUsuario.getNome());
        valores.put(COL_LOGIN, pUsuario.getLogin());
        valores.put(COL_SENHA, pUsuario.getSenha());
        valores.put(COL_CLUBE, pUsuario.getClube());

        return db.update(NOME_TABELA, valores, COL_CODIGO + " = " + pCodigo, null);
    }

    public int removerTodos(){
        return db.delete(NOME_TABELA, null, null);
    }

    public int remover(int pCodigo){
        return db.delete(NOME_TABELA, COL_CODIGO + " = " + pCodigo, null);
    }

    private void CarregarUsuario(Usuario pUsuario, Cursor pResultado){
        if (pResultado.getCount() > 0) {
            pUsuario.setCodigo(pResultado.getInt(pResultado.getColumnIndex(COL_CODIGO)));
            pUsuario.setNome(pResultado.getString(pResultado.getColumnIndex(COL_NOME)));
            pUsuario.setLogin(pResultado.getString(pResultado.getColumnIndex(COL_LOGIN)));
            pUsuario.setSenha(pResultado.getString(pResultado.getColumnIndex(COL_SENHA)));
            pUsuario.setClube(pResultado.getInt(pResultado.getColumnIndex(COL_CLUBE)));
        }
    }

    private static class usuariosDBHelper extends SQLiteOpenHelper{

        public usuariosDBHelper(Context context, String name,
                                CursorFactory factory, int version) {
            super(context, name, factory, version);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            Log.d("DATABASE","onCreate!");
            db.execSQL(SQL_TABELA);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            Log.d("DATABASE","onUpgrade!");
            db.execSQL("DROP TABLE IF EXISTS " + NOME_TABELA);
            onCreate(db);
        }

    }
}
