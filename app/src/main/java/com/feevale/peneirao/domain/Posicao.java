package com.feevale.peneirao.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.feevale.peneirao.bd.IPersistente;

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
    public void carregar(Cursor pResultados, Context pContext) {
        if (pResultados != null) {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return getDescricao();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null){
            return getCodigo() == ((Posicao)obj).getCodigo();
        }
        return super.equals(obj);
    }
}
