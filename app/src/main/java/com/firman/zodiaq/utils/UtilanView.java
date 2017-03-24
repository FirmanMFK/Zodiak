package com.firman.zodiaq.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * Created by Firman on 3/23/2017.
 */

public class UtilanView {

    //menampilkan menu action bar
    public static void munculMenuAction(Context context) {
        try {
            ViewConfiguration config = ViewConfiguration.get(context);
            Field menuKey = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");

            if (menuKey != null) {
                menuKey.setAccessible(true);
                menuKey.setBoolean(config, false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Hide keyboard
    public static void sembunyikeyboard(Context context, View view) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    //TextView Separator
    public static String formatAngkaPisah(String angkastr) {

        String bilanganpisah;

        try {
            int intangka = Integer.valueOf(angkastr);
            bilanganpisah = String.format(Locale.getDefault(), "%,d", intangka);

        } catch (Exception e) {
            e.printStackTrace();
            bilanganpisah = "" + angkastr;
        }

        return bilanganpisah;
    }
}
