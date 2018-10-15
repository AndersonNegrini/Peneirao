package com.feevale.peneirao.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public interface IPersistente {
    ContentValues inserir();
    ContentValues editar();
    String[] colunas();
    void carregar(Cursor pResultados, Context pContext);

    int getCodigo();
    void setCodigo(int codigo);
    String getNomeTabela();
}
