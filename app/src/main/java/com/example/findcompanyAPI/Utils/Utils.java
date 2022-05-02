package com.example.findcompanyAPI.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {
    public static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNW = cm.getActiveNetworkInfo();
        if (activeNW != null && activeNW.isConnected()) {
            return true;
        }
        return false;
    }
}
