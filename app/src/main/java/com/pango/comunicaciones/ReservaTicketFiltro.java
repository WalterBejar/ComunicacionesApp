package com.pango.comunicaciones;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

    String origenEscogido;
    String destinoEscogido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_ticket_filtro);
        new GetTerminales().execute();
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
                    spinnerDestino.setSelection(0);
                    spinnerOrigen.setSelection(0);

                    spinnerDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            origenEscogido = terminalesCodigos[position];
                            //Toast.makeText(getApplicationContext(),"Pos: " + position + " - Id: " + id,Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // your code here
                        }

                    });

                    //adapter.notifyDataSetChanged();

            }
            /*
            if (token.equals("")){
                Toast.makeText(getApplicationContext(),"Hubo un error. Revise los datos ingresados o su configuración.",Toast.LENGTH_SHORT).show();
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

}
