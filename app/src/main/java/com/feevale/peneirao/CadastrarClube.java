package com.feevale.peneirao;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.domain.Clube;
import com.feevale.peneirao.domain.Posicao;

import java.io.FileNotFoundException;

public class CadastrarClube  extends Activity {

    TextView txtViewNome;
    TextView txtViewAbreviacao;
    ImageView imgViewImagem;

    Clube clube;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_clube);
        txtViewNome = (TextView)findViewById(R.id.txtNomeClube);
        txtViewAbreviacao = (TextView)findViewById(R.id.txtAbreviacaoClube);
        imgViewImagem = (ImageView)findViewById(R.id.imgClube);

        Intent it = getIntent();
        int codigo = it.getIntExtra("CODIGO", 0);
        if (codigo > 0){
            BancoDados<Clube> bd = new BancoDados<Clube>(this, Clube.class);
            clube = (Clube) bd.obter(codigo);
            txtViewNome.setText(clube.getNome());
            txtViewAbreviacao.setText(clube.getAbreviacao());
            imgViewImagem.setImageBitmap(clube.getImagem());
        }
        else{
            clube = new Clube();
        }
    }
    public void onClickCadastrarClube(View v) {
        String nome = txtViewNome.getText().toString();
        String abreviacao = txtViewAbreviacao.getText().toString();
        Bitmap imagem = ((BitmapDrawable)imgViewImagem.getDrawable()).getBitmap();

        if (nome.length() != 0 && abreviacao.length() != 0 && imagem != null){
            BancoDados<Clube> bd = new BancoDados<Clube>(this, Clube.class);
            clube.setNome(nome);
            clube.setAbreviacao(abreviacao);
            clube.setImagem(imagem);
            if (clube.getCodigo() > 0){
                bd.editar(clube);
            }
            else {
                int resultado = bd.inserir(clube);
                if (resultado == -1){
                    Toast.makeText(this, "Clube já existe!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            finish();
        }
        else{
            Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_LONG).show();
        }
    }
    public void onClickCarregarImagem(View v) {
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 90);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 90 && resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                Bitmap bitmapFinal = Bitmap.createScaledBitmap(bitmap, 128, 128, true);
                imgViewImagem.setImageBitmap(bitmapFinal);
            } catch (FileNotFoundException e) {
                //e.printStackTrace();
            }
        }
    }
}
