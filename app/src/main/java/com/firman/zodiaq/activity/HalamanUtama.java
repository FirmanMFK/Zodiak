package com.firman.zodiaq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.firman.zodiaq.R;
import com.firman.zodiaq.fragment.FragmentHalamanUtama;
import com.firman.zodiaq.utils.UtilanView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Firman on 3/25/2017.
 */

public class HalamanUtama extends AppCompatActivity {

    private Unbinder mUnbinder;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_container);
        mUnbinder = ButterKnife.bind(HalamanUtama.this);
        UtilanView.munculMenuAction(HalamanUtama.this);

        HalamanUtama.this.setSupportActionBar(mToolbar);
        mActionBar = HalamanUtama.this.getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setTitle(R.string.app_name);

        inisialisasiFragment();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        HalamanUtama.this.getMenuInflater().inflate(R.menu.menu_utama, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_riwayatlahir:

                Intent intentRiwayat = new Intent(HalamanUtama.this, HalamanBookmarks.class);
                HalamanUtama.this.startActivity(intentRiwayat);
                return true;

            case R.id.menu_tentangaplikasi:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    //inisialisasi Fragment
    private void inisialisasiFragment() {
        FragmentHalamanUtama fragmentHalamanUtama = new FragmentHalamanUtama();
        FragmentTransaction fragmentTransaction = HalamanUtama.this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_fragment, fragmentHalamanUtama);
        fragmentTransaction.commit();
    }
}
