package com.firman.zodiaq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.firman.zodiaq.R;

/**
 * Created by Firman on 3/25/2017.
 */

public class Loadings extends AppCompatActivity {

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(Loadings.this, HalamanUtama.class);
            Loadings.this.startActivity(intent);
            Loadings.this.finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstancState) {
        super.onCreate(savedInstancState);
        setContentView(R.layout.loading_halaman);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Handler handler = new Handler();
        handler.postDelayed(mRunnable, 1000);
    }
}
