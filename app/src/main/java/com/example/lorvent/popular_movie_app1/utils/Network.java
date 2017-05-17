package com.example.lorvent.popular_movie_app1.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by 2naveen on 3/18/2017.
 */

public class Network {
     //Context context;
   /* public Network(Context context) {
        this.context=context;
    }*/

    public static boolean  isConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        } else {
            return false;

        }
    }

}
