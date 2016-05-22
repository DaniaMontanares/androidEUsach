package com.example.dania.myapplication;


import android.content.BroadcastReceiver;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.dania.myapplication.Controllers.HttpGet;
import com.example.dania.myapplication.Controllers.HttpPost;
import com.example.dania.myapplication.Utilities.JsonHandler;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.dania.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.dania.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void text_msg(View view){
        Intent intento = new Intent(this,recuperarContrasena.class);
        startActivity(intento);
    }

    private BroadcastReceiver br = null;
    private final String URL_GET = "http://10.0.2.2:8080/EventoUsachJava/usuarios";

    public void onButtonClick(View v) throws ExecutionException, InterruptedException {
        if(v.getId() == R.id.buttonLogin) {
            HttpGet c=new HttpGet(this.getApplicationContext());
            c.execute(URL_GET);
            Bundle arguments = new Bundle();
            String  item= null;
            try{
                item = c.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e){
            e.printStackTrace();
        }
            EditText correo = (EditText) findViewById(R.id.editTextCorreo);
            String corr = correo.getText().toString();
            EditText contrasena = (EditText) findViewById(R.id.editTextPass);
            String pass = contrasena.getText().toString();
            String usuario = " "+ corr + " "+ pass;
            Log.d("Deb2",item);
            JsonHandler jh = new JsonHandler();
            String[] userList = jh.getMailPass(item);
            int j=0;
            while(j<userList.length){
                if(userList[j].equals(usuario)){
                    j=userList.length;
                    Intent i = new Intent(this, perfilUsuario.class);
                    startActivity(i);
                }
                else{
                    j++;
                }
            }
        }
    }

}

