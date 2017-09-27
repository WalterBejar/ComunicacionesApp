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
        try {
            HttpResponse response;
            String a=params[0];
            String b=params[1];

            getToken gettoken=new getToken();
            gettoken.getToken();

            if(opcion=="get"){
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet get = new HttpGet(GlobalVariables.Urlbase+GlobalVariables.Urlbase2+a+"/"+b+"/-");//url de cada publicacion
                    get.setHeader("Authorization", "Bearer "+ GlobalVariables.token_auth);
                    response = httpClient.execute(get);

                    String respstring = EntityUtils.toString(response.getEntity());
                    JSONObject respJSON = new JSONObject(respstring);

                    JSONArray data_p = respJSON.getJSONArray("Data");

                    GlobalVariables.contador=respJSON.getInt("Count");
                    for (int i = 0; i < data_p.length(); i++) {
                        JSONObject h = data_p.getJSONObject(i);
                        String Tipo =h.getString("Tipo");


                        String CodRegistro = h.getString("CodRegistro");
                        String Autor = h.getString("Autor");
                        String Fecha = h.getString("Fecha");
                        String Titulo = h.getString("Titulo");

                        JSONObject Files = h.getJSONObject("Files");

                        JSONArray Data2 = Files.getJSONArray("Data");
                        ArrayList<String> dataf = new ArrayList<>();
                        for (int j = 0; j < Data2.length(); j++) {
                            JSONObject h1 = Data2.getJSONObject(j);

                            String Correlativo = h1.getString("Correlativo");
                            String Url = h1.getString("Url");
                            String Urlmin = h1.getString("Urlmin");

                            dataf.add(Correlativo);
                            dataf.add(Url.replaceAll("\\s","%20"));
                            dataf.add(Urlmin.replaceAll("\\s","%20"));
                        }
                        //difiere///////////////////////////////////////////////
                        //String Descripcion = h.getString("Descripcion");

                        int icon = R.drawable.ic_menu_noticias;


                        if(Tipo.equals("TP01")){
                            String Descripcion = h.getString("Descripcion");

                            noticiaData.add(new Noticias(CodRegistro, Tipo, icon, Autor, Fecha, Titulo, Descripcion, dataf));

                        }else if(Tipo.equals("TP02")) {
                            String Descripcion = h.getString("Descripcion");
                            comunicadoData.add(new Comunicado(CodRegistro, Tipo, icon, Autor, Fecha, Titulo, Descripcion, dataf));

                        }else if(Tipo.equals("TP03")) {
                            int cant_img=Files.getInt("Count");

                            List<Img_Gal> datafImg = new ArrayList<>();
                            for (int j = 0; j < Data2.length(); j++) {
                                JSONObject m = Data2.getJSONObject(j);

                                String Correlativo = m.getString("Correlativo");
                                String Url = m.getString("Url");
                                String Urlmin = m.getString("Urlmin");

                                datafImg.add(new Img_Gal(Correlativo, Url.replaceAll("\\s","%20"), Urlmin.replaceAll("\\s","%20")));
                            }

                            imagenData.add(new Imagen(CodRegistro, Tipo, icon, Autor, Fecha, Titulo, datafImg,cant_img));

                        }else if(Tipo.equals("TP04")) {
                            int CantidadV=Files.getInt("Count");

                            List<Vid_Gal> datafile = new ArrayList<>();
                            for (int j = 0; j < Data2.length(); j++) {
                                JSONObject n = Data2.getJSONObject(j);

                                String Correlativo = n.getString("Correlativo");
                                String Url = n.getString("Url");
                                String Urlmin = n.getString("Urlmin");

                                datafile.add(new Vid_Gal(Correlativo, Url.replaceAll("\\s","%20"), Urlmin.replaceAll("\\s","%20")));

                            }

                            videoData.add(new Video(CodRegistro, Tipo, icon, Autor, Fecha, Titulo, datafile,CantidadV));
                        }

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




}
