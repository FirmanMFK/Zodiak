package com.firman.zodiaq.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.firman.zodiaq.Konstan;
import com.firman.zodiaq.R;
import com.firman.zodiaq.fragment.FragmentBookmarks;
import com.firman.zodiaq.model.MsgBusPesan;
import com.firman.zodiaq.utils.UtilanView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Firman on 3/25/2017.
 */

public class HalamanBookmarks extends AppCompatActivity {

    private Unbinder mUnbinder;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private ActionBar mActionBar;

    private boolean isDataAda = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_container);
        mUnbinder = ButterKnife.bind(HalamanBookmarks.this);
        UtilanView.munculMenuAction(HalamanBookmarks.this);

        if (EventBus.getDefault().isRegistered(HalamanBookmarks.this)) {
            EventBus.getDefault().register(HalamanBookmarks.this);
        }

        HalamanBookmarks.this.setSupportActionBar(mToolbar);
        mActionBar = HalamanBookmarks.this.getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(R.string.judul_bookmark_halaman);

        inisialisasiFragment();


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(HalamanBookmarks.this)) {
            EventBus.getDefault().register(HalamanBookmarks.this);

        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItemHapusSemua = menu.findItem(R.id.menu_hapus_semua);

        if (isDataAda) {
            menuItemHapusSemua.setEnabled(true).setVisible(true);
        } else {
            menuItemHapusSemua.setEnabled(false).setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        HalamanBookmarks.this.getMenuInflater().inflate(R.menu.menu_bookmarks, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                HalamanBookmarks.this.finish();
                return true;

            case R.id.menu_hapus_semua:
                kirimPesanBusHapusSemua();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Subscribe
    public void onMsgFragmentActivityEvent(MsgBusPesan msgBusPesan) {
        int kodepesan = msgBusPesan.getKode();
        if (kodepesan == Konstan.KODE_BUS_INVALIDASI_MENU_HAPUS) {
            isDataAda = msgBusPesan.isDataAda();
            resetMenuToolbar();
        }
    }

    private void kirimPesanBusHapusSemua() {
        MsgBusPesan msgBusPesan = new MsgBusPesan();
        msgBusPesan.setKode(Konstan.KODE_BUS_HAPUS_SEMUA);
        EventBus.getDefault().post(msgBusPesan);
    }

    //Reset Menu
    private void resetMenuToolbar() {
        HalamanBookmarks.this.supportInvalidateOptionsMenu();
    }

    //InisialisasiFragment

    private void inisialisasiFragment() {
        FragmentBookmarks fragmentBookmarks = new FragmentBookmarks();
        FragmentTransaction fragmentTransaction = HalamanBookmarks.this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_fragment, fragmentBookmarks);
        fragmentTransaction.commit();
    }
}