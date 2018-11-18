package com.feevale.peneirao;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.domain.Posicao;
import com.feevale.peneirao.domain.Usuario;
import com.feevale.peneirao.listas.ListaPosicaoAdapter;

import java.util.ArrayList;

public class PosicaoActivity extends AppCompatActivity {

    ListView listViewPosicoes;
    final AppCompatActivity self = this;
    ListaPosicaoAdapter adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posicao);

        listViewPosicoes = (ListView) findViewById(R.id.lstPosicoes);

        BancoDados<Posicao> bd =  new BancoDados<Posicao>(this, Posicao.class);

        adaptador = new ListaPosicaoAdapter(getBaseContext(), bd);
        listViewPosicoes.setAdapter(adaptador);
        registerForContextMenu(listViewPosicoes);

        FloatingActionButton fab = findViewById(R.id.btnNovaPosicao);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(self, CadastrarPosicao.class);
                self.startActivityForResult(it, 1);
                adaptador.notifyDataSetChanged();
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.lstPosicoes) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(((Posicao)adaptador.getItem(info.position)).getDescricao());
            menu.add(Menu.NONE, 1, 1, "Editar");
            menu.add(Menu.NONE, 2, 2, "Deletar");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Posicao posicao = ((Posicao)adaptador.getItem(info.position));
        switch(item.getItemId()) {
            case 1:
                // Editar
                Intent it = new Intent(getBaseContext(), CadastrarPosicao.class);
                it.putExtra("CODIGO", posicao.getCodigo());
                startActivityForResult(it, 1010);
                adaptador.notifyDataSetChanged();
                return true;
            case 2:
                // Excluir
                BancoDados<Posicao> bd =  new BancoDados<Posicao>(this, Posicao.class);
                bd.remover(posicao);
                adaptador.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
