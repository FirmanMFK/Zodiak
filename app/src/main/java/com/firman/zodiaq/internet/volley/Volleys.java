package com.firman.zodiaq.internet.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Firman on 3/22/2017.
 */

public class Volleys {

    private static final String TAG = Volleys.class.getSimpleName();

    private static Volleys sVolleys;
    private RequestQueue mRequestQueue;
    private static Context sContext;
    private OkHttp3Stack mOkHttp3Stack;

    public Volleys(Context context) {
        sContext = context;
        mOkHttp3Stack = new OkHttp3Stack();
    }

    public static synchronized Volleys getInstance(Context context) {
        if (sVolleys == null) {
            sVolleys = new Volleys(context);
        }
        return sVolleys;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(sContext.getApplicationContext(), mOkHttp3Stack);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, Object tag) {

        // set the default tag if tag is empty
        req.setTag(tag == null ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelRequestConnection(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
            mOkHttp3Stack.cancelRequestOnGoing(tag);
        }
    }

    public void cancelRequestNoTagConnection() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
            mOkHttp3Stack.cancelRequestOnGoing(TAG);
        }
    }

    public void clearVolleyCache() {
        if (mRequestQueue != null) {
            mRequestQueue.getCache().clear();
        }
    }
}
