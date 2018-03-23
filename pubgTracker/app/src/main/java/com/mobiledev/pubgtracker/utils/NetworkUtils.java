package com.mobiledev.pubgtracker.utils;

import com.mobiledev.pubgtracker.MainActivity;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by stphn on 3/7/2018.
 */

public class NetworkUtils {

    private static final OkHttpClient mHTTPClient = new OkHttpClient();



    public static String doHTTPGet(String url) throws IOException {
        Request req = new Request.Builder().url(url).header(MainActivity.HEADER, MainActivity.API_KEY).build();
        Response res = mHTTPClient.newCall(req).execute();
        try {
            return res.body().string();
        } finally {
            res.close();
        }
    }
}
