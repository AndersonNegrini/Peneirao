package com.feevale.peneirao.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.bd.IPersistente;

public class AvaliacaoAtleta implements IPersistente {
    private int codigo;
    private int codigoAvaliacao;
    private Avaliacao avaliacao;
    BancoDados<Avaliacao> bdAvaliacao;

    private int codigoAtleta;
    private Atleta atleta;
    BancoDados<Atleta> bdAtleta;

    private float nota;

    public AvaliacaoAtleta(){
        codigoAtleta=0;
        codigoAvaliacao=0;
    }
    public AvaliacaoAtleta(Avaliacao pAvaliacao, Atleta pAtleta){
        setAvaliacao(pAvaliacao);
        setAtleta(pAtleta);
    }

    @Override
    public ContentValues inserir() {
        ContentValues valores = new ContentValues();
        valores.put("AVALIACAO", codigoAvaliacao != 0 ? codigoAvaliacao : null);
        valores.put("ATLETA", codigoAtleta != 0 ? codigoAtleta : null);
        valores.put("NOTA", getNota());
        return valores;
    }

    @Override
    public ContentValues editar() {
        ContentValues valores = new ContentValues();
        valores.put("AVALIACAO", codigoAvaliacao != 0 ? codigoAvaliacao : null);
        valores.put("ATLETA", codigoAtleta != 0 ? codigoAtleta : null);
        valores.put("NOTA", getNota());
        return valores;
    }

    @Override
    public String[] colunas() {
        String[] colunas = {"CODIGO", "AVALIACAO", "ATLETA", "NOTA"};
        return colunas;
    }

    @Override
    public void carregar(Cursor pResultados, Context pContext) {
        if (pResultados != null) {
            setCodigo(pResultados.getInt(pResultados.getColumnIndex("CODIGO")));
            setNota(pResultados.getFloat(pResultados.getColumnIndex("NOTA")));

            codigoAvaliacao = pResultados.getInt(pResultados.getColumnIndex("AVALIACAO"));
            bdAvaliacao = new BancoDados<Avaliacao>(pContext, Avaliacao.class);

            codigoAtleta = pResultados.getInt(pResultados.getColumnIndex("ATLETA"));
            bdAtleta = new BancoDados<Atleta>(pContext, Atleta.class);
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
        return "AVALIACAOATLETA";
    }

    public float getNota() {
        return nota;
    }
    public void setNota(float nota) {
        this.nota = nota;
    }

    public int getCodigoAvaliacao(){return codigoAvaliacao;}
    public Avaliacao getAvaliacao() {
        if (avaliacao == null && codigoAvaliacao != 0){
            IPersistente avaliacao = bdAvaliacao.obter(codigoAvaliacao);
            setAvaliacao(avaliacao != null ? (Avaliacao)avaliacao : null);
            bdAvaliacao=null;
        }
        return avaliacao;
    }
    public void setAvaliacao(Avaliacao avaliacao) {
        if (avaliacao != null){
            codigoAvaliacao = avaliacao.getCodigo();
        }
        else{
            codigoAvaliacao = 0;
        }
        this.avaliacao = avaliacao;
    }

    public Atleta getAtleta() {
        if (atleta == null && codigoAtleta != 0){
            IPersistente atleta = bdAtleta.obter(codigoAtleta);
            setAtleta(atleta != null ? (Atleta)atleta : null);
            bdAtleta=null;
        }
        return atleta;
    }
    public void setAtleta(Atleta atleta) {
        if (atleta != null){
            codigoAtleta = atleta.getCodigo();
        }
        else{
            codigoAtleta = 0;
        }
        this.atleta = atleta;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null){
            return getCodigo() == ((Avaliacao)obj).getCodigo();
        }
        return super.equals(obj);
    }
}