package com.mobiledev.pubgtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
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
    private RadioGroup rgGameType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_activity);

        //Intent intent = getIntent();
        //if (intent != null && intent.hasExtra(GitHubUtils.EXTRA_SEARCH_RESULT)) {
        //    mSearchResult = (GitHubUtils.SearchResult) intent.getSerializableExtra(GitHubUtils.EXTRA_SEARCH_RESULT);
        //    mTVSearchResultName.setText(mSearchResult.fullName);
        //    mTVSearchResultStars.setText(String.valueOf(mSearchResult.stars));
        //    mTVSearchResultDescription.setText(mSearchResult.description);
        //}

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

        rgGameType = (RadioGroup)findViewById(R.id.rd_gametype);

        rgGameType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.radio_solo){

                } else if (checkedId == R.id.radio_duo){

                } else if (checkedId == R.id.radio_squad){

                }
            }
        });
    }
}
