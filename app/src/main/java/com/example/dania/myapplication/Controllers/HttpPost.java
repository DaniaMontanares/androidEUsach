package com.example.dania.myapplication.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

/**
 * Clase que se utiliza para realizar peticiones HTTP mediante el método GET
 */
public class HttpPost extends AsyncTask<String, Void, String> {
    private Context context;

    /**
     * Constructor
     */
    public HttpPost(Context context) {
        this.context = context;
    }// HttpGet(Context context)

    /**
     * Método que realiza la petición al servidor
     */
    @Override
    protected String doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.connect();

            //Write
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(urls[1]);
            writer.close();
            os.close();
            int response = connection.getResponseCode();
            Log.d("Deb: ","The response is: " + response);
        } catch (MalformedURLException e) {
            Log.e("ERROR1", this.getClass().toString() + " " + e.toString());
        } catch (ProtocolException e) {
            Log.e("ERROR2", this.getClass().toString() + " " + e.toString());
        } catch (IOException e) {
            Log.e("ERROR3", this.getClass().toString() + " " + e.toString() + " " + urls[0]);
        }
        return null;
    }// doInBackground(String... urls)

    /**
     * Método que manipula la respuesta del servidor
     */
    @Override
    protected void onPostExecute(String result) {
        Intent intent = new Intent("httpData").putExtra("data", result);
        context.sendBroadcast(intent);
    }// onPostExecute(String result)

}// HttpGet extends AsyncTask<String, Void, String>