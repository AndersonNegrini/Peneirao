package com.feevale.peneirao.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.bd.IPersistente;

public class Avaliacao implements IPersistente {
    private int codigo;
    private String descricao;
    private int codigoPosicao;
    private Posicao posicao;
    BancoDados<Posicao> bd;

    public Avaliacao(){
        codigoPosicao = 0;
    }

    @Override
    public ContentValues inserir() {
        ContentValues valores = new ContentValues();
        valores.put("DESCRICAO", getDescricao());
        valores.put("POSICAO", codigoPosicao != 0 ? codigoPosicao : null);
        return valores;
    }

    @Override
    public ContentValues editar() {
        ContentValues valores = new ContentValues();
        valores.put("DESCRICAO", getDescricao());
        valores.put("POSICAO", codigoPosicao != 0 ? codigoPosicao : null);
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
            codigoPosicao = pResultados.getInt(pResultados.getColumnIndex("POSICAO"));
            bd = new BancoDados<Posicao>(pContext, Posicao.class);
        }
    }

    @Override
    public boolean validarExclusao(Context ctx) {
        return true;
    }

    @Override
    public String obterErros() {
        return null;
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
        if (posicao == null && codigoPosicao != 0){
            IPersistente p = bd.obter(codigoPosicao);
            setPosicao(p != null ? (Posicao)p : null);
            bd = null;
        }
        return posicao;
    }
    public void setPosicao(Posicao posicao) {
        if (posicao != null){
            codigoPosicao=posicao.getCodigo();
        }
        else{
            codigoPosicao = 0;
        }
        this.posicao = posicao;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null){
            return getCodigo() == ((Avaliacao)obj).getCodigo();
        }
        return super.equals(obj);
    }
}