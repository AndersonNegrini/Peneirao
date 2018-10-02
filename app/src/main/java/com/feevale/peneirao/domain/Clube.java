package com.feevale.peneirao.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.feevale.peneirao.bd.IPersistente;
import com.feevale.peneirao.utils.BitmapUtils;

import java.io.ByteArrayOutputStream;

public class Clube implements IPersistente {
    private int codigo;
    private String nome;
    private String abreviacao;
    private Bitmap imagem;

    public Clube(){

    }

    @Override
    public ContentValues inserir() {
        ContentValues valores = new ContentValues();
        valores.put("NOME", getNome());
        valores.put("ABREVIACAO", getAbreviacao());
        valores.put("IMAGEM", BitmapUtils.getBytes(getImagem()));
        return valores;
    }

    @Override
    public ContentValues editar() {
        ContentValues valores = new ContentValues();
        valores.put("NOME", getNome());
        valores.put("ABREVIACAO", getAbreviacao());
        valores.put("IMAGEM", BitmapUtils.getBytes(getImagem()));
        return valores;
    }

    @Override
    public String[] colunas() {
        String[] colunas = {"CODIGO", "NOME", "ABREVIACAO", "IMAGEM"};
        return colunas;
    }

    @Override
    public void carregar(Cursor pResultados) {
        if (pResultados != null) {
            setCodigo(pResultados.getInt(pResultados.getColumnIndex("CODIGO")));
            setNome(pResultados.getString(pResultados.getColumnIndex("NOME")));
            setAbreviacao(pResultados.getString(pResultados.getColumnIndex("ABREVIACAO")));
            setImagem(BitmapUtils.getImage(pResultados.getBlob(pResultados.getColumnIndex("IMAGEM"))));
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
        return "CLUBE";
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAbreviacao() {
        return abreviacao;
    }
    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    public Bitmap getImagem() {
        return imagem;
    }
    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }
}