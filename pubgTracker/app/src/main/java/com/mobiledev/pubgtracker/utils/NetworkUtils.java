package com.mobiledev.pubgtracker.utils;

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
        Request req = new Request.Builder().url(url).header("TRN-Api-Key", "5cacd83e-3861-42ff-a981-c0c1a81ff6c4").build();
        Response res = mHTTPClient.newCall(req).execute();
        try {
            return res.body().string();
        } finally {
            res.close();
        }
    }
}
