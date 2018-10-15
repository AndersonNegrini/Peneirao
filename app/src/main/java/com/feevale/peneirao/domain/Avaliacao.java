package com.feevale.peneirao.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.bd.IPersistente;

public class Avaliacao implements IPersistente {
    private int codigo;
    private String descricao;
    private Posicao posicao;

    public Avaliacao(){

    }

    @Override
    public ContentValues inserir() {
        ContentValues valores = new ContentValues();
        valores.put("DESCRICAO", getDescricao());
        valores.put("POSICAO", posicao != null ? posicao.getCodigo() : null);
        return valores;
    }

    @Override
    public ContentValues editar() {
        ContentValues valores = new ContentValues();
        valores.put("DESCRICAO", getDescricao());
        valores.put("POSICAO", posicao != null ? posicao.getCodigo() : null);
        return valores;
    }

    @Override
    public String[] colunas() {
        String[] colunas = {"CODIGO", "DESCRICAO", "POSICAO"};
        return colunas;
    }

    @Override
    public void carregar(Cursor pResultados, Context pContext) {
        if (pResultados != null) {
            setCodigo(pResultados.getInt(pResultados.getColumnIndex("CODIGO")));
            setDescricao(pResultados.getString(pResultados.getColumnIndex("DESCRICAO")));
            int codigoPosicao = pResultados.getInt(pResultados.getColumnIndex("POSICAO"));
            BancoDados<Posicao> bd = new BancoDados<Posicao>(pContext, Posicao.class);
            IPersistente p = bd.obter(codigoPosicao);
            setPosicao(p != null ? (Posicao)p : null);
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
        return "AVALIACAO";
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Posicao getPosicao() {
        return posicao;
    }
    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }
}