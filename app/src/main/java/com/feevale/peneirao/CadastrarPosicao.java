package com.feevale.peneirao;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.domain.Posicao;
import com.feevale.peneirao.domain.Usuario;

public class CadastrarPosicao extends Activity {

    TextView txtViewPosicao;
    Posicao posicao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_posicao);
        txtViewPosicao = (TextView)findViewById(R.id.txtPosicao);

        Intent it = getIntent();
        int codigo = it.getIntExtra("CODIGO", 0);
        if (codigo > 0){
            BancoDados<Posicao> bd = new BancoDados<Posicao>(this, Posicao.class);
            posicao = (Posicao)bd.obter(codigo);
            txtViewPosicao.setText(posicao.getDescricao());
        }
        else{
            posicao = new Posicao();
        }
    }
    public void onClickConfirmarPosicao(View v) {
        String txtPosicao = txtViewPosicao.getText().toString();
        if (txtPosicao.length() != 0){
            BancoDados<Posicao> bd = new BancoDados<Posicao>(this, Posicao.class);
            posicao.setDescricao(txtPosicao);
            if (posicao.getCodigo() > 0){
                bd.editar(posicao);
            }
            else {
                int resultado = bd.inserir(posicao);
                if (resultado == -1){
                    Toast.makeText(this, "Posição já existe!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            finish();
        }
        else{
            Toast.makeText(this, "Informe uma posição.", Toast.LENGTH_LONG).show();
        }
    }
}
