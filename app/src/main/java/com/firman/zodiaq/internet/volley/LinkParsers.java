package com.firman.zodiaq.internet.volley;

import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Firman on 3/22/2017.
 */

public class LinkParsers {

    public LinkParsers() {

    }

    //Ubah Parameters ke bentuk Link quey untuk request tipe get
    public static String setParamsLinks(Map<String, String> mapParams) {

        String urlLinks = "";
        StringBuilder stringBuilder = new StringBuilder();

        if (mapParams != null) {
            for (String key : mapParams.keySet()) {
                Object value = mapParams.get(key);
                if (value != null) {
                    try {

                        String valuesLink = URLEncoder.encode(String.valueOf(value), "UTF-8");
                        if (stringBuilder.length() > 0) {
                            stringBuilder.append("&");
                        }

                        stringBuilder.append(key);
                        stringBuilder.append("=");
                        stringBuilder.append(valuesLink);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            urlLinks = stringBuilder.toString();
        }
        return urlLinks;
    }
}