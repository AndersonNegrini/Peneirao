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
import com.feevale.peneirao.domain.Atleta;
import com.feevale.peneirao.listas.ListaAtletaAdapter;

public class AtletaActivity extends AppCompatActivity {

    ListView listViewAtletas;
    final AppCompatActivity self = this;
    ListaAtletaAdapter adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atleta);
        listViewAtletas = (ListView) findViewById(R.id.lstAtletas);

        BancoDados<Atleta> bd =  new BancoDados<Atleta>(this, Atleta.class);

        adaptador = new ListaAtletaAdapter(getBaseContext(), bd);
        listViewAtletas.setAdapter(adaptador);
        registerForContextMenu(listViewAtletas);

        FloatingActionButton fab = findViewById(R.id.btnNovoAtleta);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(self, CadastrarAtleta.class);
                self.startActivityForResult(it, 1);
                adaptador.notifyDataSetChanged();
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lstAtletas) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(((Atleta)adaptador.getItem(info.position)).getNome());
            menu.add(Menu.NONE, 1, 1, "Avaliar");
            menu.add(Menu.NONE, 2, 2, "Editar");
            menu.add(Menu.NONE, 3, 3, "Deletar");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Atleta atleta = ((Atleta)adaptador.getItem(info.position));
        switch(item.getItemId()) {
            case 1:
                // Avaliar
                Intent itAvaliar = new Intent(getBaseContext(), AvaliarAtleta.class);
                itAvaliar.putExtra("CODIGO", atleta.getCodigo());
                startActivityForResult(itAvaliar, 1010);
                return true;
            case 2:
                // Editar
                Intent it = new Intent(getBaseContext(), CadastrarAtleta.class);
                it.putExtra("CODIGO", atleta.getCodigo());
                startActivityForResult(it, 1010);
                return true;
            case 3:
                // Excluir
                BancoDados<Atleta> bd =  new BancoDados<Atleta>(this, Atleta.class);
                bd.remover(atleta);
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
