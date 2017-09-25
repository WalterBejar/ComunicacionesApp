package com.pango.comunicaciones.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.model.NoticiasModel;

import java.util.List;

/**
 * Created by WalterBCH on 25/9/2017.
 */

public class NoticiasInicioAdapter extends ArrayAdapter<NoticiasModel> {

    private List<NoticiasModel> data;
    private Context context;

    public NoticiasInicioAdapter(Context context, List<NoticiasModel> data) {
        super(context, R.layout.item_carrete, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View rowView=inflater.inflate(R.layout.item_carrete, null,true);

        TextView nFecha = (TextView)  rowView.findViewById(R.id.txt_fecha);
        TextView nTitulo = (TextView)  rowView.findViewById(R.id.txt_titulo);
        ImageView nImagen = (ImageView)  rowView.findViewById(R.id.img_carrete);

        final String tempFecha = data.get(position).getFecha();
        final String tempTitulo = data.get(position).getTitulo();

        nFecha.setText(tempFecha);
        nTitulo.setText(tempTitulo);

        int ds = data.get(position).getFiledata().size();

        /*if(ds==0) {
        }else {*/
            Glide.with(context)
                    .load("https://app.antapaccay.com.pe/Proportal/SCOM_Service/api/media/GetImagen/4056/CUMPLEAÑOS.jpg".replaceAll("\\s", "%20"))
                    .into(nImagen);
        //}

        nImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Se hizo click en la imagen", Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;
    }
}
