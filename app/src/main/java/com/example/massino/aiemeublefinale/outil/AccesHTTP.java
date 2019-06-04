package com.example.massino.aiemeublefinale.outil;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
/*
* Created by massino on 24/05/2019.
* */

public class AccesHTTP extends AsyncTask<String,Integer,Long> {


    private ArrayList<NameValuePair>parametres;
    private String ret=null;
    private AsyncReponse delegate=null;

    //constructeur
    public AccesHTTP() {
        parametres=new ArrayList<NameValuePair>();
    }

  //ajouter un paramétre post
    public void addParametre(String nom,String valeur){
        parametres.add(new BasicNameValuePair(nom,valeur));

    }

     //connexion en tache de fond
    @Override
    protected Long doInBackground(String... strings) {
        HttpClient cnxHTTP=new DefaultHttpClient();
        HttpPost paramCnx=new HttpPost(strings[0]);

        try {
            //encodage des parmetres
            paramCnx.setEntity(new UrlEncodedFormEntity(parametres));
            //connexion et envoi de paramétre avec attente de réponse
            HttpResponse reponse=cnxHTTP.execute(paramCnx);
            //transformation de la réponse
           ret = EntityUtils.toString(reponse.getEntity());


        } catch (UnsupportedEncodingException e) {
            Log.d("erreur encodage","*******"+e.toString());
        } catch (ClientProtocolException e) {
            Log.d("erreur de protocole","*******"+e.toString());
        } catch (IOException e) {
            Log.d("erreur d I/O","*******"+e.toString());
        }

        return null;
    }

    @Override
    protected  void onPostExecute(Long result){
     delegate.processFinish(ret.toString());
    }
}
