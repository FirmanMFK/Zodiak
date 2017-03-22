package com.firman.zodiaq.presenter;

import android.content.Context;

import com.firman.zodiaq.database.RepoBookmark;
import com.firman.zodiaq.database.model.RMDataLahiran;
import com.firman.zodiaq.viewcontract.HalBookmarkContract;

import java.util.List;

/**
 * Created by Firman on 3/22/2017.
 */

public class BookmarkPresenters implements HalBookmarkContract.Presenter {

    private HalBookmarkContract.View mView;

    private Context mContext;

    private RepoBookmark mRepoBookmark;

    public BookmarkPresenters(HalBookmarkContract.View view) {
        mView = view;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public void aktifkanRealm() {
        mRepoBookmark = new RepoBookmark(BookmarkPresenters.this);
        mRepoBookmark.setContext(mContext);
        mRepoBookmark.aktifkanRealm();
    }

    @Override
    public void hentikanRealm() {
        mRepoBookmark.hentikanRealm();
    }

    @Override
    public void ambilDataDatabase(){
        mRepoBookmark.ambilDatabase();
    }

    @Override
    public void setelDataTampilan(List<RMDataLahiran> list) {
        mView.addDataBaruDatabase(list);
    }

    @Override
    public void hapusDataDatabase() {
        mRepoBookmark.hapusSemuaDatabase();
    }

    @Override
    public void hapusSatuDataDatabase(RMDataLahiran rmDataLahiran) {
        mRepoBookmark.hapusSatuDataDatabase(rmDataLahiran);
    }

    @Override
    public void tampilPesanPeringatanView(int resID) {
        mView.tampilPesanPeringatanView(resID);
    }
}
