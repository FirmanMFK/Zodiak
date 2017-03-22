package com.firman.zodiaq.database;

import android.content.Context;

import com.firman.zodiaq.R;
import com.firman.zodiaq.database.model.RMDataLahiran;
import com.firman.zodiaq.viewcontract.HalBookmarkContract;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Firman on 3/22/2017.
 */

public class RepoBookmark implements RepositoryContract.BookmarkRepos {

    private HalBookmarkContract.Presenter mPresenter;

    private Context mContext;

    private Realm mRealm;
    private RealmConfiguration mRealmConfiguration;
    private RealmAsyncTask mRealmAsyncTaskQuery;
    private RealmAsyncTask mRealmAsyncTaskHapus;
    private RealmAsyncTask mRealmAsyncTaskHapus1;

    private List<RMDataLahiran> mDataLahiranList;

    public RepoBookmark(HalBookmarkContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public void aktifkanRealm() {
        mRealmConfiguration = RealmConfigs.getRealmConfigs(mContext);
        if (mRealm == null) {
            mRealm = Realm.getInstance(mRealmConfiguration);
        } else {
            if (mRealm.isClosed()) {
                mRealm = Realm.getInstance(mRealmConfiguration);
            }
        }
    }

    @Override
    public void hentikanRealm() {
        if (mRealmAsyncTaskQuery != null) {
            mRealmAsyncTaskQuery.cancel();
        }

        if (mRealmAsyncTaskHapus != null) {
            mRealmAsyncTaskHapus.cancel();
        }

        if (mRealmAsyncTaskHapus1 != null) {
            mRealmAsyncTaskHapus1.cancel();
        }

        if (mRealm != null) {
            mRealm.close();
        }
    }

    @Override
    public void ambilDatabase() {

        mRealmAsyncTaskQuery = mRealm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmQuery<RMDataLahiran> realmQuery = realm.where(RMDataLahiran.class);
                        RealmResults<RMDataLahiran> realmResults = realmQuery.findAllSorted("nama", Sort.ASCENDING);

                        if (!realmResults.isEmpty()) {
                            mDataLahiranList = realm.copyFromRealm(realmResults);
                        } else {
                            mDataLahiranList = new ArrayList<>();
                        }

                    }
                },
                new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        //kirim ke presenter dan view
                        //untuk setel data baru ke dalam halaman bookmark
                        mPresenter.setelDataTampilan(mDataLahiranList);
                    }
                },
                new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {

                        error.printStackTrace();
                        mDataLahiranList = new ArrayList<>();

                        //kirim ke presenter dan view
                        //untuk setel data baru ke dalam halaman bookmark
                        mPresenter.setelDataTampilan(mDataLahiranList);
                    }
                }
        );
    }

    @Override
    public void hapusSemuaDatabase() {

        mRealmAsyncTaskHapus = mRealm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        RealmQuery<RMDataLahiran> realmQuery = realm.where(RMDataLahiran.class);
                        RealmResults<RMDataLahiran> realmResults = realmQuery.findAllSorted("nama", Sort.ASCENDING);

                        if (!realmResults.isEmpty()) {
                            realmResults.deleteAllFromRealm();
                        }
                    }
                },
                new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {

                        //ambil ulang database
                        mPresenter.tampilPesanPeringatanView(R.string.pesantoast_datadihapus);

                        ambilDatabase();

                    }
                },
                new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {

                        error.printStackTrace();

                        //kirim pesan peringatan
                        mPresenter.tampilPesanPeringatanView(R.string.pesantoast_gagal_datadihapus);

                        //ambil ulang database
                        ambilDatabase();
                    }
                }
        );

    }

    @Override
    public void hapusSatuDataDatabase(final RMDataLahiran rmDataLahiran) {


        mRealmAsyncTaskHapus1 = mRealm.executeTransactionAsync(

                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        String idDataLahiran = rmDataLahiran.getIdLahiran();

                        RealmQuery<RMDataLahiran> realmQuery = realm.where(RMDataLahiran.class).equalTo("idLahiran", idDataLahiran);
                        RealmResults<RMDataLahiran> realmResults = realmQuery.findAll();

                        if (!realmResults.isEmpty()) {
                            realmResults.deleteAllFromRealm();
                        }
                    }
                },
                new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {

                        //ambil ulang database
                        mPresenter.tampilPesanPeringatanView(R.string.pesantoast_datadihapus);

                        ambilDatabase();

                    }
                },
                new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {

                        error.printStackTrace();

                        //kirim pesan peringatan
                        mPresenter.tampilPesanPeringatanView(R.string.pesantoast_gagal_datadihapus);

                        //ambil ulang database
                        ambilDatabase();
                    }
                }
        );

    }
}
