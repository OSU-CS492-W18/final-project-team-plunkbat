package com.mobiledev.pubgtracker.utils;
import android.provider.BaseColumns;
/**
 * Created by stphn on 3/18/2018.
 */

public class SearchContract {

    private SearchContract() {}

    public static class SavedPlayers implements BaseColumns {
        public static final String TABLE_NAME = "savedPlayers";
        public static final String COLUMN_PLAYERS = "Players";
    }
}