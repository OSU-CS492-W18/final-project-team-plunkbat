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
public class MainActivity extends AppCompatActivity implements TrackAdapter.OnSavedPlayerClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String BASE_SITE = "https://fortnitetracker.com/profile/pc/";
    public static final String API_URL = "https://api.fortnitetracker.com/v1/profile/pc/";
    public static final String API_KEY = "5cacd83e-3861-42ff-a981-c0c1a81ff6c4";
    public static final String HEADER = "TRN-Api-Key";
    String resultJSON;

    RecyclerView mSavedPlayerRV;
    TrackAdapter mAdapter;

    SQLiteDatabase mDB;
    FortniteParser.StatObject object = new FortniteParser.StatObject();
    EditText enteredPlayer;

    static String EXTRA = "extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSavedPlayerRV = findViewById(R.id.rv_last_searches);
        mSavedPlayerRV.setLayoutManager(new LinearLayoutManager(this));
        mSavedPlayerRV.setHasFixedSize(true);

        DBHelper dbHelper = new DBHelper(this);
        mDB = dbHelper.getWritableDatabase();

        mAdapter = new TrackAdapter(this);
        mAdapter.updateSavedPlayers(loadData());
        mSavedPlayerRV.setAdapter(mAdapter);

        enteredPlayer = (EditText) findViewById(R.id.et_search_box);

        ImageButton searchButton = (ImageButton) findViewById(R.id.button_search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchQuery = enteredPlayer.getText().toString();

                if (!TextUtils.isEmpty(searchQuery)) {
                    new trackerSearch().execute(API_URL + searchQuery);
                }

            }
        });
    }

    //Simple hack to get RV to update correctly.
    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onDestroy() {
        mDB.close();
        super.onDestroy();
    }


    @Override
    public void onSavedPlayerClick(String savedPlayer) {
        Log.d(TAG, "onSavedPlayerClick - " + savedPlayer);

        deletePlayerDB(savedPlayer);
        new trackerSearch().execute(API_URL + savedPlayer);
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

            Log.d(TAG, "onPostExecute/StringParam:" + s);


            if (s != null) {
                if (FortniteParser.PlayerFound(s)) {
                    object = FortniteParser.Parser(s);

                    addPlayerDB(object.epicUserHandle);

                    Log.d(TAG, s);
                    Log.d(TAG, "Testing name: " + object.epicUserHandle);
//                    Log.d(TAG, "Testing solo win: " + String.valueOf(object.gameModeStats.get(0).wins)); //solo
//                    Log.d(TAG, "Testing dou win: " + String.valueOf(object.gameModeStats.get(1).wins)); //dou
//                    Log.d(TAG, "Testing squad win: " + String.valueOf(object.gameModeStats.get(2).wins)); //squad
//                    Log.d(TAG, "Testing solo win: " + String.valueOf(object.gameModeStats.get(0).score)); //solo
//                    Log.d(TAG, "Testing dou win: " + String.valueOf(object.gameModeStats.get(1).score)); //dou
//                    Log.d(TAG, "Testing squad win: " + String.valueOf(object.gameModeStats.get(2).score)); //squad

                    Intent statsIntent = new Intent(MainActivity.this, StatsActivity.class);
                    statsIntent.putExtra(EXTRA, object);
                    startActivity(statsIntent);
                } else {
                    Log.d(TAG, "onPostExecute() - Player not found"); //TODO Pop a toast?
                }
            } else {
                Log.d(TAG,"onPostExecute() - String param NULL.");
            }
        }

    }

    // for loading data base of recent searches
    private ArrayList<String> loadData() {
        Log.d(TAG, "Loading db stuff");
        Cursor c = mDB.query(SearchContract.SavedPlayers.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                SearchContract.SavedPlayers.COLUMN_TIMESTAMP + " DESC");

        ArrayList<String> playerUsernamesList = new ArrayList<>();

        while (c.moveToNext()) {
            String player = c.getString(c.getColumnIndex("Players"));
            Log.d(TAG, "adding " + player + " from DB to navBar");
            playerUsernamesList.add(player);
        }
        c.close();
        Log.d(TAG, "Done db stuff");

        return playerUsernamesList;
    }

    private boolean checkPlayerInDB(String s) {
        boolean isSaved = false;
        if (s != null) {
            String sqlSelection = SearchContract.SavedPlayers.COLUMN_PLAYERS + " = ?";
            String[] sqlSelectionArgs = { s };
            Cursor cursor = mDB.query(
                    SearchContract.SavedPlayers.TABLE_NAME,
                    null,
                    sqlSelection,
                    sqlSelectionArgs,
                    null,
                    null,
                    null
            );
            isSaved = cursor.getCount() > 0;
            cursor.close();
        }
        return isSaved;
    }

    public boolean deletePlayerDB(String name) {
        String[] args = new String[]{name};
        return mDB.delete(SearchContract.SavedPlayers.TABLE_NAME,
                SearchContract.SavedPlayers.COLUMN_PLAYERS + "=?",
                args) > 0;
    }

    public boolean addPlayerDB(String name) {
        boolean bool = false;
        if(!checkPlayerInDB(name)) {
            ContentValues row = new ContentValues();
            row.put(SearchContract.SavedPlayers.COLUMN_PLAYERS, name);
            bool = mDB.insert(SearchContract.SavedPlayers.TABLE_NAME, null, row) > 0;
        }
        return bool;
    }
}
