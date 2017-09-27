package com.pango.comunicaciones;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pango.comunicaciones.model.TicketModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReservaTicketDetalle extends AppCompatActivity {

    String codigoTicket;
    TicketModel ticket;

    String[] busDetalleListaNombre = {"Nro Programa", "Fecha", "Hora", "Origen", "Destino", "Reservas", "Patente", "Marca", "Modelo", "Tipo Vehiculo", "Asientos"};

    DetalleAdapter detalleAdapter;

    Button botonGestionarPasajeros, botonGestionarReserva;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_ticket_detalle);

        botonGestionarPasajeros = (Button) findViewById(R.id.botonGestionarPasajeros);
        botonGestionarReserva = (Button) findViewById(R.id.botonGestionarReserva);

        // Establecer visibilidad de botonGestionarPasajeros
        if (Utils.esAdmin)
            botonGestionarPasajeros.setVisibility(View.VISIBLE);
        else
            botonGestionarPasajeros.setVisibility(View.GONE);

        // Recibir codigo ticket
        Bundle extras = getIntent().getExtras();
        codigoTicket = extras.getString("CodigoTicket");

        // Asignar adapter a lista
        detalleAdapter = new DetalleAdapter();
        ListView listaDetalles = (ListView) findViewById(R.id.listaDetalles);
        listaDetalles.setAdapter(detalleAdapter);

        //LLamar a GetBusDetalles
        new GetBusDetalles().execute();

    }

    public void gestionarReserva(View view){
        if (ticket != null && ticket.Separado)
            botonGestionarReserva.setText("Reservar ticket");
        else
            botonGestionarReserva.setText("Eliminar Reserva");
        if (ticket != null)
            ticket.Separado = !ticket.Separado;
    }

    public void toReservaTicketListaPasajeros(View view){
        Intent toReservaTicketListaPasajeros = new Intent(getApplicationContext(), ReservaTicketListaPasajeros.class);
        toReservaTicketListaPasajeros.putExtra("CodigoTicket", ticket.Codigo);
        startActivity(toReservaTicketListaPasajeros);
        /*
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
                */
    }

    public class GetBusDetalles extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);

            switch (str) {
                case "404":
                    break;
                case "500":
                    break;
                default:
                    Gson gson = new Gson();
                    ticket = gson.fromJson(str, TicketModel.class);

                    if (ticket.Separado)
                        botonGestionarReserva.setText("Eliminar reserva");
                    else
                        botonGestionarReserva.setText("Reservar ticket");

                    // Actualizar lista
                    detalleAdapter.notifyDataSetChanged();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(Utils.getUrlForReservaTicketDetalle(codigoTicket));
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("Authorization", "Bearer " + Utils.token);
                con.setRequestMethod("GET");
                con.connect();

                switch (con.getResponseCode()) {
                    case 200:
                        InputStream in = con.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder result = new StringBuilder();
                        String line;
                        while((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        return result.toString();
                    default:
                        return "" + con.getResponseCode();
                }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
            return null;
        }
    }

    public class DetalleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (ticket == null)
                return 0;
            return busDetalleListaNombre.length;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            if (ticket == null)
                return  null;

            convertView = getLayoutInflater().inflate(R.layout.reserva_tickets_detalle, null);

            TextView ladoIzquierdo = (TextView) convertView.findViewById(R.id.lblBusCaracteristica);
            ladoIzquierdo.setText(busDetalleListaNombre[position]);

            TextView ladoDerecho = (TextView) convertView.findViewById(R.id.lblBusDetalle);
            ladoDerecho.setText(Utils.getTicketProperty(ticket,busDetalleListaNombre[position]));

            return convertView;
        }
    }
}
