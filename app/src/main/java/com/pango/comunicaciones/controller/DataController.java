package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pango.comunicaciones.ActNotDetalle;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.SplashScreenActivity;
import com.pango.comunicaciones.model.Comunicado;
import com.pango.comunicaciones.model.Imagen;
import com.pango.comunicaciones.model.Img_Gal;
import com.pango.comunicaciones.model.NotDet;
import com.pango.comunicaciones.model.Noticias;
import com.pango.comunicaciones.model.Vid_Gal;
import com.pango.comunicaciones.model.Video;

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
 * Created by Andre on 27/09/2017.
 */

public class DataController extends AsyncTask<String,Void,Void> {
int varcant;
    String url;
    String opcion;
    SplashScreenActivity splashScreenActivity;

    ProgressDialog progressDialog;
    List<Noticias> noticiaData=new ArrayList<Noticias>();
    List<Imagen> imagenData=new ArrayList<Imagen>();
    List<Comunicado> comunicadoData=new ArrayList<Comunicado>();
    List<Video> videoData=new ArrayList<Video>();

    public DataController(String url,String opcion, SplashScreenActivity splashScreenActivity){
        this.url=url;
        this.opcion=opcion;
        this.splashScreenActivity=splashScreenActivity;
    }

    @Override
    protected Void doInBackground(String... params) {
        String a = params[0];
        String b = params[1];

       if (GlobalVariables.Urlbase.equals("")){

           obtener_noticia(a,b);
           obtener_comunicado(a,b);
           obtener_imagen(a,b);
           obtener_video(a,b);

    } else {

           getToken gettoken=new getToken();
           gettoken.getToken();

           obtener_noticia(a,b);
           obtener_comunicado(a,b);
           obtener_imagen(a,b);
           obtener_video(a,b);

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
            //progressDialog = ProgressDialog.show(splashScreenActivity, "Loading", "Cargando publicaciones...");
        }
    }
    @Override
    protected  void onPostExecute(Void result){
        try {
            if (opcion == "get") {

                GlobalVariables.noticias2=noticiaData;
                GlobalVariables.comlist=comunicadoData;
                GlobalVariables.imagen2=imagenData;
                GlobalVariables.vidlist=videoData;


            }
        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }

public void obtener_noticia(String a, String b){
    try {
        HttpResponse response;

        if(opcion=="get"){
            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpGet get = new HttpGet(GlobalVariables.Urlbase + GlobalVariables.Urlbase2 + a + "/" + b + "/" + "TP01");//url de cada publicacion

                get.setHeader("Authorization", "Bearer " + GlobalVariables.token_auth);
                response = httpClient.execute(get);

                String respstring = EntityUtils.toString(response.getEntity());
                JSONObject respJSON = new JSONObject(respstring);

                JSONArray data_p = respJSON.getJSONArray("Data");
                GlobalVariables.contador1 = respJSON.getInt("Count");

                for (int i = 0; i < data_p.length(); i++) {
                    JSONObject h = data_p.getJSONObject(i);
                    String Tipo = h.getString("Tipo");


                    String CodRegistro = h.getString("CodRegistro");
                    String Autor = h.getString("Autor");
                    String Fecha = h.getString("Fecha");
                    String Titulo = h.getString("Titulo");
                    String Descripcion = h.getString("Descripcion");
                    int icon = R.drawable.ic_menu_noticias;

                    JSONObject Files = h.getJSONObject("Files");

                    JSONArray Data2 = Files.getJSONArray("Data");
                    ArrayList<String> dataf = new ArrayList<>();
                    for (int j = 0; j < Data2.length(); j++) {
                        JSONObject h1 = Data2.getJSONObject(j);

                        String Correlativo = h1.getString("Correlativo");
                        String Url = h1.getString("Url");
                        String Urlmin = h1.getString("Urlmin");

                        dataf.add(Correlativo);
                        dataf.add(Url.replaceAll("\\s", "%20"));
                        dataf.add(Urlmin.replaceAll("\\s", "%20"));
                    }

                    noticiaData.add(new Noticias(CodRegistro, Tipo, icon, Autor, Fecha, Titulo, Descripcion, dataf));

                }

            }catch (Exception ex){
                Log.w("Error get\n",ex);
            }
        }
    }
    catch (Throwable e) {
        Log.d("InputStream", e.getLocalizedMessage());
    }
}


