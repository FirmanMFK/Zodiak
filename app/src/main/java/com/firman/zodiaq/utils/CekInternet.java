package com.firman.zodiaq.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Firman on 3/23/2017.
 */

public class CekInternet {

    private Context konteks;
    ConnectivityManager conmanager = null;
    NetworkInfo netinfo = null;
    boolean isInternet = false;

    public CekInternet(Context ctx) {
        isInternet = false;
        conmanager = null;
        netinfo = null;
        this.konteks = ctx;
        conmanager = (ConnectivityManager) konteks.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean cekStatusInternet() {
        netinfo = conmanager.getActiveNetworkInfo();
        isInternet = netinfo != null && netinfo.isConnected();
        return isInternet;
    }
}
