package com.feevale.peneirao;

public class Usuario {
    private int codigo;
    private String nome;
    private String login;
    private String senha;
    //Clube clube;
    private int clube;

    public Usuario(){

    }

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public int getClube() {
        return clube;
    }
    public void setClube(int clube) {
        this.clube = clube;
    }
}
