package com.mobiledev.pubgtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mobiledev.pubgtracker.utils.FortniteParser;

import java.util.concurrent.TimeUnit;

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

    FortniteParser.StatObject mResult;


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

        rgGameType = (RadioGroup)findViewById(R.id.rd_gametype);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.EXTRA)) {
            mResult = (FortniteParser.StatObject) intent.getSerializableExtra(MainActivity.EXTRA);
            tvPlayerName.setText(mResult.epicUserHandle);

            updateView(checkButton());
        }


        rgGameType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                updateView(checkButton());
            }
        });
    }

    private String checkButton(){
        String selection = "Bad";
        if(rgGameType.getCheckedRadioButtonId()!=-1){
            int id = rgGameType.getCheckedRadioButtonId();
            View radioButton = rgGameType.findViewById(id);
            int radioId = rgGameType.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) rgGameType.getChildAt(radioId);
            selection = (String) btn.getText();
        }

        Log.d("Button: ", selection);

        return selection;
    }

    private void updateView(String mode){

            int modeNum;
            switch (mode){
                case "Solo":
                    modeNum = 0;
                    break;
                case "Duo":
                    modeNum = 1;
                    break;
                case "Squad":
                    modeNum = 2;
                    break;
                default:
                    modeNum = -1;
                    break;
            }

            Log.d("Mode", Integer.toString(modeNum));

            if(modeNum >= 0){

                long avgTime = mResult.gameModeStats.get(modeNum).avgTimeDoub;
                long nMatches = mResult.gameModeStats.get(modeNum).numMatches;
                long time = avgTime*nMatches;

                double kills = mResult.gameModeStats.get(modeNum).kills;

                Double killsPerMin = Math.round((kills/time) * 1000.00)/1000.0;

                String timePlayed = String.format(
                        "%02dD:%02dH:%02dM:%02dS",
                        TimeUnit.SECONDS.toDays(time),
                        TimeUnit.SECONDS.toHours(time) - TimeUnit.DAYS.toHours(TimeUnit.SECONDS.toDays(time)),
                        TimeUnit.SECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(time)),
                        TimeUnit.SECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(time)));

                tvTimePlayed.setText(timePlayed);
                Log.d("Time:", timePlayed);
                tvAvgMatchTime.setText(mResult.gameModeStats.get(modeNum).avgMatchTime);
                Log.d("AvgTime:", mResult.gameModeStats.get(modeNum).avgMatchTime);
                tvScore.setText(Integer.toString(mResult.gameModeStats.get(modeNum).score));
                tvWins.setText(Integer.toString(mResult.gameModeStats.get(modeNum).wins));
                tvWinPercent.setText(Double.toString(mResult.gameModeStats.get(modeNum).wins));
                tvKills.setText(Integer.toString(mResult.gameModeStats.get(modeNum).kills));
                tvKDRatio.setText(Double.toString(mResult.gameModeStats.get(modeNum).kdr));
                tvKPMin.setText(Double.toString(killsPerMin));
                tvKPMatch.setText(Double.toString(mResult.gameModeStats.get(modeNum).killsPerMatch));
                tvScorePerMatch.setText(Integer.toString(mResult.gameModeStats.get(modeNum).score));

            }
    }

}
