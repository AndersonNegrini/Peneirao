package com.feevale.peneirao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    PieChart grafico;

    float itensGrafico[] = {12f, 9f, 15f, 8f, 2f};
    String descricao[] = {"Meia", "Atacante", "Volante", "Zagueiro", "Lateral"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grafico = (PieChart) findViewById(R.id.graficoID);

        List<PieEntry> entradasGrafico = new ArrayList<>();

        for (int i=0; i < itensGrafico.length; i++){
            entradasGrafico.add(new PieEntry(itensGrafico[i], descricao[i]));

        }

        PieDataSet dataSet = new PieDataSet(entradasGrafico, "Posições mais avaliadas");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(dataSet);
        grafico.animateY(1300);
        grafico.setData(pieData);

        grafico.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);

        return true;
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