    public void obtener_comunicado(String a, String b){
        try {
            HttpResponse response;

            if(opcion=="get"){
                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase + GlobalVariables.Urlbase2 + a + "/" + b + "/" + "TP02");//url de cada publicacion

                    get.setHeader("Authorization", "Bearer " + GlobalVariables.token_auth);
                    response = httpClient.execute(get);

                    String respstring = EntityUtils.toString(response.getEntity());
                    JSONObject respJSON = new JSONObject(respstring);

                    JSONArray data_p = respJSON.getJSONArray("Data");
                    GlobalVariables.contador2 = respJSON.getInt("Count");


                    for (int i = 0; i < data_p.length(); i++) {
                        JSONObject h = data_p.getJSONObject(i);
                        String Tipo = h.getString("Tipo");


                        String CodRegistro = h.getString("CodRegistro");
                        String Autor = h.getString("Autor");
                        String Fecha = h.getString("Fecha");
                        String Titulo = h.getString("Titulo");
                        String Descripcion = h.getString("Descripcion");
                        int icon = R.drawable.ic_menu_publicaciones;

                        JSONObject Files = h.getJSONObject("Files");
                        JSONArray Data2 = Files.getJSONArray("Data");
                        ArrayList<String> dataf = new ArrayList<>();
                        for (int j = 0; j < Data2.length(); j++) {
                            JSONObject h1 = Data2.getJSONObject(j);

                            String Correlativo = h1.getString("Correlativo");
                            String Url = h1.getString("Url");
                            String Urlmin = h1.getString("Urlmin");

                            dataf.add(Correlativo);
                            dataf.add(Url.replaceAll("\\s", "%20"));
                            dataf.add(Urlmin.replaceAll("\\s", "%20"));
                        }
                        comunicadoData.add(new Comunicado(CodRegistro, Tipo, icon, Autor, Fecha, Titulo, Descripcion, dataf));
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
    }


    public void obtener_imagen(String a, String b){
        try {
            HttpResponse response;

            if(opcion=="get"){
                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase + GlobalVariables.Urlbase2 + a + "/" + b + "/" + "TP03");//url de cada publicacion

                    get.setHeader("Authorization", "Bearer " + GlobalVariables.token_auth);
                    response = httpClient.execute(get);

                    String respstring = EntityUtils.toString(response.getEntity());
                    JSONObject respJSON = new JSONObject(respstring);

                    JSONArray data_p = respJSON.getJSONArray("Data");
                    GlobalVariables.contador3 = respJSON.getInt("Count");


                    for (int i = 0; i < data_p.length(); i++) {
                        JSONObject h = data_p.getJSONObject(i);
                        String Tipo = h.getString("Tipo");


                        String CodRegistro = h.getString("CodRegistro");
                        String Autor = h.getString("Autor");
                        String Fecha = h.getString("Fecha");
                        String Titulo = h.getString("Titulo");
                        int icon = R.drawable.ic_menu_gallery;

                        JSONObject Files = h.getJSONObject("Files");
                        int cant_img = Files.getInt("Count");
                        JSONArray Data2 = Files.getJSONArray("Data");

                        List<Img_Gal> datafImg = new ArrayList<>();
                        for (int j = 0; j < Data2.length(); j++) {
                            JSONObject m = Data2.getJSONObject(j);

                            String Correlativo = m.getString("Correlativo");
                            String Url = m.getString("Url");
                            String Urlmin = m.getString("Urlmin");

                            datafImg.add(new Img_Gal(Correlativo, Url.replaceAll("\\s", "%20"), Urlmin.replaceAll("\\s", "%20")));
                        }

                        imagenData.add(new Imagen(CodRegistro, Tipo, icon, Autor, Fecha, Titulo, datafImg, cant_img));
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
    }




    public void obtener_video(String a, String b){
        try {
            HttpResponse response;

            if(opcion=="get"){
                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase + GlobalVariables.Urlbase2 + a + "/" + b + "/" + "TP04");//url de cada publicacion

                    get.setHeader("Authorization", "Bearer " + GlobalVariables.token_auth);
                    response = httpClient.execute(get);

                    String respstring = EntityUtils.toString(response.getEntity());
                    JSONObject respJSON = new JSONObject(respstring);

                    JSONArray data_p = respJSON.getJSONArray("Data");
                    GlobalVariables.contador4 = respJSON.getInt("Count");


                    for (int i = 0; i < data_p.length(); i++) {
                        JSONObject h = data_p.getJSONObject(i);
                        String Tipo = h.getString("Tipo");


                        String CodRegistro = h.getString("CodRegistro");
                        String Autor = h.getString("Autor");
                        String Fecha = h.getString("Fecha");
                        String Titulo = h.getString("Titulo");
                        int icon = R.drawable.ic_menu_slideshow;

                        JSONObject Files = h.getJSONObject("Files");
                        int CantidadV = Files.getInt("Count");
                        JSONArray Data2 = Files.getJSONArray("Data");


                        List<Vid_Gal> datafile = new ArrayList<>();
                        for (int j = 0; j < Data2.length(); j++) {
                            JSONObject n = Data2.getJSONObject(j);

                            String Correlativo = n.getString("Correlativo");
                            String Url = n.getString("Url");
                            String Urlmin = n.getString("Urlmin");

                            datafile.add(new Vid_Gal(Correlativo, Url.replaceAll("\\s", "%20"), Urlmin.replaceAll("\\s", "%20")));

                        }

                        videoData.add(new Video(CodRegistro, Tipo, icon, Autor, Fecha, Titulo, datafile, CantidadV));
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
    }

















}
