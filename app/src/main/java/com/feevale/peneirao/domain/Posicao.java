package com.feevale.peneirao.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.bd.IPersistente;

import java.util.ArrayList;

public class Posicao implements IPersistente {
    private ArrayList<String> erros = new ArrayList<String>();

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
    public boolean validarExclusao(Context ctx) {
        erros.clear();

        BancoDados<Atleta> bdAtletas = new BancoDados<Atleta>(ctx, Atleta.class);
        Atleta atleta = (Atleta)bdAtletas.obter("POSICAO = ?", new String[] { String.valueOf(getCodigo()) });
        if (atleta != null){
            erros.add("Não é possível excluir Posição vinculada em Atleta.");
        }

        BancoDados<Avaliacao> bdAvaliacao = new BancoDados<Avaliacao>(ctx, Avaliacao.class);
        Avaliacao avaliacao = (Avaliacao)bdAvaliacao.obter("POSICAO = ?", new String[] { String.valueOf(getCodigo()) });
        if (avaliacao != null){
            erros.add("Não é possível excluir Posição vinculada em Avaliação de Atleta.");
        }

        return erros.size() == 0;
    }

    @Override
    public String obterErros() {
        StringBuilder builder = new StringBuilder();
        for(String e : erros){
            if (builder.length() != 0){
                builder.append("\n");
            }
            builder.append(e);
        }
        return builder.toString();
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
