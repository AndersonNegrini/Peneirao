package com.feevale.peneirao;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.AutoText;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.domain.Atleta;
import com.feevale.peneirao.domain.Avaliacao;
import com.feevale.peneirao.domain.AvaliacaoAtleta;
import com.feevale.peneirao.listas.ListaAtletaAdapter;
import com.feevale.peneirao.listas.ListaMelhoresAtletasAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    PieChart grafico;
    ListView listViewMelhoresAtletas;
    ListaMelhoresAtletasAdapter adaptador;

    float itensGrafico[] = {12f, 9f, 15f, 8f, 2f};
    String descricao[] = {"Meia", "Atacante", "Volante", "Zagueiro", "Lateral"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grafico = (PieChart) findViewById(R.id.graficoID);
        DesenharGrafico();
        DesenharMelhoresAtletas();
    }

    private void DesenharGrafico(){
        List<PieEntry> entradasGrafico = new ArrayList<>();
        BancoDados<Atleta> bdAtletas = new BancoDados<Atleta>(this, Atleta.class);
        ArrayList<Atleta> atletas = bdAtletas.obter();
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (Atleta at: atletas) {
            if (map.containsKey(at.getPosicao().getDescricao())){
                Integer n = map.get(at.getPosicao().getDescricao());
                map.remove(at.getPosicao().getDescricao());
                map.put(at.getPosicao().getDescricao(), ++n);
            }
            else{
                map.put(at.getPosicao().getDescricao(), 1);
            }
        }

        for(Map.Entry<String, Integer> entry : map.entrySet()) {
            entradasGrafico.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entradasGrafico, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(dataSet);
        Legend legend = grafico.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setTextColor(Color.WHITE);
        grafico.setCenterText("Atletas por posição");
        grafico.animateY(1300);
        grafico.setBackgroundColor(Color.BLACK);
        grafico.setData(pieData);
        grafico.invalidate();
    }
    private void DesenharMelhoresAtletas(){
        listViewMelhoresAtletas = (ListView) findViewById(R.id.lstMelhoresAtletas);
        BancoDados<Atleta> bd =  new BancoDados<Atleta>(this, Atleta.class);
        adaptador = new ListaMelhoresAtletasAdapter(getBaseContext(), bd);
        listViewMelhoresAtletas.setAdapter(adaptador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        DesenharGrafico();
        adaptador.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.posicao) {
            Intent it = new Intent(this, PosicaoActivity.class);
            this.startActivity(it);
            return true;
        }
        else if (id == R.id.btnClubes){
            Intent it = new Intent(this, ClubeActivity.class);
            this.startActivity(it);
            return true;
        }
        else if (id == R.id.btnAvaliacoes){
            Intent it = new Intent(this, AvaliacaoActivity.class);
            this.startActivity(it);
            return true;
        }
        else if (id == R.id.btnAtletas){
            Intent it = new Intent(this, AtletaActivity.class);
            this.startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
