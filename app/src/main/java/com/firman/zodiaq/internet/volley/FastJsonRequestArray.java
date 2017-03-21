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
import java.util.List;
import java.util.Map;

import okio.BufferedSource;
import okio.Okio;

/**
 * Created by Firman on 3/22/2017.
 */

public class FastJsonRequestArray<T> extends Request<List<T>> {

    private final Class<T> mClass;
    private final Response.Listener<List<T>> mListenerOk;
    private final Map<String, String> headers;
    private final Map<String, String> params;

    public FastJsonRequestArray(int method,
                                String url,
                                Class<T> tClass,
                                Map<String, String> headers,
                                Map<String, String> paramsbody,
                                Response.Listener<List<T>> listenerSucces,
                                Response.ErrorListener listenerGagal) {
        super(method, url, listenerGagal);

        this.mClass = tClass;
        this.mListenerOk = listenerSucces;

        this.headers = headers;
        this.params = paramsbody;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        super.getBody();

        try {
            return JSON.toJSONString(params).getBytes(getParamsEncoding());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    @Override
    protected Response<List<T>> parseNetworkResponse(NetworkResponse response) {

        try {

            InputStream inputStream = new ByteArrayInputStream(response.data);
            BufferedSource bufferedSource = Okio.buffer(Okio.source(inputStream));

            String jsons = bufferedSource.readUtf8();
            bufferedSource.close();

            return Response.success(JSON.parseArray(jsons, mClass),
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (Exception ex) {
            return Response.error(new ParseError(ex));
        }
    }

    @Override
    protected void deliverResponse(List<T> response) {
        mListenerOk.onResponse(response);
    }
}
