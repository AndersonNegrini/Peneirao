package com.feevale.peneirao.bd;

import android.content.ContentValues;
import android.database.Cursor;

public interface IPersistente {
    ContentValues inserir();
    ContentValues editar();
    String[] colunas();
    void carregar(Cursor pResultados);

    int getCodigo();
    void setCodigo(int codigo);
    String getNomeTabela();
    String getCreateTable();
}
