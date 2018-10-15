package com.feevale.peneirao;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.domain.Avaliacao;
import com.feevale.peneirao.domain.Posicao;
import com.feevale.peneirao.listas.ListaAvaliacaoAdapter;
import com.feevale.peneirao.listas.ListaPosicaoAdapter;

public class AvaliacaoActivity extends AppCompatActivity {

    ListView listViewAvaliacoes;
    final AppCompatActivity self = this;
    ListaAvaliacaoAdapter adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao);

        listViewAvaliacoes = (ListView) findViewById(R.id.lstAvaliacoes);

        BancoDados<Avaliacao> bd =  new BancoDados<Avaliacao>(this, Avaliacao.class);

        adaptador = new ListaAvaliacaoAdapter(getBaseContext(), bd);
        listViewAvaliacoes.setAdapter(adaptador);
        registerForContextMenu(listViewAvaliacoes);

        FloatingActionButton fab = findViewById(R.id.btnNovaAvaliacao);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(self, CadastrarAvaliacao.class);
                self.startActivityForResult(it, 1);
                adaptador.notifyDataSetChanged();
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lstAvaliacoes) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(((Avaliacao)adaptador.getItem(info.position)).getDescricao());
            menu.add(Menu.NONE, 1, 1, "Editar");
            menu.add(Menu.NONE, 2, 2, "Deletar");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Avaliacao avaliacao = ((Avaliacao)adaptador.getItem(info.position));
        switch(item.getItemId()) {
            case 1:
                // Editar
                Intent it = new Intent(getBaseContext(), CadastrarPosicao.class);
                it.putExtra("CODIGO", avaliacao.getCodigo());
                startActivityForResult(it, 1010);
                finish();
                return true;
            case 2:
                // Excluir
                BancoDados<Avaliacao> bd =  new BancoDados<Avaliacao>(this, Avaliacao.class);
                bd.remover(avaliacao.getCodigo());
                adaptador.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
