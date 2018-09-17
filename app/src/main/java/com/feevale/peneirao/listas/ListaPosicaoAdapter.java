package com.feevale.peneirao.listas;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feevale.peneirao.R;
import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.domain.Posicao;

public class ListaPosicaoAdapter extends BaseAdapter{
    Context ctx;
    BancoDados<Posicao> db;
    List<Posicao> posicoes;


    public ListaPosicaoAdapter(Context ctx, BancoDados<Posicao> db) {
        this.ctx = ctx;
        this.db = db;
        posicoes = db.obter();
    }

    public  void recarregar(){
        posicoes = db.obter();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        posicoes = db.obter();
        return posicoes.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        posicoes = db.obter();
        return posicoes.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        posicoes = db.obter();
        return posicoes.size() == 0 ? (long)0 : posicoes.get(position).getCodigo();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.template_posicao, null);

        TextView txtDescricao = (TextView) convertView.findViewById(R.id.txtViewDescricao);

        Posicao posicao = posicoes.get(position);

        txtDescricao.setText(posicao.getDescricao());
        return convertView;
    }

}
