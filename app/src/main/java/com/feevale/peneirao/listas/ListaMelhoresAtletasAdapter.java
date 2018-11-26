package com.feevale.peneirao.listas;

import android.content.Context;
import android.text.AutoText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feevale.peneirao.R;
import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.domain.Atleta;
import com.feevale.peneirao.domain.AvaliacaoAtleta;
import com.feevale.peneirao.domain.Clube;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListaMelhoresAtletasAdapter extends BaseAdapter {
    Context ctx;
    BancoDados<Atleta> db;
    List<Atleta> atletas;


    public ListaMelhoresAtletasAdapter(Context ctx, BancoDados<Atleta> db) {
        this.ctx = ctx;
        this.db = db;
        //atletas = db.obter();
        //atletas = new ArrayList<Atleta>();
        ObterMelhoresAtletas();
    }

    private void ObterMelhoresAtletas(){
        ArrayList<AtletaMedia> atletasMedia = new ArrayList<AtletaMedia>();
        BancoDados<AvaliacaoAtleta> bdAvaliacaoAtleta = new BancoDados<AvaliacaoAtleta>(ctx, AvaliacaoAtleta.class);

        for (Atleta atleta: db.obter()) {
            ArrayList<AvaliacaoAtleta> avaliacoesFeitas = bdAvaliacaoAtleta.obterFiltrado("ATLETA = ?", new String[] { String.valueOf(atleta.getCodigo()) });
            float media = 0;
            for (AvaliacaoAtleta av: avaliacoesFeitas) {
                media += av.getNota();
            }
            if (avaliacoesFeitas.size() > 0){
                media /= avaliacoesFeitas.size();
            }

            atletasMedia.add(new AtletaMedia(atleta, media));
        }

        Collections.sort(atletasMedia, new Comparator<AtletaMedia>() {
            @Override
            public int compare(AtletaMedia atleta1, AtletaMedia atleta2)
            {
                return  Float.compare(atleta1.media, atleta2.media) * -1;
            }
        });

        ArrayList<Atleta> atletasTop = new ArrayList<Atleta>();
        for (AtletaMedia atletaMedia: atletasMedia) {
            atletasTop.add(atletaMedia.atleta);
            if (atletasTop.size() == 5) break;
        }

        atletas = atletasTop;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        ObterMelhoresAtletas();
        return atletas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        ObterMelhoresAtletas();
        return atletas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        ObterMelhoresAtletas();
        return atletas.size() == 0 ? (long)0 : atletas.get(position).getCodigo();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.template_melhoresatletas, null);

        TextView txtNome = (TextView) convertView.findViewById(R.id.txtMelhorAtletaNome);
        TextView txtPosicao = (TextView) convertView.findViewById(R.id.txtMelhorAtletaPosicao);
        TextView txtMedia = (TextView) convertView.findViewById(R.id.txtMelhorAtletaMedia);

        Atleta atleta = atletas.get(position);

        txtNome.setText(atleta.getNome());
        txtPosicao.setText(atleta.getPosicao().getDescricao());

        /*
        BancoDados<AvaliacaoAtleta> bdAvaliacaoAtleta = new BancoDados<AvaliacaoAtleta>(ctx, AvaliacaoAtleta.class);
        ArrayList<AvaliacaoAtleta> avaliacoesFeitas = bdAvaliacaoAtleta.obterFiltrado("ATLETA = ?", new String[] { String.valueOf(atleta.getCodigo()) });
        float media = 0;
        for (AvaliacaoAtleta av: avaliacoesFeitas) {
            media += av.getNota();
        }
        if (avaliacoesFeitas.size() > 0){
            media /= avaliacoesFeitas.size();
        }
        */
        txtMedia.setText(String.valueOf(atleta.getMedia()));
        return convertView;
    }
}

class AtletaMedia{
    public Atleta atleta;
    public float media;

    public AtletaMedia(Atleta pAtleta, float pMedia){
        atleta=pAtleta;
        media=pMedia;
    }
}
