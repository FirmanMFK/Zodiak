package com.firman.zodiaq.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firman.zodiaq.Konstan;
import com.firman.zodiaq.R;
import com.firman.zodiaq.adapter.RecyclerBookmarks;
import com.firman.zodiaq.database.model.RMDataLahiran;
import com.firman.zodiaq.model.MsgBusPesan;
import com.firman.zodiaq.presenter.BookmarkPresenters;
import com.firman.zodiaq.viewcontract.HalBookmarkContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Firman on 3/24/2017.
 */

public class FragmentBookmarks extends Fragment implements HalBookmarkContract.View {

    private Unbinder mUnbinder;

    @BindView(R.id.recycler_bookmark)
    RecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;

    private List<RMDataLahiran> mDataLahiranList;
    private RMDataLahiran mRMDataLahiran;
    private RecyclerBookmarks mRecyclerBookmarks;
    private RecyclerBookmarks.OnButtonDeleteClickListener mOnButtonDeleteClickListener;

    private View.OnClickListener mOnClickListenerTombol;

    private BookmarkPresenters mPresenters;
    private boolean isInisialisasiOk = false;
    private boolean isDataAda = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        mUnbinder = ButterKnife.bind(FragmentBookmarks.this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenters = new BookmarkPresenters(FragmentBookmarks.this);
        mPresenters.setContext(FragmentBookmarks.this.getActivity());

        inisialisasiDataAwal();

        inisialisasiListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(FragmentBookmarks.this)) {
            EventBus.getDefault().register(FragmentBookmarks.this);
        }

        mPresenters.aktifkanRealm();
        mPresenters.ambilDataDatabase();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(FragmentBookmarks.this)) {
            EventBus.getDefault().unregister(FragmentBookmarks.this);
        }

        mPresenters.hentikanRealm();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Subscribe
    public void onMessageAktivitasFragmentEvent(MsgBusPesan msgBusPesan) {

        int kodepesan = msgBusPesan.getKode();

        if (kodepesan == Konstan.KODE_BUS_HAPUS_SEMUA) {
            hapusDataDatabase();
        }
    }


    @Override
    public void inisialisasiDataAwal() {

        mDataLahiranList = new ArrayList<>();
        mRMDataLahiran = new RMDataLahiran();
    }

    @Override
    public void inisialisasiTampilan() {

        mLinearLayoutManager = new LinearLayoutManager(FragmentBookmarks.this.getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRecyclerBookmarks = new RecyclerBookmarks(FragmentBookmarks.this.getActivity(), mDataLahiranList);
        mRecyclerBookmarks.setOnButtonDeleteClickListener(mOnButtonDeleteClickListener);
        mRecyclerView.setAdapter(mRecyclerBookmarks);

        isInisialisasiOk = true;
    }

    @Override
    public void inisialisasiListener() {
        mOnButtonDeleteClickListener = new RecyclerBookmarks.OnButtonDeleteClickListener() {
            @Override
            public void onButtonDeleteClick(int posisi, RMDataLahiran rmDataLahiran) {

                mRMDataLahiran = rmDataLahiran;

                hapusSatuDataDatabase();
            }
        };

        mOnClickListenerTombol = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
    }


    @Override
    public void addDataBaruDatabase(List<RMDataLahiran> list) {

        mDataLahiranList = list;

        if (isInisialisasiOk) {

            mRecyclerBookmarks.hapusData();
            mRecyclerBookmarks.tambahData(list);
            mRecyclerBookmarks.segarkanData();

        } else {
            inisialisasiTampilan();
        }

        if (mDataLahiranList.isEmpty()) {
            isDataAda = false;
        } else {
            isDataAda = true;
        }

        kirimPesanBusResetMenu();
    }

    @Override
    public void hapusDataDatabase() {

        mPresenters.hapusDataDatabase();
    }

    @Override
    public void hapusSatuDataDatabase() {

        mPresenters.hapusSatuDataDatabase(mRMDataLahiran);
    }

    @Override
    public void tampilPesanPeringatanView(int resID) {

        Snackbar.make(mRecyclerView, resID, Snackbar.LENGTH_LONG)
                .setAction("OK", mOnClickListenerTombol)
                .setActionTextColor(ContextCompat.getColor(FragmentBookmarks.this.getContext(), R.color.colorAccent))
                .show();
    }


    private void kirimPesanBusResetMenu() {

        MsgBusPesan msgBusPesan = new MsgBusPesan();
        msgBusPesan.setKode(Konstan.KODE_BUS_INVALIDASI_MENU_HAPUS);
        msgBusPesan.setDataAda(isDataAda);

        EventBus.getDefault().post(msgBusPesan);
    }
}
