package com.mobiledev.pubgtracker.utils;

import android.nfc.Tag;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Stephen on 3/16/2018.
 */

public class ForniteParser{

    public static StatObject Parser(String jsonObj) {
        try {
            JSONObject searchObj = new JSONObject(jsonObj);
            StatObject obj = new StatObject();
            if(searchObj.has("accountId"))
            {
                Log.d(TAG, "ITS HAS A NAME");
                obj.accountID = searchObj.getString("accountId");
            }
            if(searchObj.has("platformNameLong"))
            {
                obj.PlatformNameLong = searchObj.getString("platformNameLong");
            }
            if(searchObj.has("epicUserHandle"))
            {
                obj.epicUserHandle = searchObj.getString("epicUserHandle");
            }
            for(int i = 0; i < 3; i++)
            {
                StatObject.GameModeStats gm = new StatObject.GameModeStats();
                String mode = "p2";
                String modeClass = "solo";

                if(i == 1)
                {
                    mode = "p10";
                    modeClass = "dou";
                }
                if(i ==2)
                {
                    mode = "p9";
                    modeClass = "squad";
                }
                if(searchObj.getJSONObject("stats").getJSONObject(mode).
                        has("score"))
                {
                    gm.score = searchObj.getJSONObject("stats").getJSONObject(mode).
                            getJSONObject("score").getInt("valueInt");
                    if(searchObj.getJSONObject("stats").getJSONObject(mode).
                            getJSONObject("score").has("rank"))
                    {
                        gm.scoreRank= searchObj.getJSONObject("stats").getJSONObject(mode).
                                getJSONObject("score").getInt("rank");
                    }
                }
                if(searchObj.getJSONObject("stats").getJSONObject(mode).
                        has("kills"))
                {
                    gm.kills= searchObj.getJSONObject("stats").getJSONObject(mode).
                            getJSONObject("kills").getInt("valueInt");
                }
                if(searchObj.getJSONObject("stats").getJSONObject(mode).
                        has("top1")) {
                    gm.wins = searchObj.getJSONObject("stats").getJSONObject(mode).
                            getJSONObject("top1").getInt("valueInt");
                }
                if(searchObj.getJSONObject("stats").getJSONObject(mode).
                        has("top10"))
                {
                    gm.topTen= searchObj.getJSONObject("stats").getJSONObject(mode).
                            getJSONObject("top10").getInt("valueInt");
                    if(searchObj.getJSONObject("stats").getJSONObject(mode).
                            getJSONObject("top10").has("rank")){
                        gm.topTenRank= searchObj.getJSONObject("stats").getJSONObject(mode).
                                getJSONObject("top10").getInt("rank");
                    }

                }
                if(searchObj.getJSONObject("stats").getJSONObject(mode).
                        has("top25"))
                {
                    gm.topTwentyFive = searchObj.getJSONObject("stats").getJSONObject(mode).
                            getJSONObject("top25").getInt("valueInt");
                }
                if(searchObj.getJSONObject("stats").getJSONObject(mode).
                        has("kd")) {
                    gm.kdr = searchObj.getJSONObject("stats").getJSONObject(mode).
                            getJSONObject("kd").getDouble("valueDec");
                }
                if(searchObj.getJSONObject("stats").getJSONObject(mode).
                        has("minutesPlayed"))
                {
                   gm.timePlayed= searchObj.getJSONObject("stats").getJSONObject(mode).
                            getJSONObject("minutesPlayed").getString("displayValue");
                }
                if(searchObj.getJSONObject("stats").getJSONObject(mode).
                        has("kpm"))
                {
                    gm.killsPerMin = searchObj.getJSONObject("stats").getJSONObject(mode).
                            getJSONObject("kpm").getInt("valueDec");
                }
                if(searchObj.getJSONObject("stats").getJSONObject(mode).
                        has("kpg"))
                {
                    gm.killsPerMatch= searchObj.getJSONObject("stats").getJSONObject(mode).
                            getJSONObject("kpg").getInt("valueDec");
                }
                if(searchObj.getJSONObject("stats").getJSONObject(mode).
                        has("avgTimePlayed"))
                {
                    gm.avgMatchTime= searchObj.getJSONObject("stats").getJSONObject(mode).
                            getJSONObject("avgTimePlayed").getString("displayValue");
                }
                obj.gameModeStats.add(gm);
            }
            Log.d(TAG, obj.epicUserHandle);
            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
            StatObject temp = new StatObject();
            temp.epicUserHandle = "Error";
            return  temp;
        }
        //return null;
    }

    public static class  StatObject implements Serializable{
         String accountID = "";
        public String PlatformNameLong = "";
        public String epicUserHandle= "";

        public static ArrayList<GameModeStats> gameModeStats = new ArrayList<GameModeStats>();

        public static class GameModeStats
        {
            public int score = -1;
            public int scoreRank = -1;
            public int kills= -1;
            public int wins= -1;
            public int topTen= -1;
            public int topTenRank= -1;
            public int topTwentyFive= -1;
            public double kdr= -1;
            public String timePlayed= "";
            public double killsPerMin= -1;
            public double killsPerMatch= -1;
            public String avgMatchTime= "";
        }
    }
}
