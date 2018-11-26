package com.feevale.peneirao.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;

import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.bd.IPersistente;
import com.feevale.peneirao.utils.BitmapUtils;

import java.util.ArrayList;

public class Atleta implements IPersistente {
    private int codigo;
    private String nome;
    private int anoNascimento;
    private String lateralidade;
    private String pros;
    private String contra;
    private String telefone;
    private Bitmap foto;

    BancoDados<Posicao> bdPosicao;
    private int codigoPosicao;
    private Posicao posicao;

    BancoDados<Usuario> bdUsuario;
    private int codigoUsuario;
    private Usuario usuario;

    private float media = 0;
    private Boolean calculou = false;
    BancoDados<AvaliacaoAtleta> bdAvaliacaoAtleta;

    public Atleta(){
        codigoUsuario=0;
        codigoPosicao=0;
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
        valores.put("POSICAO", codigoPosicao != 0 ? codigoPosicao : null);
        valores.put("USUARIO", codigoUsuario != 0 ? codigoUsuario : null);
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
        valores.put("POSICAO", codigoPosicao != 0 ? codigoPosicao : null);
        valores.put("USUARIO", codigoUsuario != 0 ? codigoUsuario : null);
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

            codigoPosicao = pResultados.getInt(pResultados.getColumnIndex("POSICAO"));
            bdPosicao = new BancoDados<Posicao>(pContext, Posicao.class);

            codigoUsuario = pResultados.getInt(pResultados.getColumnIndex("USUARIO"));
            bdUsuario = new BancoDados<Usuario>(pContext, Usuario.class);

            setFoto(BitmapUtils.getImage(pResultados.getBlob(pResultados.getColumnIndex("FOTO"))));

            bdAvaliacaoAtleta = new BancoDados<AvaliacaoAtleta>(pContext, AvaliacaoAtleta.class);
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

    @Override
    public boolean validarExclusao(Context ctx) {
        return true;
    }

    @Override
    public String obterErros() {
        return null;
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

    public int getCodigoPosicao(){ return codigoPosicao;}
    public Posicao getPosicao() {
        if (posicao == null && codigoPosicao != 0) {
            IPersistente p = bdPosicao.obter(codigoPosicao);
            setPosicao(p != null ? (Posicao)p : null);
            bdPosicao = null;
        }

        return posicao;
    }

    public void setPosicao(Posicao posicao) {
        if (posicao != null) {
            codigoPosicao=posicao.getCodigo();
        }
        else {
            codigoPosicao = 0;
        }
        this.posicao = posicao;
    }

    public Usuario getUsuario() {
        if (usuario == null && codigoUsuario != 0){
            IPersistente u = bdUsuario.obter(codigoUsuario);
            setUsuario(u != null ? (Usuario)u : null);
            bdUsuario = null;
        }

        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        if (usuario != null){
            codigoUsuario=usuario.getCodigo();
        }
        else{
            codigoUsuario = 0;
        }
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null){
            return getCodigo() == ((Atleta)obj).getCodigo();
        }
        return super.equals(obj);
    }

    public void calcularMedia(){
        if (calculou){
            return;
        }
        calculou=true;
        ArrayList<AvaliacaoAtleta> avaliacoesFeitas = bdAvaliacaoAtleta.obterFiltrado("ATLETA = ?", new String[] { String.valueOf(getCodigo()) });
        media = 0;
        for (AvaliacaoAtleta av: avaliacoesFeitas) {
            media += av.getNota();
        }
        if (avaliacoesFeitas.size() > 0){
            media /= avaliacoesFeitas.size();
        }
    }
    public  float getMedia(){
        calcularMedia();

        return media;
    }
}