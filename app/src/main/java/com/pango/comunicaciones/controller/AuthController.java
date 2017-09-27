package com.pango.comunicaciones.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.model.Noticias;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import layout.FragmentTickets;

/**
 * Created by Andre on 04/09/2017.
 */

public class AuthController extends AsyncTask<String,Void,Void> {
    View v;
    String url;
    String opcion;
    FragmentTickets Frag;
    String Resultado="";
    String CodRegistro;
    ArrayList<String> list_user;
    ProgressDialog progressDialog;
    Noticias noticia2;
   // List<User_Auth> user_auth=new ArrayList<User_Auth>();
    //ListView recList;
  //  int a;
   // int celda = 3;

    public AuthController(View v, String url, String opcion, FragmentTickets Frag, ArrayList<String> list_user){
        this.v=v;
        this.url=url;
        this.opcion=opcion;
        this.Frag=Frag;
        this.list_user=list_user;
        //recList=(ListView) v.findViewById(R.id.frag_not);
        //recList.setOnScrollListener(this);
    }
    @Override
    protected Void doInBackground(String... params) {
        try {
            HttpResponse response;
            //String a=params[0];
           // String b=params[1];





            if(opcion=="post"){
                InputStream inputStream = null;
                String result = "";
                try{
                    HttpClient httpclient = new DefaultHttpClient ();
                    HttpPost httpPost = new HttpPost (GlobalVariables.Urlbase+"usuario/getlogin");

                    String json = "";
                   // for(int i=0;i<list_user.size();i++) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.accumulate("CodUsuario", list_user.get(0));
                        jsonObject.accumulate("Password", list_user.get(1));
                        jsonObject.accumulate("Dominio", list_user.get(2));

                        json += jsonObject.toString();
                        /*if(i!=(lista_Parametros.size()-1)){
                            json+=",";
                        }*/
                    //}

                    //json+="]";

                    StringEntity se = new StringEntity(json);
                    httpPost.setEntity(se);
                    httpPost.setHeader("Accept", "application/json");
                    httpPost.setHeader("Content-type", "application/json");
                    HttpResponse httpResponse = httpclient.execute(httpPost);

                    inputStream = httpResponse.getEntity().getContent();
                    if(inputStream != null)
                        result = convertInputStreamToString(inputStream);
                    else
                        result = "Did not work!";
                   String responsepost= GlobalVariables.reemplazarUnicode(result);
                    Resultado=responsepost;
                    JSONObject respJSON = new JSONObject(Resultado);
                    CodRegistro = respJSON.getString("CodPersona");

                }
                catch (Exception e) {
                    e.printStackTrace();
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
        if(opcion=="post") {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(v.getContext(), "Loading", "Iniciando sesion");
        }

    }
    @Override
    protected  void onPostExecute(Void result){
        try {
            if (opcion == "post") {

                if(CodRegistro.equals("null")){

                    //Thread.sleep(8000);
                    progressDialog.dismiss();
                    Toast.makeText(v.getContext(),"Usuario y/o contraseña incorrecta",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(v.getContext(),"logueo correcto",Toast.LENGTH_SHORT).show();

                    //Intent intent = new Intent(v.getContext(), ActFiltro.class);
                    //v.getContext().startActivity(intent);
                    //Thread.sleep(2000);
                    progressDialog.dismiss();

                }

            }
        }catch (Exception ex){
            Log.w("Error",ex);
        }
    }
}