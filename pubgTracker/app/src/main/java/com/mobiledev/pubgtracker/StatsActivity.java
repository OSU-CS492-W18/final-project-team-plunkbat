package com.mobiledev.pubgtracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

        int modeNum = getModeNum(mode);

        Log.d("Mode", Integer.toString(modeNum));

        if(modeNum >= 0){

            long nMatches = mResult.gameModeStats.get(modeNum).numMatches;

            if(nMatches>0) {
                long avgTime = mResult.gameModeStats.get(modeNum).avgTimeDoub;
                long time = avgTime * nMatches;

                int kills = mResult.gameModeStats.get(modeNum).kills;

                Double killsPerMin = Math.round(((double)kills / time) * 1000.00) / 1000.0;

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
                tvWinPercent.setText(Double.toString((mResult.gameModeStats.get(modeNum).wins)/(nMatches)));
                tvKills.setText(Integer.toString(kills));
                tvKDRatio.setText(Double.toString(mResult.gameModeStats.get(modeNum).kdr));
                tvKPMin.setText(Double.toString(killsPerMin));
                tvKPMatch.setText(Double.toString(mResult.gameModeStats.get(modeNum).killsPerMatch));
                tvScorePerMatch.setText(Integer.toString(mResult.gameModeStats.get(modeNum).score));
            } else {
                long time = 0;

                String timePlayed = String.format(
                        "%02dD:%02dH:%02dM:%02dS",
                        TimeUnit.SECONDS.toDays(time),
                        TimeUnit.SECONDS.toHours(time) - TimeUnit.DAYS.toHours(TimeUnit.SECONDS.toDays(time)),
                        TimeUnit.SECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(time)),
                        TimeUnit.SECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(time)));

                tvTimePlayed.setText(timePlayed);
                Log.d("Time:", timePlayed);
                tvAvgMatchTime.setText("N/A");
                Log.d("AvgTime:", "N/A");
                tvScore.setText("N/A");
                tvWins.setText("N/A");
                tvWinPercent.setText("N/A");
                tvKills.setText("N/A");
                tvKDRatio.setText("N/A");
                tvKPMin.setText("N/A");
                tvKPMatch.setText("N/A");
                tvScorePerMatch.setText("N/A");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.player_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_online:
                viewStatsInBrowser();
                return true;
            case R.id.action_share:
                shareStats();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void viewStatsInBrowser() {
        if (mResult != null) {
            Uri playerFNStatsURL = Uri.parse(MainActivity.BASE_SITE + mResult.epicUserHandle);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, playerFNStatsURL);
            if (webIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(webIntent);
            }
        }
    }

    public void shareStats() {
        if (mResult != null) {

            int modeNum = getModeNum(checkButton());

            if (modeNum >= 0) {

                long nMatches = mResult.gameModeStats.get(modeNum).numMatches;

                if (nMatches > 0) {
                    long avgTime = mResult.gameModeStats.get(modeNum).avgTimeDoub;
                    long time = avgTime * nMatches;

                    double kills = mResult.gameModeStats.get(modeNum).kills;

                    Double killsPerMin = Math.round((kills / time) * 1000.00) / 1000.0;

                    String timePlayed = String.format(
                            "%02dD:%02dH:%02dM:%02dS",
                            TimeUnit.SECONDS.toDays(time),
                            TimeUnit.SECONDS.toHours(time) - TimeUnit.DAYS.toHours(TimeUnit.SECONDS.toDays(time)),
                            TimeUnit.SECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(time)),
                            TimeUnit.SECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(time)));

                    String avgMatchTime = mResult.gameModeStats.get(modeNum).avgMatchTime;
//                    long score = mResult.gameModeStats.get(modeNum).score;
                    int wins = mResult.gameModeStats.get(modeNum).wins;
                    double winPercent = mResult.gameModeStats.get(modeNum).wins / nMatches;
//                    int kills = mResult.gameModeStats.get(modeNum).kills;
                    double kdr = mResult.gameModeStats.get(modeNum).kdr;
                    double killsPerMatch = mResult.gameModeStats.get(modeNum).killsPerMatch;
                    long score = mResult.gameModeStats.get(modeNum).score;


                    String shareText = getString(R.string.share_text_prefix) + " - \n" +
                            mResult.epicUserHandle + " playing " + checkButton() +" on " + mResult.PlatformNameLong + ": \n" +
                            "Wins: "+wins+"/WinPct: "+winPercent+"/ModeScore: "+score+"\n"+
                            "Kills: "+kills+"/KillsMatch: "+killsPerMatch+"/KDR: "+kdr+"\n"+
                            "AvgMatchTime: "+avgMatchTime;


                    ShareCompat.IntentBuilder.from(this)
                            .setChooserTitle(R.string.share_app_choose_titlebar)
                            .setType("text/plain" )
                            .setText(shareText)
                            .startChooser();
                }
            }
        }
    }

    private int getModeNum(String modeText) {
        int modeNum;
        switch (modeText) {
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

        return modeNum;
    }
}
