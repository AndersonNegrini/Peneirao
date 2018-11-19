package com.feevale.peneirao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.domain.Avaliacao;
import com.feevale.peneirao.domain.Posicao;

import java.util.ArrayList;

public class CadastrarAvaliacao extends Activity {

    TextView txtViewAvaliacao;
    Spinner cbPosicao;
    Avaliacao avaliacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_avaliacao);
        txtViewAvaliacao = (TextView)findViewById(R.id.txtCadAvaliacao);

        cbPosicao = (Spinner)findViewById(R.id.cbAvPosicao);

        BancoDados<Posicao> bdPosicao = new BancoDados<Posicao>(this, Posicao.class);
        ArrayList<Posicao> posicoes =  bdPosicao.obter();
        ArrayAdapter adp = new ArrayAdapter<Posicao>(this, R.layout.spinner_posicao, posicoes);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cbPosicao.setAdapter(adp);

        Intent it = getIntent();
        int codigo = it.getIntExtra("CODIGO", 0);
        if (codigo > 0){
            BancoDados<Avaliacao> bd = new BancoDados<Avaliacao>(this, Avaliacao.class);
            avaliacao = (Avaliacao)bd.obter(codigo);
            txtViewAvaliacao.setText(avaliacao.getDescricao());
            cbPosicao.setSelection(posicoes.indexOf(avaliacao.getPosicao()));
        }
        else{
            avaliacao = new Avaliacao();
        }
    }
    public void onClickConfirmar(View v) {
        String txtDescricao = txtViewAvaliacao.getText().toString();
        Object posicao = cbPosicao.getSelectedItem();
        if (txtDescricao.length() != 0 && posicao != null){
            BancoDados<Avaliacao> bd = new BancoDados<Avaliacao>(this, Avaliacao.class);
            avaliacao.setDescricao(txtDescricao);
            avaliacao.setPosicao((Posicao)posicao);
            if (avaliacao.getCodigo() > 0){
                bd.editar(avaliacao);
            }
            else {
                int resultado = bd.inserir(avaliacao);
                if (resultado == -1){
                    Toast.makeText(this, "Avaliação já existe!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            finish();
        }
        else{
            Toast.makeText(this, "Informe uma descrição e uma posicao.", Toast.LENGTH_LONG).show();
        }
    }
}
