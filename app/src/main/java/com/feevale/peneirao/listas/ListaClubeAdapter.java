package com.feevale.peneirao.listas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feevale.peneirao.R;
import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.domain.Clube;
import com.feevale.peneirao.domain.Posicao;

import java.util.List;

public class ListaClubeAdapter extends BaseAdapter {
    Context ctx;
    BancoDados<Clube> db;
    List<Clube> clubes;


    public ListaClubeAdapter(Context ctx, BancoDados<Clube> db) {
        this.ctx = ctx;
        this.db = db;
        clubes = db.obter();
    }

    public  void recarregar(){
        clubes = db.obter();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        clubes = db.obter();
        return clubes.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        clubes = db.obter();
        return clubes.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        clubes = db.obter();
        return clubes.size() == 0 ? (long)0 : clubes.get(position).getCodigo();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.template_clube, null);

        TextView txtNome = (TextView) convertView.findViewById(R.id.txtViewClubeNome);
        TextView txtAbrev = (TextView) convertView.findViewById(R.id.txtViewClubeAbrev);
        ImageView img = (ImageView) convertView.findViewById(R.id.imgClubeLst);

        Clube clube = clubes.get(position);
        txtNome.setText(clube.getNome());
        txtAbrev.setText(clube.getAbreviacao());
        img.setImageBitmap(clube.getImagem());
        return convertView;
    }
}
