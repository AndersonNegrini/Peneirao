package com.feevale.peneirao;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feevale.peneirao.bd.Posicao;

public class ListaPosicaoAdapter extends BaseAdapter{
    Context ctx;
    ArrayList<Posicao> lista;

    public ListaPosicaoAdapter(Context ctx, ArrayList<Posicao> lista) {
        this.ctx = ctx;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.template_posicao, null);

        TextView txtDescricao = (TextView) convertView.findViewById(R.id.txtViewDescricao);

        Posicao posicao = lista.get(position);

        txtDescricao.setText(posicao.getDescricao());
        return convertView;
    }

}
