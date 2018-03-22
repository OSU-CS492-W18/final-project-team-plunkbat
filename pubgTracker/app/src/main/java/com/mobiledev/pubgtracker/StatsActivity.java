package com.mobiledev.pubgtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by warrickc on 3/16/2018.
 */

public class StatsActivity extends AppCompatActivity {


    private TextView tvPlayerName;
    private TextView tvTimePlayed;
    private TextView tvAvgMatchTime;
    private TextView tvScore;
    private TextView tvWins;
    private TextView tvWinPercent;
    private TextView tvKills;
    private TextView tvKDRatio;
    private TextView tvKPMin;
    private TextView tvKPMatch;
    private TextView tvScorePerMatch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_activity);

        tvPlayerName = (TextView)findViewById(R.id.tv_player_name);
        tvTimePlayed = (TextView)findViewById(R.id.tv_time_played);
        tvAvgMatchTime = (TextView)findViewById(R.id.tv_avg_match_time);
        tvScore = (TextView)findViewById(R.id.tv_score);
        tvWins = (TextView)findViewById(R.id.tv_wins);
        tvWinPercent = (TextView)findViewById(R.id.tv_win_percent);
        tvKills = (TextView)findViewById(R.id.tv_kills);
        tvKDRatio = (TextView)findViewById(R.id.tv_kill_death);
        tvKPMin = (TextView)findViewById(R.id.tv_kpmin);
        tvKPMatch = (TextView)findViewById(R.id.tv_kpmatch);
        tvScorePerMatch = (TextView)findViewById(R.id.tv_score_per_match);
    }
}
