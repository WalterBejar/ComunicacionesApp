package com.pango.comunicaciones.controller;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pango.comunicaciones.adapter.NoticiasInicioAdapter;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.model.NoticiaModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WalterBCH on 25/9/2017.
 */

public class NoticiasInicioController
    implements NoticiasInicioAdapter.OnAdapterInteractionListener{

    View v;
    RecyclerView listViewNoticias;
    List<NoticiaModel> listaNoticias = new ArrayList<NoticiaModel>();
    NoticiasInicioAdapter adapter;
    TextView cTitulo;
    TextView cFecha;
    ImageView cImagen;
    TextView cDescripcion;

    public NoticiasInicioController(View v){
        this.v = v;
        listViewNoticias = (RecyclerView) v.findViewById(R.id.listViewNoticias);
        listViewNoticias.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Primer render de la noticia
                onNewsSelected(listaNoticias.get(0));
                adapter.notifyItemChanged(0);
                listViewNoticias.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        //listViewNoticias.setHasFixedSize(true);

        cTitulo = (TextView) v.findViewById(R.id.txt_noticia_titulo);
        cFecha = (TextView) v.findViewById(R.id.txt_noticia_fecha);
        cImagen = (ImageView) v.findViewById(R.id.img_noticia);
        cDescripcion = (TextView) v.findViewById(R.id.txt_noticia_contenido);
    }

    public void Execute() {
        for (int i = 0; i < 10; i++)
        {
            NoticiaModel noticia = new NoticiaModel();
            noticia.setTitulo("Este es el titulo de la noticia Nro: " + (i+1));
            String detalle = "Este es el detalle de la noticia es un detalle corto";

            for (int j = 0; j < i/3; j++)
                detalle += " Este el detalle aumentado para incrementar la descripción";

            noticia.setDescripcion(detalle);
            noticia.setFecha("21-09-2017");
            listaNoticias.add(noticia);
        }

        try {
            adapter = new NoticiasInicioAdapter(v.getContext(), listaNoticias, this);
            LinearLayoutManager MyLayoutManager = new LinearLayoutManager(v.getContext());
            MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            if (listaNoticias.size() > 0 & listViewNoticias != null) {
                listViewNoticias.setAdapter(adapter);
            }
            listViewNoticias.setLayoutManager(MyLayoutManager);

        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }
    
    public void refreshState() {
        for (NoticiaModel noticia: listaNoticias) {
            noticia.setIsChecked(false);
        }
    }

    @Override
    public void onNewsSelected(NoticiaModel noticia) {
        cTitulo.setText(noticia.getTitulo());
        cFecha.setText(noticia.getFecha());
        cDescripcion.setText(noticia.getDescripcion());
        Glide.with(v.getContext())
                .load("https://app.antapaccay.com.pe/Proportal/SCOM_Service/api/media/GetImagen/4056/CUMPLEAÑOS.jpg".replaceAll("\\s", "%20"))
                .into(cImagen);
        refreshState();
        noticia.setIsChecked(true);
    }
}
