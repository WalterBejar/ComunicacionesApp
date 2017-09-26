package com.pango.comunicaciones;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReservaTicketFiltro extends AppCompatActivity {

    String[] terminalesCodigos;
    String[] terminalesNombres;
    String[] tickets = {"asd", "asdasd"};

    String origenEscogido;
    String destinoEscogido;

    Boolean escogioOrigen;
    Boolean escogioDestino;
    Boolean escogioFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_ticket_filtro);
        new GetTerminales().execute();

        //tickets = ;
        ListView listaTickets = (ListView) findViewById(R.id.listaTickets);
        FiltroAdapter adapter = new FiltroAdapter();
        listaTickets.setAdapter(adapter);
    }

    public void showHideGrupo(View view){
        ConstraintLayout grupo = (ConstraintLayout) findViewById(R.id.grupoConstraint);
        if (grupo.getVisibility() == View.GONE)
            grupo.setVisibility(View.VISIBLE);
        else
            grupo.setVisibility(View.GONE);
    }

    public class GetTerminales extends AsyncTask<String, String, String> {
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
                    final Terminal[] arrayTerminales = gson.fromJson(str, Terminal[].class);
                    terminalesCodigos = new String[arrayTerminales.length];
                    terminalesNombres = new String[arrayTerminales.length];

                    for (int i = 0 ; i < arrayTerminales.length ; i++){
                        terminalesCodigos[i] = arrayTerminales[i].CodTipo;
                        terminalesNombres[i] = arrayTerminales[i].Descripcion;
                    }

                    final Spinner spinnerOrigen = (Spinner) findViewById(R.id.spinnerOrigen);
                    Spinner spinnerDestino = (Spinner) findViewById(R.id.spinnerDestino);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(com.pango.comunicaciones.ReservaTicketFiltro.this,
                            android.R.layout.simple_spinner_item, terminalesNombres);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerDestino.setAdapter(adapter);
                    spinnerOrigen.setAdapter(adapter);

                    spinnerDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            destinoEscogido = terminalesCodigos[position];
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            destinoEscogido = "";
                        }
                    });
                    spinnerOrigen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            origenEscogido = terminalesCodigos[position];
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            origenEscogido = "";
                        }
                    });
            }
            /*
            try {
                TableLayout tablaViajes = (TableLayout) findViewById(R.id.tablaTickets);
                LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
                        (Context.LAYOUT_INFLATER_SERVICE);
                ConstraintLayout constlayout = (ConstraintLayout) inflater.inflate(R.layout.reserva_tickets_row, null);
                TableRow filaViajes = (TableRow) constlayout.findViewById(R.id.RTFiltroFila);

                if(filaViajes.getParent()!=null)
                    ((ViewGroup)filaViajes.getParent()).removeView(filaViajes);

                tablaViajes.addView(filaViajes);
                //tablaViajes.addView(filaViajes);
            } catch (Exception e){
                e.printStackTrace();
            }
            */

            /*
            if (token.equals("")){

            } else {
                //Intent toReservaTicketFiltro = new Intent(getApplicationContext(), ReservaTicketFiltro.class);
                //startActivity(toReservaTicketFiltro);
                Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();
            }*/
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(Utils.getUrlForReservaTicketTerminales());
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
                //Toast.makeText(getApplicationContext(),con.getResponseCode(),Toast.LENGTH_SHORT).show();
                //return "" + con.getResponseCode();
                //return token;
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
            return null;
        }
    }

    public class FiltroAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return tickets.length;
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
            convertView = getLayoutInflater().inflate(R.layout.reserva_tickets_row, null);

            TextView busNombre = (TextView) convertView.findViewById(R.id.lblBusNombre);
            busNombre.setText(tickets[position]);

            return convertView;
        }
    }
}
