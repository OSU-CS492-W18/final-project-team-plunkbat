package com.mobiledev.pubgtracker.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Stephen on 3/16/2018.
 */

public class FortniteParser {

    private static final String TAG = FortniteParser.class.getSimpleName();

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

            obj.gameModeStats.clear();

            for(int i = 0; i < 3; i++)
            {
                StatObject.GameModeStats gm = new StatObject.GameModeStats();

                String mode = "p2";

                if(i == 1)
                {
                    mode = "p10";
                }
                if(i == 2)
                {
                    mode = "p9";
                }
                if(searchObj.getJSONObject("stats").has(mode)) {
                    if (searchObj.getJSONObject("stats").getJSONObject(mode).
                            has("score")) {
                        gm.score = searchObj.getJSONObject("stats").getJSONObject(mode).
                                getJSONObject("score").getInt("valueInt");
                        if (searchObj.getJSONObject("stats").getJSONObject(mode).
                                getJSONObject("score").has("rank")) {
                            gm.scoreRank = searchObj.getJSONObject("stats").getJSONObject(mode).
                                    getJSONObject("score").getInt("rank");
                        }
                    }
                    if (searchObj.getJSONObject("stats").getJSONObject(mode).
                            has("kills")) {
                        gm.kills = searchObj.getJSONObject("stats").getJSONObject(mode).
                                getJSONObject("kills").getInt("valueInt");
                    }

                    if (searchObj.getJSONObject("stats").getJSONObject(mode).
                            has("scorePerMatch")) {
                        gm.scorePerMatch = searchObj.getJSONObject("stats").getJSONObject(mode).
                                getJSONObject("scorePerMatch").getDouble("valueDec");
                    }

                    if (searchObj.getJSONObject("stats").getJSONObject(mode).
                            has("top1")) {
                        gm.wins = searchObj.getJSONObject("stats").getJSONObject(mode).
                                getJSONObject("top1").getInt("valueInt");
                    }
                    if (searchObj.getJSONObject("stats").getJSONObject(mode).
                            has("top10")) {
                        gm.topTen = searchObj.getJSONObject("stats").getJSONObject(mode).
                                getJSONObject("top10").getInt("valueInt");
                        if (searchObj.getJSONObject("stats").getJSONObject(mode).
                                getJSONObject("top10").has("rank")) {
                            gm.topTenRank = searchObj.getJSONObject("stats").getJSONObject(mode).
                                    getJSONObject("top10").getInt("rank");
                        }

                    }
                    if (searchObj.getJSONObject("stats").getJSONObject(mode).
                            has("top25")) {
                        gm.topTwentyFive = searchObj.getJSONObject("stats").getJSONObject(mode).
                                getJSONObject("top25").getInt("valueInt");
                    }
                    if (searchObj.getJSONObject("stats").getJSONObject(mode).
                            has("kd")) {
                        gm.kdr = searchObj.getJSONObject("stats").getJSONObject(mode).
                                getJSONObject("kd").getDouble("valueDec");
                    }
//                    if (searchObj.getJSONObject("stats").getJSONObject(mode).
//                            has("minutesPlayed")) {
//                        gm.timePlayed = searchObj.getJSONObject("stats").getJSONObject(mode).
//                                getJSONObject("minutesPlayed").getString("displayValue");
//                    }
//                    if (searchObj.getJSONObject("stats").getJSONObject(mode).
//                            has("kpm")) {
//                        gm.killsPerMin = searchObj.getJSONObject("stats").getJSONObject(mode).
//                                getJSONObject("kpm").getDouble("valueDec");
//                    }
                    if (searchObj.getJSONObject("stats").getJSONObject(mode).
                            has("matches")) {
                        gm.numMatches = searchObj.getJSONObject("stats").getJSONObject(mode).
                                getJSONObject("matches").getInt("valueInt");
                    }
                    if (searchObj.getJSONObject("stats").getJSONObject(mode).
                            has("kpg")) {
                        gm.killsPerMatch = searchObj.getJSONObject("stats").getJSONObject(mode).
                                getJSONObject("kpg").getInt("valueDec");
                    }
                    if (searchObj.getJSONObject("stats").getJSONObject(mode).
                            has("avgTimePlayed")) {
                        gm.avgMatchTime = searchObj.getJSONObject("stats").getJSONObject(mode).
                                getJSONObject("avgTimePlayed").getString("displayValue");
                    }

                    if (searchObj.getJSONObject("stats").getJSONObject(mode).
                            has("avgTimePlayed")) {
                        gm.avgTimeDoub = searchObj.getJSONObject("stats").getJSONObject(mode).
                                getJSONObject("avgTimePlayed").getLong("valueDec");
                    }

                }
                obj.gameModeStats.add(gm);
            }
            Log.d(TAG, obj.epicUserHandle);
            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
            StatObject temp = new StatObject();
            temp.epicUserHandle = "Error";
            return temp;
        }
        //return null;
    }

    public static class StatObject implements Serializable{
         String accountID = "";
        public String PlatformNameLong = "";
        public String epicUserHandle= "";

        public static ArrayList<GameModeStats> gameModeStats = new ArrayList<GameModeStats>();

        public static class GameModeStats implements Serializable
        {
            public int score = -1;
            public int scoreRank = -1;
            public int kills= -1;
            public int wins= -1;
            public int topTen= -1;
            public int topTenRank= -1;
            public int topTwentyFive= -1;
            public double kdr= -1;
//            public String timePlayed= "";
//            public double killsPerMin= -1;
            public double killsPerMatch= -1;
            public String avgMatchTime= "-1";
            public int numMatches = -1;
            public long avgTimeDoub = -1;
            public double scorePerMatch = -1;
        }
    }

    public static boolean PlayerFound(String jsonString){
        try {
            JSONObject searchObj = new JSONObject(jsonString);
            if(searchObj.has("epicUserHandle"))
                return true;
            else
                return false;

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    //return false;
    }
}
