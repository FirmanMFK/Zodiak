package com.firman.zodiaq.internet.volley;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import okio.BufferedSource;
import okio.Okio;


/**
 * Created by Firman on 3/22/2017.
 */

public class FastJsonRequest<T> extends Request<T> {

    private final Class<T> mClass;
    private final Response.Listener<T> mListenerOk;
    private final Map<String, String> headers;
    private final Map<String, String> params;

    public FastJsonRequest(int method,
                           String url,
                           Class<T> tClass,
                           Map<String, String> headers,
                           Map<String, String> parameterPost,
                           Response.Listener<T> listenerSucces,
                           Response.ErrorListener listenerGagal) {
        super(method, url, listenerGagal);

        this.mClass = tClass;
        this.mListenerOk = listenerSucces;

        this.headers = headers;
        this.params = parameterPost;
    }

    @Override
    public Map<String, String>getHeaders() throws  AuthFailureError{
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws  AuthFailureError {
        return params != null ? params : super.getParams();
    }

    @Override
    public byte[] getBody() throws AuthFailureError{
        super.getBody();

        //Konversi dari hashmap ke json string dan  jadikan bytes
        try {
            return JSON.toJSONString(params).getBytes(getParamsEncoding());

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {

            InputStream inputStream = new ByteArrayInputStream(response.data);
            BufferedSource bufferedSource = Okio.buffer(Okio.source(inputStream));

            String jsons = bufferedSource.readUtf8();
            bufferedSource.close();

            return Response.success(JSON.parseObject(jsons, mClass),
            HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception ex) {
            return Response.error(new ParseError(ex));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListenerOk.onResponse(response);
    }
}
