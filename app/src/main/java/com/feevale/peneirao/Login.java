package com.feevale.peneirao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.feevale.peneirao.bd.BancoDados;

import java.util.Objects;

public class Login extends AppCompatActivity {
    TextView txtLogin;
    TextView txtSenha;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login);


        txtLogin = (TextView)findViewById(R.id.txtUsuario);
        txtSenha = (TextView)findViewById(R.id.txtSenha);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnLogin.setEnabled(false);
        txtLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 0 && txtSenha.getText().length() != 0) {
                    btnLogin.setEnabled(true);
                }
                else{
                    btnLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0 && txtLogin.getText().length() != 0) {
                    btnLogin.setEnabled(true);
                }
                else{
                    btnLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void onClickLogin(View v) throws Exception {
        String login = txtLogin.getText().toString();
        String senha = txtSenha.getText().toString();
        BancoDados<Usuario> bd =  new BancoDados<Usuario>(this, Usuario.class);
        Usuario usuario = (Usuario)bd.obter("LOGIN = ? and SENHA = ?", new String[] { login, senha});
        if (usuario != null){
            alerta("Sucesso !");
            Intent it = new Intent(this, MainActivity.class);
            this.startActivity(it);
        }
        else{
            alerta("Usuário não encontrado.");
        }
    }
    public void onClickCadastrar(View v) {
        Intent it = new Intent(this, CadastrarUsuario.class);
        this.startActivity(it);
    }

    public void alerta(String pMensagem){
        Toast.makeText(this, pMensagem, Toast.LENGTH_LONG).show();
    }
}
