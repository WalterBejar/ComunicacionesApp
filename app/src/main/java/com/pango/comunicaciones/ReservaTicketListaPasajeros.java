package com.pango.comunicaciones;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pango.comunicaciones.model.PasajeroModel;

public class ReservaTicketListaPasajeros extends AppCompatActivity {

    String codigoTicket;
    PasajeroModel[] listaPasajeros = {};
    boolean[] listaCheckBoxPasajeros = {};

    ListaAdapter listaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_ticket_lista_pasajeros);

        // Recibir codigo ticket
        Bundle extras = getIntent().getExtras();
        codigoTicket = extras.getString("CodigoTicket");

        ListView listaTickets = (ListView) findViewById(R.id.listaPasajeros);
        listaAdapter = new ListaAdapter();
        listaTickets.setAdapter(listaAdapter);

        listaPasajeros = new PasajeroModel[2];
        listaPasajeros[0] = new PasajeroModel();
        listaPasajeros[0].Nombre = "N1";
        listaPasajeros[0].ApellidoPaterno = "AP1";
        listaPasajeros[0].ApellidoMaterno = "AM1";
        listaPasajeros[0].Empresa = "E1";
        listaPasajeros[0].Dni = "dni1";

        listaPasajeros[1] = new PasajeroModel();
        listaPasajeros[1].Nombre = "N2";
        listaPasajeros[1].ApellidoPaterno = "AP2";
        listaPasajeros[1].ApellidoMaterno = "AM2";
        listaPasajeros[1].Empresa = "E2";
        listaPasajeros[1].Dni = "dni2";

        listaCheckBoxPasajeros = new boolean[listaPasajeros.length];
        listaAdapter.notifyDataSetChanged();
    }

    public void toBuscarPasajeros(View view) {
        Intent toReservaTicketBuscarPasajeros = new Intent(getApplicationContext(), ReservaTicketBuscarPasajeros.class);
        toReservaTicketBuscarPasajeros.putExtra("CodigoTicket", codigoTicket);
        startActivity(toReservaTicketBuscarPasajeros);
    }


    public class ListaAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (listaPasajeros == null)
                return 0;
            return listaPasajeros.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (listaPasajeros == null)
                return  null;

            convertView = getLayoutInflater().inflate(R.layout.reserva_tickets_listapasajeros, null);

            TextView pasajeroNombre = (TextView) convertView.findViewById(R.id.lblPasajeroNombre);
            pasajeroNombre.setText(listaPasajeros[position].Nombre + listaPasajeros[position].ApellidoPaterno + listaPasajeros[position].ApellidoMaterno);

            TextView pasajeroEmpresa = (TextView) convertView.findViewById(R.id.lblPasajeroEmpresa);
            pasajeroEmpresa.setText(listaPasajeros[position].Empresa);

            TextView pasajeroDNI = (TextView) convertView.findViewById(R.id.lblPasajeroDNI);
            pasajeroDNI.setText(listaPasajeros[position].Dni);

            CheckBox pasajeroCheckEliminar = (CheckBox) convertView.findViewById(R.id.checkBoxListaPasajeros);
            pasajeroCheckEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),""+listaCheckBoxPasajeros[position],Toast.LENGTH_SHORT).show();
                    listaCheckBoxPasajeros[position] = !listaCheckBoxPasajeros[position];
                }
            });
            return convertView;
        }
    }
}
