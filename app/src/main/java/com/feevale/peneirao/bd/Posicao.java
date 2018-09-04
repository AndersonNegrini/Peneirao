package com.feevale.peneirao.bd;

import android.content.ContentValues;
import android.database.Cursor;

public class Posicao implements IPersistente {
    private int codigo;
    private String descricao;

    public Posicao(){

    }

    @Override
    public ContentValues inserir() {
        ContentValues valores = new ContentValues();
        valores.put("DESCRICAO", getDescricao());
        return valores;
    }

    @Override
    public ContentValues editar() {
        ContentValues valores = new ContentValues();
        valores.put("DESCRICAO", getDescricao());
        return valores;
    }

    @Override
    public String[] colunas() {
        String[] colunas = {"CODIGO", "DESCRICAO"};
        return colunas;
    }

    @Override
    public void carregar(Cursor pResultados) {
        pResultados.moveToFirst();
        if (pResultados.getCount() > 0) {
            setCodigo(pResultados.getInt(pResultados.getColumnIndex("CODIGO")));
            setDescricao(pResultados.getString(pResultados.getColumnIndex("DESCRICAO")));
        }
    }

    @Override
    public int getCodigo() {
        return codigo;
    }

    @Override
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getNomeTabela() {
        return "POSICAO";
    }

    @Override
    public String getCreateTable() {
        return "create table " + getNomeTabela() +
                "( CODIGO integer primary key autoincrement," +
                "DESCRICAO text not null)";
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
