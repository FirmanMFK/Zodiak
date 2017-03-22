package com.firman.zodiaq.internet;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.firman.zodiaq.internet.model.Lahiran;
import com.firman.zodiaq.internet.volley.FastJsonRequest;
import com.firman.zodiaq.internet.volley.LinkParsers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Firman on 3/22/2017.
 */

public class ApiVolley {

    public static int JUMLAH_TIMEOUT = 15000;
    public static int JUMLAH_COBA = 2;
    public static float PENGALI_TIMEOUT = 1;
    public static final String TAG_HEADERCONTENTTIPE = "Content-Type";
    public static final String HEADER_FORMTYPE = "application/json;charset=utf-8";

    public ApiVolley() {

    }

    private static DefaultRetryPolicy getRetryPolicy() {
        return new DefaultRetryPolicy(JUMLAH_TIMEOUT, JUMLAH_COBA, PENGALI_TIMEOUT);
    }

    public static FastJsonRequest<Lahiran> getDataKelahiran(
            String namalengkap,
            String tanggalLahir,
            Response.Listener<Lahiran> listener,
            Response.ErrorListener errorListener
    ) {

        Map<String, String> mapHeaders = new HashMap<>();
        mapHeaders.put(TAG_HEADERCONTENTTIPE, HEADER_FORMTYPE);

        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("nama", namalengkap);
        mapParams.put("tgl", tanggalLahir);

        String urlParamsLinks = LinkParsers.setParamsLinks(mapParams);
        String urls = Konstan.ALAMAT_SERVER + "/api/zodiak?" + urlParamsLinks;

        FastJsonRequest<Lahiran> fastJsonRequest = new FastJsonRequest<>(
                Request.Method.GET,
                urls,
                Lahiran.class,
                mapHeaders,
                mapParams,
                listener,
                errorListener
        );

        fastJsonRequest.setRetryPolicy(getRetryPolicy());

        return fastJsonRequest;
    }

}
