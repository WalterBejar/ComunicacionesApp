package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.pango.comunicaciones.ActVid;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.adapter.Adap_Vid;
import com.pango.comunicaciones.model.Vid_Gal;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andre on 23/08/2017.
 */

public class ViddetController extends AsyncTask<String,Void,Void> {

   // View v;
    String url;
    String opcion;
    ActVid actVid;

    ProgressDialog progressDialog;
    ArrayList<String> VidDetArray=new ArrayList<String>();
    List<Vid_Gal> view_video=new ArrayList<Vid_Gal>();
    // ListView recList;
    static ImageView imagv1;
    static TextView txv1, txv2, txv3;
    private GridView gridView;
    private Adap_Vid adaptador;
    //TextView tx4;
    //WebView content;

    // int a;
    // int celda = 3;

    public ViddetController(String url, String opcion, ActVid actVid){
        //this.v=v;
        this.url=url;
        this.opcion=opcion;
        this.actVid=actVid;///////////////////////////////////////////////////////////////////
        // recList=(ListView) v.findViewById(R.id.list_recycler);


        // tx4 = (TextView)  v.findViewById(R.id.not_subt);
        //content=(WebView) v.findViewById(R.id.Visor);
        //recList.setOnScrollListener(this);
    }
    @Override
    protected Void doInBackground(String... params) {
        try {
            HttpResponse response;
            String codreg=params[0];

            //String b=params[1];

            if(opcion=="get"){
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase+"entrada/Getentrada/"+codreg);//url de cada publicacion
                    get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    response = httpClient.execute(get);

                    String respstring = EntityUtils.toString(response.getEntity());
                    JSONObject respJSON = new JSONObject(respstring);
                    //notdetArray.add(R.drawable.ic_menu_noticias);

                    VidDetArray.add(respJSON.getString("Autor"));
                    VidDetArray.add(respJSON.getString("Fecha"));
                    VidDetArray.add(respJSON.getString("Titulo"));
                    // ImgdetArray.add(respJSON.getString("Subtitulo"));
                    //  ImgdetArray.add(respJSON.getString("Descripcion"));

                    JSONObject Files = respJSON.getJSONObject("Files");
                    JSONArray Data2 = Files.getJSONArray("Data");

                    for (int i = 0; i < Data2.length(); i++) {
                        JSONObject h = Data2.getJSONObject(i);
/*
                        private String Correlativo;
                        private String url_img;
                        private String urlmin_imag;*/

                        String correlativo=h.getString("Correlativo");
                        // int tamanio=h.getInt("Tamanio");
                        String url_file=h.getString("Url");
                        String urlmin=h.getString("Urlmin");


                        view_video.add(new Vid_Gal(correlativo,url_file.replaceAll("\\s","%20"),urlmin.replaceAll("\\s","%20")));

                    }

                    // des_data
                }catch (Exception ex){
                    Log.w("Error get\n",ex);
                }
            }
        }
        catch (Throwable e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return null;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }
    @Override
    protected void onPreExecute() {
        if(opcion=="get") {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(actVid, "Loading", "Cargando publicaciones...");
        }
    }
    @Override
    protected  void onPostExecute(Void result){
        try {
            if (opcion == "get") {

                GlobalVariables.listdetvid=view_video;//datos correlativo,url,urlmin

                imagv1 =(ImageView) actVid.findViewById(R.id.icon_viddet);
                txv1 = (TextView) actVid.findViewById(R.id.txv_publicador);
                txv2 = (TextView) actVid.findViewById(R.id.txvfecha);
                txv3 = (TextView) actVid.findViewById(R.id.vid_titulo);

                imagv1.setImageResource(R.drawable.ic_menu_slideshow);


                txv1.setText(VidDetArray.get(0));
                txv2.setText(VidDetArray.get(1));
                txv3.setText(VidDetArray.get(2));

                gridView = (GridView) actVid.findViewById(R.id.grid_vid);
                adaptador = new Adap_Vid(actVid);
                gridView.setAdapter(adaptador);
                progressDialog.dismiss();

            }
        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }



}
