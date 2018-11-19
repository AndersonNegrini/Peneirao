package com.feevale.peneirao.listas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feevale.peneirao.R;
import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.domain.Atleta;
import com.feevale.peneirao.domain.AvaliacaoAtleta;

import java.util.ArrayList;
import java.util.List;

public class ListaAtletaAdapter  extends BaseAdapter {
    Context ctx;
    BancoDados<Atleta> db;
    List<Atleta> atletas;


    public ListaAtletaAdapter(Context ctx, BancoDados<Atleta> db) {
        this.ctx = ctx;
        this.db = db;
        atletas = db.obter();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        atletas = db.obter();
        return atletas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        atletas = db.obter();
        return atletas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        atletas = db.obter();
        return atletas.size() == 0 ? (long)0 : atletas.get(position).getCodigo();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.template_atleta, null);

        TextView txtNome = (TextView) convertView.findViewById(R.id.txtTempNomeAtleta);
        TextView txtPosicao = (TextView) convertView.findViewById(R.id.txtTempPosicaoAtleta);
        TextView txtMedia = (TextView) convertView.findViewById(R.id.txtMedia);

        Atleta atleta = atletas.get(position);

        txtNome.setText(atleta.getNome());
        txtPosicao.setText(atleta.getPosicao().getDescricao());

        BancoDados<AvaliacaoAtleta> bdAvaliacaoAtleta = new BancoDados<AvaliacaoAtleta>(ctx, AvaliacaoAtleta.class);
        ArrayList<AvaliacaoAtleta> avaliacoesFeitas = bdAvaliacaoAtleta.obterFiltrado("ATLETA = ?", new String[] { String.valueOf(atleta.getCodigo()) });
        float media = 0;
        for (AvaliacaoAtleta av: avaliacoesFeitas) {
            media += av.getNota();
        }
        if (avaliacoesFeitas.size() > 0){
            media /= avaliacoesFeitas.size();
        }
        txtMedia.setText(String.valueOf(media));

        return convertView;
    }
}
