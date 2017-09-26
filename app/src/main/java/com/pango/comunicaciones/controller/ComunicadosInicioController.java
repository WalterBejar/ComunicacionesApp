package com.pango.comunicaciones.controller;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.pango.comunicaciones.R;
import com.pango.comunicaciones.adapter.ComunicadosInicioAdapter;
import com.pango.comunicaciones.adapter.NoticiasInicioAdapter;
import com.pango.comunicaciones.model.ComunicadoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Walter Béjar on 26/09/2017.
 */

public class ComunicadosInicioController {

    View v;
    RecyclerView listViewComunicados;
    List<ComunicadoModel> listaComunicados = new ArrayList<ComunicadoModel>();

    public ComunicadosInicioController(View v){
        this.v = v;
        listViewComunicados = (RecyclerView) v.findViewById(R.id.listViewComunicados);
        //listViewNoticias.setHasFixedSize(true);
    }

    public void Execute() {
        for (int i = 0; i < 10; i++)
        {
            ComunicadoModel comunicado = new ComunicadoModel();
            comunicado.setTitulo("Este es el titulo del comunicado Nro: " + (i+1));
            comunicado.setFecha("21-09-2017");
            listaComunicados.add(comunicado);
        }

        try {
            ComunicadosInicioAdapter adapter = new ComunicadosInicioAdapter(v.getContext(), listaComunicados);
            LinearLayoutManager MyLayoutManager = new LinearLayoutManager(v.getContext());
            MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            if (listaComunicados.size() > 0 & listViewComunicados != null) {
                listViewComunicados.setAdapter(adapter);
            }
            listViewComunicados.setLayoutManager(MyLayoutManager);

        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }
}
