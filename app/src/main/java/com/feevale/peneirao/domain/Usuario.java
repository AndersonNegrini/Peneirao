package com.feevale.peneirao.domain;

import android.content.ContentValues;
import android.database.Cursor;

import com.feevale.peneirao.bd.IPersistente;

public class Usuario implements IPersistente {
    private int codigo;
    private String nome;
    private String login;
    private String senha;
    //Clube clube;
    private int clube;

    public Usuario(){

    }

    @Override
    public int getCodigo() {
        return codigo;
    }
    @Override
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public int getClube() {
        return clube;
    }
    public void setClube(int clube) {
        this.clube = clube;
    }

    @Override
    public String getNomeTabela(){ return "USUARIO"; }

    @Override
    public String getCreateTable() {
        return "create table " + getNomeTabela() +
                "( CODIGO integer primary key autoincrement," +
                "NOME text not null," +
                "LOGIN text not null unique," +
                "SENHA text not null," +
                "CLUBE integer not null)";
    }

    @Override
    public ContentValues inserir() {
        ContentValues valores = new ContentValues();
        valores.put("NOME", getNome());
        valores.put("LOGIN", getLogin());
        valores.put("SENHA", getSenha());
        valores.put("CLUBE", getClube());
        return valores;
    }

    @Override
    public ContentValues editar() {
        ContentValues valores = new ContentValues();
        valores.put("NOME", getNome());
        valores.put("LOGIN", getLogin());
        valores.put("SENHA", getSenha());
        valores.put("CLUBE", getClube());
        return valores;
    }

    @Override
    public String[] colunas() {
        String[] colunas = {"CODIGO", "NOME", "LOGIN", "SENHA", "CLUBE"};
        return colunas;
    }

    @Override
    public void carregar(Cursor pResultados) {
        if (pResultados != null) {
            setNome(pResultados.getString(pResultados.getColumnIndex("NOME")));
            setCodigo(pResultados.getInt(pResultados.getColumnIndex("CODIGO")));
            setLogin(pResultados.getString(pResultados.getColumnIndex("LOGIN")));
            setSenha(pResultados.getString(pResultados.getColumnIndex("SENHA")));
            setClube(pResultados.getInt(pResultados.getColumnIndex("CLUBE")));
        }
    }
}
