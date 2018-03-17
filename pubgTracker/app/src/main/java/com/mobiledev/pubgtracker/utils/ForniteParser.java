package com.mobiledev.pubgtracker.utils;

import android.nfc.Tag;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Stephen on 3/16/2018.
 */

public class ForniteParser{

    public static StatObject Parser(String jsonObj) {
        try {
            JSONObject searchObj = new JSONObject(jsonObj);
            StatObject obj = new StatObject();
            obj.accountID = searchObj.getString("accountId");
            obj.PlatformNameLong = searchObj.getString("platformNameLong");
            obj.epicUserHandle = searchObj.getString("epicUserHandle");
            //solostats
            obj.solo.score = searchObj.getJSONObject("stats").getJSONObject("p2").
                    getJSONObject("score").getInt("valueInt");
            obj.solo.scoreRank = searchObj.getJSONObject("stats").getJSONObject("p2").
                    getJSONObject("score").getInt("rank");
            obj.solo.kills = searchObj.getJSONObject("stats").getJSONObject("p2").
                    getJSONObject("kills").getInt("valueInt");
            obj.solo.wins = searchObj.getJSONObject("stats").getJSONObject("p2").
                    getJSONObject("top1").getInt("valueInt");
            obj.solo.topTen = searchObj.getJSONObject("stats").getJSONObject("p2").
                    getJSONObject("top10").getInt("valueInt");
            obj.solo.topTenRank = searchObj.getJSONObject("stats").getJSONObject("p2").
                    getJSONObject("top10").getInt("rank");
            obj.solo.topTwentyFive = searchObj.getJSONObject("stats").getJSONObject("p2").
                    getJSONObject("top25").getInt("valueInt");
            obj.solo.kdr = searchObj.getJSONObject("stats").getJSONObject("p2").
                    getJSONObject("kd").getDouble("valueDec");
            obj.solo.timePlayed = searchObj.getJSONObject("stats").getJSONObject("p2").
                    getJSONObject("minutesPlayed").getString("displayValue");
            obj.solo.killsPerMin = searchObj.getJSONObject("stats").getJSONObject("p2").
                    getJSONObject("kpm").getInt("valueDec");
            obj.solo.killsPerMatch = searchObj.getJSONObject("stats").getJSONObject("p2").
                    getJSONObject("kpg").getInt("valueDec");
            obj.solo.avgMatchTime = searchObj.getJSONObject("stats").getJSONObject("p2").
                    getJSONObject("avgTimePlayed").getString("displayValue");
            //dou code
            obj.dou.score = searchObj.getJSONObject("stats").getJSONObject("p10").
                    getJSONObject("score").getInt("valueInt");
            obj.dou.scoreRank = searchObj.getJSONObject("stats").getJSONObject("p10").
                    getJSONObject("score").getInt("rank");
            obj.dou.kills = searchObj.getJSONObject("stats").getJSONObject("p10").
                    getJSONObject("kills").getInt("valueInt");
            obj.dou.wins = searchObj.getJSONObject("stats").getJSONObject("p10").
                    getJSONObject("top1").getInt("valueInt");
            obj.dou.topTen = searchObj.getJSONObject("stats").getJSONObject("p10").
                    getJSONObject("top10").getInt("valueInt");
            obj.dou.topTenRank = searchObj.getJSONObject("stats").getJSONObject("p10").
                    getJSONObject("top10").getInt("rank");
            obj.dou.topTwentyFive = searchObj.getJSONObject("stats").getJSONObject("p10").
                    getJSONObject("top25").getInt("valueInt");
            obj.dou.kdr = searchObj.getJSONObject("stats").getJSONObject("p10").
                    getJSONObject("kd").getDouble("valueDec");
            obj.dou.timePlayed = searchObj.getJSONObject("stats").getJSONObject("p10").
                    getJSONObject("minutesPlayed").getString("displayValue");
            obj.dou.killsPerMin = searchObj.getJSONObject("stats").getJSONObject("p10").
                    getJSONObject("kpm").getInt("valueDec");
            obj.dou.killsPerMatch = searchObj.getJSONObject("stats").getJSONObject("p10").
                    getJSONObject("kpg").getInt("valueDec");
            obj.dou.avgMatchTime = searchObj.getJSONObject("stats").getJSONObject("p10").
                    getJSONObject("avgTimePlayed").getString("displayValue");
            //squad code
            obj.squad.score = searchObj.getJSONObject("stats").getJSONObject("p9").
                    getJSONObject("score").getInt("valueInt");
            obj.squad.scoreRank = searchObj.getJSONObject("stats").getJSONObject("p9").
                    getJSONObject("score").getInt("rank");
            obj.squad.kills = searchObj.getJSONObject("stats").getJSONObject("p9").
                    getJSONObject("kills").getInt("valueInt");
            obj.squad.wins = searchObj.getJSONObject("stats").getJSONObject("p9").
                    getJSONObject("top1").getInt("valueInt");
            obj.squad.topTen = searchObj.getJSONObject("stats").getJSONObject("p2").
                    getJSONObject("top10").getInt("valueInt");
            obj.squad.topTenRank = searchObj.getJSONObject("stats").getJSONObject("p9").
                    getJSONObject("top10").getInt("rank");
            obj.squad.topTwentyFive = searchObj.getJSONObject("stats").getJSONObject("p9").
                    getJSONObject("top25").getInt("valueInt");
            obj.squad.kdr = searchObj.getJSONObject("stats").getJSONObject("p9").
                    getJSONObject("kd").getDouble("valueDec");
            obj.squad.timePlayed = searchObj.getJSONObject("stats").getJSONObject("p9").
                    getJSONObject("minutesPlayed").getString("displayValue");
            obj.squad.killsPerMin = searchObj.getJSONObject("stats").getJSONObject("p9").
                    getJSONObject("kpm").getInt("valueDec");
            obj.squad.killsPerMatch = searchObj.getJSONObject("stats").getJSONObject("p9").
                    getJSONObject("kpg").getInt("valueDec");
            obj.squad.avgMatchTime = searchObj.getJSONObject("stats").getJSONObject("p9").
                    getJSONObject("avgTimePlayed").getString("displayValue");

            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
            StatObject temp = new StatObject();
            temp.epicUserHandle = "Error";
            return  temp;
        }
        //return null;
    }

    public static class StatObject{
        public String accountID = "";
        public String PlatformNameLong = "";
        public String epicUserHandle= "";
        public Solo solo = new Solo();
        public Dou dou = new Dou();
        public Squad squad = new Squad();

        public class Solo
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
        public class Dou
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
        public class Squad
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
