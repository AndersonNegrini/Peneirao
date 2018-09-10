package com.feevale.peneirao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.feevale.peneirao.bd.BancoDados;

public class CadastrarUsuario extends AppCompatActivity {

    TextView txtNome;
    TextView txtLogin;
    TextView txtSenha;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

        txtNome = (TextView)findViewById(R.id.txtNome);
        txtLogin = (TextView)findViewById(R.id.txtUsuario);
        txtSenha = (TextView)findViewById(R.id.txtSenha);
        btnRegistrar = (Button)findViewById(R.id.btnRegistrarUsuario);
    }
    public void onClickRegistrar(View v) throws Exception {
        String nome = txtNome.getText().toString();
        String login = txtLogin.getText().toString();
        String senha = txtSenha.getText().toString();
        if (nome.length() != 0 && login.length() != 0 && senha.length() != 0){
            BancoDados<Usuario> bd = new BancoDados<Usuario>(this, Usuario.class);
            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setLogin(login);
            usuario.setSenha(senha);
            bd.inserir(usuario);

            Intent it = new Intent(this, Login.class);
            this.startActivity(it);
        }
    }
}
