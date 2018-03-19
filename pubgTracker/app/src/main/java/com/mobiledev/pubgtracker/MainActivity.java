package com.mobiledev.pubgtracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;

import static com.mobiledev.pubgtracker.utils.NetworkUtils.doHTTPGet;
import com.mobiledev.pubgtracker.utils.DBHelper;
import com.mobiledev.pubgtracker.utils.SearchContract;
import com.mobiledev.pubgtracker.TrackAdapter;

//Main page,search bar allows to search for user and click by name.
public class MainActivity extends AppCompatActivity implements TrackAdapter.ItemClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    String apiKey = "5cacd83e-3861-42ff-a981-c0c1a81ff6c4";
    String URLTest = "https://api.fortnitetracker.com/v1/profile/pc/AlexRamiGaming";
    String jsonTest;
    TrackAdapter mAdapter;
    SQLiteDatabase mDB;
    Button searchButton;
    SearchContract mSearchContract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this);
        mDB = dbHelper.getWritableDatabase();
        loadData();
       // searchButton = (Button)findViewById(R.id.search_button);
       // searchButton.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View v) {
       //         //Still needs a logic for null
       //         mDB.insert("savedPlayers", "THIS NEEDS TO BE text entered by player", null);
       //     }
       // });

        new trackerSearch().execute();
    }

    @Override
    public void onItemClick(View view, int position) {

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
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonTest;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(String.valueOf(this), s);
        }

    }
    // for loading data base of recent searches
    void loadData(){
        ArrayList<String> playerData = new ArrayList<>();
        Log.d(TAG, "Loading db stuff");
        Cursor c = mDB.query(SearchContract.SavedRepos.TABLE_NAME, null, null, null, null, null, null);

        if(c!=null)
        {
            while(c.moveToNext())
            {
                String player = c.getString(c.getColumnIndex("Players"));
                Log.d(TAG, "adding "+ player +" from DB to navBar");
                playerData.add(player);
            }
        }
        Log.d(TAG, "Done db stuff");

        RecyclerView recyclerView = findViewById(R.id.rv_last_searches);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TrackAdapter(this, playerData);
        mAdapter.setClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }
}
