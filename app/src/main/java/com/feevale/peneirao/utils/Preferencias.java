package com.feevale.peneirao.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.feevale.peneirao.bd.BancoDados;
import com.feevale.peneirao.domain.Usuario;

public class Preferencias {

    public static void salvarUsuarioLogado(Context pContexto, int pCodigo){
        SharedPreferences shPref = pContexto.getSharedPreferences("PREFERENCIAS", 0);
        SharedPreferences.Editor editor = shPref.edit();
        editor.putInt("USUARIO", pCodigo);
        editor.commit();
    }

    public static Usuario obterUsuarioLogado(Context pContexto){
        SharedPreferences shPref = pContexto.getSharedPreferences("PREFERENCIAS", 0);
        int codigo = shPref.getInt("USUARIO",0);
        if (codigo > 0){
            BancoDados<Usuario> bd = new BancoDados<Usuario>(pContexto, Usuario.class);
            return  (Usuario)bd.obter(codigo);
        }
        else{
            return null;
        }
    }
}
