package com.feevale.peneirao;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.domain.Atleta;
import com.feevale.peneirao.domain.Avaliacao;
import com.feevale.peneirao.domain.AvaliacaoAtleta;

import java.util.ArrayList;

public class AvaliarAtleta extends AppCompatActivity {

    ArrayList<AvaliacaoAtleta> avaliacoesFeitas;
    ArrayList<RatingBar> ratingBars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliar_atleta);

        Intent it = getIntent();
        int codigo = it.getIntExtra("CODIGO", 0);
        if (codigo > 0) {
            BancoDados<Atleta> bd = new BancoDados<Atleta>(this, Atleta.class);
            Atleta atleta = (Atleta) bd.obter(codigo);

            BancoDados<Avaliacao> bdAvaliacao = new BancoDados<Avaliacao>(this, Avaliacao.class);
            ArrayList<Avaliacao> avaliacoesDisponiveis = bdAvaliacao.obterFiltrado("POSICAO = ?", new String[] { String.valueOf(atleta.getPosicao().getCodigo()) });

            final BancoDados<AvaliacaoAtleta> bdAvaliacaoAtleta = new BancoDados<AvaliacaoAtleta>(this, AvaliacaoAtleta.class);
            avaliacoesFeitas = bdAvaliacaoAtleta.obterFiltrado("ATLETA = ?", new String[] { String.valueOf(atleta.getCodigo()) });

            for(Avaliacao av : avaliacoesDisponiveis){
                AvaliacaoAtleta avAtleta = null;
                for(AvaliacaoAtleta af : avaliacoesFeitas) {
                    if (av.getCodigo() == af.getAvaliacao().getCodigo()){
                        avAtleta=af;
                        break;
                    }
                }

                if (avAtleta == null){
                    avaliacoesFeitas.add(new AvaliacaoAtleta(av, atleta));
                }
            }

            LinearLayout l = findViewById(R.id.avaliarAtletaLinearLayout);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            ratingBars = new ArrayList<RatingBar>();
            for (AvaliacaoAtleta af : avaliacoesFeitas){
                TextView txt = new TextView(getApplicationContext());
                txt.setText(af.getAvaliacao().getDescricao());
                txt.setTextColor(Color.parseColor("#ffffff"));
                txt.setLayoutParams(params);
                l.addView(txt);

                RatingBar rb = new RatingBar(getApplicationContext());
                rb.setRating(af.getNota());
                rb.setNumStars(5);
                rb.setLayoutParams(params);
                //rb.setBackgroundColor(Color.parseColor("#2d2d2d"));
                LayerDrawable stars = (LayerDrawable) rb.getProgressDrawable();
                stars.getDrawable(0).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                l.addView(rb);
                ratingBars.add(rb);
            }

            if (avaliacoesFeitas.size() > 0){
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                params2.gravity = Gravity.CENTER_HORIZONTAL;
                Button btnSalvar = new Button(getApplicationContext());
                btnSalvar.setText("Salvar");
                btnSalvar.setLayoutParams(params2);
                btnSalvar.setBackgroundColor(Color.parseColor("#2196f3"));
                btnSalvar.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        for (int i = 0; i < ratingBars.size(); i++){
                            avaliacoesFeitas.get(i).setNota(ratingBars.get(i).getRating());
                            if (avaliacoesFeitas.get(i).getCodigo() == 0){
                                bdAvaliacaoAtleta.inserir(avaliacoesFeitas.get(i));
                            }
                            else{
                                bdAvaliacaoAtleta.editar(avaliacoesFeitas.get(i));
                            }
                        }

                        Toast.makeText(getApplicationContext(), "Salvo com sucesso!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });

                l.addView(btnSalvar);
            }
        }
    }
}
