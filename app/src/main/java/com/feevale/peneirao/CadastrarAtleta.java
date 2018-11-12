package com.feevale.peneirao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.domain.Atleta;
import com.feevale.peneirao.domain.Posicao;
import com.feevale.peneirao.utils.MascaraTelefone;
import com.feevale.peneirao.utils.Preferencias;

import java.util.ArrayList;

public class CadastrarAtleta extends Activity {

    TextView txtViewNomeAtleta;
    TextView txtViewAnoNascimentoAtleta;
    TextView txtViewProsAtleta;
    TextView txtViewContraAtleta;
    TextView txtViewTelefoneAtleta;
    Spinner cbLateralidade;
    Spinner cbPosicao;
    Atleta atleta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cadastrar_atleta);
        txtViewNomeAtleta = (TextView)findViewById(R.id.txtCadNomeAtleta);
        txtViewAnoNascimentoAtleta = (TextView) findViewById(R.id.txtCadAnoNascimentoAtleta);
        txtViewProsAtleta = (TextView) findViewById(R.id.txtCadProsAtleta);
        txtViewContraAtleta = (TextView) findViewById(R.id.txtCadContraAtleta);
        txtViewTelefoneAtleta = (TextView) findViewById(R.id.txtCadTelefoneAtleta);
        EditText editTextTelefone = (EditText)findViewById(R.id.txtCadTelefoneAtleta);
        editTextTelefone.addTextChangedListener(MascaraTelefone.insert("(##)#####-####", editTextTelefone));
        cbLateralidade = (Spinner)findViewById(R.id.cbCadLateralidadeAtleta);
        cbPosicao = (Spinner)findViewById(R.id.cbCadPosicaoAtleta);

        BancoDados<Posicao> bdPosicao = new BancoDados<Posicao>(this, Posicao.class);
        ArrayList<Posicao> posicoes = bdPosicao.obter();
        ArrayAdapter adp = new ArrayAdapter<Posicao>(this, R.layout.spinner_posicao, posicoes);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cbPosicao.setAdapter(adp);
        cbPosicao.setDropDownVerticalOffset(147);

        ArrayList<String> opcoesLateralidade = new ArrayList<String>();
        opcoesLateralidade.add("Destro");
        opcoesLateralidade.add("Canhoto");
        ArrayAdapter adpLateralidade = new ArrayAdapter<String>(this, R.layout.spinner_lateralidade, opcoesLateralidade);
        adpLateralidade.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cbLateralidade.setAdapter(adpLateralidade);
        cbLateralidade.setDropDownVerticalOffset(147);

        Intent it = getIntent();
        int codigo = it.getIntExtra("CODIGO", 0);
        if (codigo > 0){
            BancoDados<Atleta> bd = new BancoDados<Atleta>(this, Atleta.class);
            atleta = (Atleta) bd.obter(codigo);
            txtViewNomeAtleta.setText(atleta.getNome());
            txtViewAnoNascimentoAtleta.setText(String.valueOf(atleta.getAnoNascimento()));
            txtViewProsAtleta.setText(atleta.getPros());
            txtViewContraAtleta.setText(atleta.getContra());
            txtViewTelefoneAtleta.setText(atleta.getTelefone());
            cbPosicao.setSelection(posicoes.indexOf(atleta.getPosicao()));
            cbLateralidade.setSelection(opcoesLateralidade.indexOf(atleta.getLateralidade()));
        }
        else{
            atleta = new Atleta();
        }
    }
    public void onClickConfirmar(View v) {
        if (txtViewNomeAtleta.getText().toString().length() == 0){
            Toast.makeText(this, "Informe o nome do atleta.", Toast.LENGTH_LONG).show();
        }
        else if (cbPosicao.getSelectedItem() == null){
            Toast.makeText(this, "Informe a posição do atleta.", Toast.LENGTH_LONG).show();
        }
        else if (txtViewAnoNascimentoAtleta.getText().toString().length() == 0){
            Toast.makeText(this, "Informe o ano de nascimento do atleta.", Toast.LENGTH_LONG).show();
        }
        else if (cbLateralidade.getSelectedItem() == null){
            Toast.makeText(this, "Informe a lateralidade do atleta.", Toast.LENGTH_LONG).show();
        }
        else if (txtViewTelefoneAtleta.getText().toString().length() == 0){
            Toast.makeText(this, "Informe o telefone do atleta.", Toast.LENGTH_LONG).show();
        }
        else {
            BancoDados<Atleta> bd = new BancoDados<Atleta>(this, Atleta.class);
            atleta.setNome(txtViewNomeAtleta.getText().toString());
            atleta.setPosicao((Posicao) cbPosicao.getSelectedItem());
            atleta.setAnoNascimento(Integer.parseInt(txtViewAnoNascimentoAtleta.getText().toString()));
            atleta.setPros(txtViewProsAtleta.getText().toString());
            atleta.setContra(txtViewContraAtleta.getText().toString());
            atleta.setLateralidade(cbLateralidade.getSelectedItem().toString());
            atleta.setTelefone(txtViewTelefoneAtleta.getText().toString());
            atleta.setUsuario(Preferencias.obterUsuarioLogado(this));
            if (atleta.getCodigo() > 0) {
                bd.editar(atleta);
                Intent it = new Intent(this, AtletaActivity.class);
                this.startActivity(it);
            } else {
                int resultado = bd.inserir(atleta);
                if (resultado == -1) {
                    Toast.makeText(this, "Atleta já existe!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            finish();
        }
    }
}
