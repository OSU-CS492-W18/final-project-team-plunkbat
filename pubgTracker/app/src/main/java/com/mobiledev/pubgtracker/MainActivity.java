package com.mobiledev.pubgtracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.IOException;
import java.util.ArrayList;

import static com.mobiledev.pubgtracker.utils.NetworkUtils.doHTTPGet;
import com.mobiledev.pubgtracker.utils.DBHelper;
import com.mobiledev.pubgtracker.utils.FortniteParser;
import com.mobiledev.pubgtracker.utils.SearchContract;

//Main page,search bar allows to search for user and click by name.
public class MainActivity extends AppCompatActivity implements TrackAdapter.ItemClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    String BASE_URL = "https://api.fortnitetracker.com/v1/profile/pc/";
    String resultJSON;
    TrackAdapter mAdapter;
    SQLiteDatabase mDB;
    FortniteParser.StatObject object = new FortniteParser.StatObject();
    EditText enteredPlayer;

    static String EXTRA = "extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this);
        mDB = dbHelper.getWritableDatabase();
        loadData();
        enteredPlayer = (EditText)findViewById(R.id.et_search_box);

        ImageButton searchButton = (ImageButton)findViewById(R.id.button_search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchQuery = enteredPlayer.getText().toString();

                if (!TextUtils.isEmpty(searchQuery)) {
                    new trackerSearch().execute(BASE_URL+searchQuery);
                }

            }
        });
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
                Log.d("URL", urls[0]);
                resultJSON = doHTTPGet(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resultJSON;
        }

        @Override
        protected void onPostExecute(String s) {

            Log.d(TAG,"onPostExecute/StringParam:" + s);

            if(s != null) {
                object = FortniteParser.Parser(s);

                // Check if the query returned anything before adding it to the DB
                ContentValues row = new ContentValues();
                row.put(SearchContract.SavedPlayers.COLUMN_PLAYERS, object.epicUserHandle);
                mDB.insert(SearchContract.SavedPlayers.TABLE_NAME, null, row);

                Log.d(TAG, s);
                Log.d(TAG, "Testing name: " + object.epicUserHandle);
                Log.d(TAG, "Testing solo win: " + String.valueOf(object.gameModeStats.get(0).wins)); //solo
                Log.d(TAG, "Testing dou win: " + String.valueOf(object.gameModeStats.get(1).wins)); //dou
                Log.d(TAG, "Testing squad win: " + String.valueOf(object.gameModeStats.get(2).wins)); //squad
                Log.d(TAG, "Testing solo win: " + String.valueOf(object.gameModeStats.get(0).score)); //solo
                Log.d(TAG, "Testing dou win: " + String.valueOf(object.gameModeStats.get(1).score)); //dou
                Log.d(TAG, "Testing squad win: " + String.valueOf(object.gameModeStats.get(2).score)); //squad

                Intent statsIntent = new Intent(MainActivity.this, StatsActivity.class);
                statsIntent.putExtra(EXTRA, object);
                startActivity(statsIntent);
            }
        }

    }
    // for loading data base of recent searches
    void loadData(){
        ArrayList<String> playerData = new ArrayList<>();
        Log.d(TAG, "Loading db stuff");
        Cursor c = mDB.query(SearchContract.SavedPlayers.TABLE_NAME, null, null, null, null, null, null);

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
