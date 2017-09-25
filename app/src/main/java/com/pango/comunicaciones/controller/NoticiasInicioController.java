package com.pango.comunicaciones.controller;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.pango.comunicaciones.adapter.NoticiasInicioAdapter;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.model.NoticiasModel;

import java.util.ArrayList;
import java.util.List;

import layout.FragmentInicio;

/**
 * Created by WalterBCH on 25/9/2017.
 */

public class NoticiasInicioController extends AsyncTask<String,Void,Void> {

    View v;
    FragmentInicio fragmentInicio;
    ListView listViewNoticias;
    List<NoticiasModel> listaNoticias = new ArrayList<NoticiasModel>();

    public NoticiasInicioController(View v, FragmentInicio Frag){
        this.v = v;
        fragmentInicio = Frag;
        listViewNoticias = (ListView) v.findViewById(R.id.listViewNoticias);
    }

    @Override
    protected Void doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(v.getContext(), "Se cargaron los videos", Toast.LENGTH_SHORT).show();

        for (int i = 0; i < 10; i++)
        {
            NoticiasModel noticia = new NoticiasModel();
            noticia.setTitulo("Este es el titulo de la noticia Nro: " + (i+1));
            noticia.setFecha("21-09-2017");
            listaNoticias.add(noticia);
        }

    }
    @Override
    protected  void onPostExecute(Void result){
        try {
            NoticiasInicioAdapter adapter = new NoticiasInicioAdapter(v.getContext(), listaNoticias);
            listViewNoticias.setAdapter(adapter);
        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }
}
