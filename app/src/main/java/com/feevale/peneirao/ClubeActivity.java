package com.feevale.peneirao;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.domain.Clube;
import com.feevale.peneirao.domain.Posicao;
import com.feevale.peneirao.listas.ListaClubeAdapter;
import com.feevale.peneirao.listas.ListaPosicaoAdapter;

import java.io.FileNotFoundException;

public class ClubeActivity extends AppCompatActivity {

    ListView listViewClubes;
    final AppCompatActivity self = this;
    ListaClubeAdapter adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clube);

        listViewClubes = (ListView) findViewById(R.id.lstClubes);

        BancoDados<Clube> bd =  new BancoDados<Clube>(this, Clube.class);
        adaptador = new ListaClubeAdapter(getBaseContext(), bd);
        listViewClubes.setAdapter(adaptador);
        registerForContextMenu(listViewClubes);

        FloatingActionButton fab = findViewById(R.id.btnNovoClube);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(self, CadastrarClube.class);
                self.startActivityForResult(it, 33);
                adaptador.notifyDataSetChanged();
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.lstClubes) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(((Clube)adaptador.getItem(info.position)).getAbreviacao());
            menu.add(Menu.NONE, 1, 1, "Editar");
            menu.add(Menu.NONE, 2, 2, "Deletar");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Clube clube = ((Clube)adaptador.getItem(info.position));
        switch(item.getItemId()) {
            case 1:
                // Editar
                Intent it = new Intent(getBaseContext(), CadastrarClube.class);
                it.putExtra("CODIGO", clube.getCodigo());
                startActivityForResult(it, 1010);
                adaptador.notifyDataSetChanged();
                return true;
            case 2:
                // Excluir
                BancoDados<Clube> bd =  new BancoDados<Clube>(this, Clube.class);
                bd.remover(clube);
                adaptador.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adaptador.notifyDataSetChanged();
    }
}
