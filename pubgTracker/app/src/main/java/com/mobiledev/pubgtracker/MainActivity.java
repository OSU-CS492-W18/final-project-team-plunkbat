package com.mobiledev.pubgtracker;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import static com.mobiledev.pubgtracker.utils.NetworkUtils.doHTTPGet;


//Main page,search bar allows to search for user and click by name.
public class MainActivity extends AppCompatActivity {
    String apiKey = "5cacd83e-3861-42ff-a981-c0c1a81ff6c4";
    String URLTest = "https://api.fortnitetracker.com/v1/profile/pc/AlexRamiGaming";
    String jsonTest;
    //api.pubgtracker.com/v2/profile/pc/shroud/4d4729e6-c640-463f-bb72-c814c22eb26c
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        new trackerSearch().execute();

    }

    public class trackerSearch extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                jsonTest = doHTTPGet(URLTest);
              //  Log.d(String.valueOf(this), "somethiuung");
            } catch (IOException e) {
                e.printStackTrace();
               // Log.d(String.valueOf(this), "Error");
            }
            return jsonTest;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(String.valueOf(this), s);
        }

    }
}
