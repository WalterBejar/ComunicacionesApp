package com.pango.comunicaciones.controller;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pango.comunicaciones.R;
import com.pango.comunicaciones.adapter.ImagenesInicioAdapter;
import com.pango.comunicaciones.adapter.NoticiasInicioAdapter;
import com.pango.comunicaciones.model.ImagenModel;
import com.pango.comunicaciones.model.NoticiaModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Walter Béjar on 26/09/2017.
 */

public class ImagenesInicioController {

    View v;
    RecyclerView listViewImagenes;
    List<ImagenModel> listaImagenes = new ArrayList<ImagenModel>();

    public ImagenesInicioController(View v){
        this.v = v;
        listViewImagenes = (RecyclerView) v.findViewById(R.id.listViewImagenes);
        //listViewNoticias.setHasFixedSize(true);
    }

    public void Execute() {
        for (int i = 0; i < 10; i++)
        {
            ImagenModel imagen = new ImagenModel();
            imagen.setTitulo("Este es el titulo de la imagen Nro: " + (i+1));
            imagen.setFecha("21-09-2017");
            listaImagenes.add(imagen);
        }

        try {
            ImagenesInicioAdapter adapter = new ImagenesInicioAdapter(v.getContext(), listaImagenes);
            LinearLayoutManager MyLayoutManager = new LinearLayoutManager(v.getContext());
            MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            if (listaImagenes.size() > 0 & listViewImagenes != null) {
                listViewImagenes.setAdapter(adapter);
            }
            listViewImagenes.setLayoutManager(MyLayoutManager);

        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }
}
