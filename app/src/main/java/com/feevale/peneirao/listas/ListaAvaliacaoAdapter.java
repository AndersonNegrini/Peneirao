package com.feevale.peneirao.listas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feevale.peneirao.R;
import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.domain.Avaliacao;
import com.feevale.peneirao.domain.Posicao;

import java.util.List;

public class ListaAvaliacaoAdapter extends BaseAdapter{
    Context ctx;
    BancoDados<Avaliacao> db;
    List<Avaliacao> avaliacoes;


    public ListaAvaliacaoAdapter(Context ctx, BancoDados<Avaliacao> db) {
        this.ctx = ctx;
        this.db = db;
        avaliacoes = db.obter();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        avaliacoes = db.obter();
        return avaliacoes.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        avaliacoes = db.obter();
        return avaliacoes.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        avaliacoes = db.obter();
        return avaliacoes.size() == 0 ? (long)0 : avaliacoes.get(position).getCodigo();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.template_avaliacao, null);

        TextView txtDescricao = (TextView) convertView.findViewById(R.id.txtAvaliacaoDescricao);

        Avaliacao avaliacao = avaliacoes.get(position);

        txtDescricao.setText(avaliacao.getDescricao());
        return convertView;
    }

}
