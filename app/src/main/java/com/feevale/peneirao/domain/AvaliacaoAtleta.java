package com.feevale.peneirao.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.bd.IPersistente;

public class AvaliacaoAtleta implements IPersistente {
    private int codigo;
    private Avaliacao avaliacao;
    private Atleta atleta;
    private float nota;

    public AvaliacaoAtleta(){

    }
    public AvaliacaoAtleta(Avaliacao pAvaliacao, Atleta pAtleta){
        avaliacao=pAvaliacao;
        atleta=pAtleta;
    }

    @Override
    public ContentValues inserir() {
        ContentValues valores = new ContentValues();
        valores.put("AVALIACAO", avaliacao != null ? avaliacao.getCodigo() : null);
        valores.put("ATLETA", atleta != null ? atleta.getCodigo() : null);
        valores.put("NOTA", getNota());
        return valores;
    }

    @Override
    public ContentValues editar() {
        ContentValues valores = new ContentValues();
        valores.put("AVALIACAO", avaliacao != null ? avaliacao.getCodigo() : null);
        valores.put("ATLETA", atleta != null ? atleta.getCodigo() : null);
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

            int codigoAvaliacao = pResultados.getInt(pResultados.getColumnIndex("AVALIACAO"));
            BancoDados<Avaliacao> bd = new BancoDados<Avaliacao>(pContext, Avaliacao.class);
            IPersistente avaliacao = bd.obter(codigoAvaliacao);
            setAvaliacao(avaliacao != null ? (Avaliacao)avaliacao : null);

            int codigoAtleta = pResultados.getInt(pResultados.getColumnIndex("ATLETA"));
            BancoDados<Atleta> bd2 = new BancoDados<Atleta>(pContext, Atleta.class);
            IPersistente atleta = bd2.obter(codigoAtleta);
            setAtleta(atleta != null ? (Atleta)atleta : null);
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

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }
    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Atleta getAtleta() {
        return atleta;
    }
    public void setAtleta(Atleta atleta) {
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