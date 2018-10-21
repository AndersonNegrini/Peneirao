package com.feevale.peneirao.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;

import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.bd.IPersistente;
import com.feevale.peneirao.utils.BitmapUtils;

public class Atleta implements IPersistente {
    private int codigo;
    private String nome;
    private int anoNascimento;
    private String lateralidade;
    private String pros;
    private String contra;
    private String telefone;
    private Bitmap foto;
    private Posicao posicao;
    private Usuario usuario;

    public Atleta(){

    }

    @Override
    public ContentValues inserir() {
        ContentValues valores = new ContentValues();
        valores.put("NOME", getNome());
        valores.put("ANONASCIMENTO", getAnoNascimento());
        valores.put("LATERALIDADE", getLateralidade());
        valores.put("PROS", getPros());
        valores.put("CONTRA", getContra());
        valores.put("TELEFONE", getTelefone());
        valores.put("FOTO", BitmapUtils.getBytes(getFoto()));
        valores.put("POSICAO", posicao != null ? posicao.getCodigo() : null);
        valores.put("USUARIO", usuario != null ? usuario.getCodigo() : null);
        return valores;
    }

    @Override
    public ContentValues editar() {
        ContentValues valores = new ContentValues();
        valores.put("NOME", getNome());
        valores.put("ANONASCIMENTO", getAnoNascimento());
        valores.put("LATERALIDADE", getLateralidade());
        valores.put("PROS", getPros());
        valores.put("CONTRA", getContra());
        valores.put("TELEFONE", getTelefone());
        valores.put("FOTO", BitmapUtils.getBytes(getFoto()));
        valores.put("POSICAO", posicao != null ? posicao.getCodigo() : null);
        valores.put("USUARIO", usuario != null ? usuario.getCodigo() : null);
        return valores;
    }

    @Override
    public String[] colunas() {
        String[] colunas = {"CODIGO", "NOME", "ANONASCIMENTO", "LATERALIDADE", "PROS", "CONTRA", "TELEFONE", "FOTO", "POSICAO", "USUARIO"};
        return colunas;
    }

    @Override
    public void carregar(Cursor pResultados, Context pContext) {
        if (pResultados != null) {
            setCodigo(pResultados.getInt(pResultados.getColumnIndex("CODIGO")));
            setNome(pResultados.getString(pResultados.getColumnIndex("NOME")));
            setAnoNascimento(pResultados.getInt(pResultados.getColumnIndex("ANONASCIMENTO")));
            setLateralidade(pResultados.getString(pResultados.getColumnIndex("LATERALIDADE")));
            setPros(pResultados.getString(pResultados.getColumnIndex("PROS")));
            setContra(pResultados.getString(pResultados.getColumnIndex("CONTRA")));
            setTelefone(pResultados.getString(pResultados.getColumnIndex("TELEFONE")));

            int codigoPosicao = pResultados.getInt(pResultados.getColumnIndex("POSICAO"));
            BancoDados<Posicao> bdPosicao = new BancoDados<Posicao>(pContext, Posicao.class);
            IPersistente p = bdPosicao.obter(codigoPosicao);
            setPosicao(p != null ? (Posicao)p : null);

            int codigoUsuario = pResultados.getInt(pResultados.getColumnIndex("USUARIO"));
            BancoDados<Usuario> bdUsuario = new BancoDados<Usuario>(pContext, Usuario.class);
            IPersistente u = bdUsuario.obter(codigoUsuario);
            setUsuario(u != null ? (Usuario)u : null);

            setFoto(BitmapUtils.getImage(pResultados.getBlob(pResultados.getColumnIndex("FOTO"))));
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
        return "ATLETA";
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public String getLateralidade() {
        return lateralidade;
    }

    public void setLateralidade(String lateralidade) {
        this.lateralidade = lateralidade;
    }

    public String getPros() {
        return pros;
    }

    public void setPros(String pros) {
        this.pros = pros;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null){
            return getCodigo() == ((Atleta)obj).getCodigo();
        }
        return super.equals(obj);
    }
}