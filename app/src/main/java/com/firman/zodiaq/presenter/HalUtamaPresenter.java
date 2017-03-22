package com.firman.zodiaq.presenter;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.firman.zodiaq.R;
import com.firman.zodiaq.database.RepoHalamanUtama;
import com.firman.zodiaq.internet.ApiVolley;
import com.firman.zodiaq.internet.model.Lahiran;
import com.firman.zodiaq.internet.volley.FastJsonRequest;
import com.firman.zodiaq.internet.volley.Volleys;
import com.firman.zodiaq.utils.CekInternet;
import com.firman.zodiaq.viewcontract.HalUtamaContract;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

/**
 * Created by Firman on 3/23/2017.
 */

public class HalUtamaPresenter implements HalUtamaContract.Presenter {

    private static final String TAG_VOLLEY_HALAMAN_UTAMA = "" + HalUtamaPresenter.class.getSimpleName();

    private HalUtamaContract.View mView;
    private Context mContext;

    private String mStringNamaPengguna;
    private String mStringTanggalahir;
    private Lahiran mLahiran;

    private DateTimeFormatter mDateTimeFormatterIN;
    private CekInternet mCekInternet;
    private boolean isMuatDataInternet = false;
    private boolean isInternetAda = false;

    private RepoHalamanUtama mRepoHalamanUtama;

    public HalUtamaPresenter(HalUtamaContract.View view) {
        mView = view;

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy");
        mDateTimeFormatterIN = dateTimeFormatter.withLocale(new Locale("id"));
    }

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public void aktifkanRealm() {

        mRepoHalamanUtama = new RepoHalamanUtama(HalUtamaPresenter.this);
        mRepoHalamanUtama.setContext(mContext);
        mRepoHalamanUtama.aktifkanRealm();
    }

    @Override
    public void hentikanRealm() {

        mRepoHalamanUtama.hentikanRealm();
    }

    @Override
    public void hentikanProsesMuatData() {

        isMuatDataInternet = false;

        //hentikan koneksi volley
        Volleys.getInstance(mContext).cancelRequestConnection(TAG_VOLLEY_HALAMAN_UTAMA);
        Volleys.getInstance(mContext).clearVolleyCache();

        //hentikan progress dialog
        setStatusDisegarkanData(false);
    }

    @Override
    public void setDataIsianPengguna(String namaPengguna, String stringTanggalLahir) {

        if (namaPengguna.length() > 2) {

            if (stringTanggalLahir.length() > 2) {

                mStringNamaPengguna = namaPengguna;
                mStringTanggalahir = stringTanggalLahir;

                //cek koneksi internet
                cekKoneksiInternet();
            } else {
                //tampil pesan peringatan
                mView.tampilPesanPeringatan(R.string.toast_gagal_tanggalahir);
            }
        } else {
            //tampil pesan peringatan
            mView.tampilPesanPeringatan(R.string.toast_gagal_namapengguna);
        }
    }

    @Override
    public void cekKoneksiInternet() {

        mCekInternet = new CekInternet(mContext);
        isInternetAda = mCekInternet.cekStatusInternet();

        if (isInternetAda) {

            //jika statusnya bukan lagi proses muat data
            if (!isMuatDataInternet) {

                //tampilkan progress dialog
                setStatusDisegarkanData(true);

                ambilDataDariInternet();
            }
        } else {

            setStatusDisegarkanData(false);

            //tampil pesan peringatan
            mView.tampilPesanPeringatan(R.string.toast_koneksi_internet_gagal);
        }

    }

    @Override
    public void ambilDataDariInternet() {

        FastJsonRequest<Lahiran> fastJsonRequest = ApiVolley.getDataKelahiran(
                mStringNamaPengguna, mStringTanggalahir,
                new Response.Listener<Lahiran>() {
                    @Override
                    public void onResponse(Lahiran response) {

                        mLahiran = response;

                        //cek hasil ambil data
                        cekHasilAmbilDataPengguna();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();

                        mLahiran = null;

                        //cek hasil ambil data
                        cekHasilAmbilDataPengguna();
                    }
                }
        );

        Volleys.getInstance(mContext).addToRequestQueue(fastJsonRequest, TAG_VOLLEY_HALAMAN_UTAMA);
    }

    @Override
    public void cekHasilAmbilDataPengguna() {

        if (mLahiran != null) {

            //setel data ke tampilan
            setelDataTampilan();
        } else {

            //tampil pesan peringatan
            mView.tampilPesanPeringatan(R.string.toast_gagal_ambildata);
        }

        //hentikan proses penyegaran
        setStatusDisegarkanData(false);
    }

    @Override
    public void setelDataTampilan() {

        mView.tampilkanHasilCekTanggalLahir(mLahiran);
    }

    @Override
    public void simpanDataIsianHasilAmbilData() {

        if (mLahiran != null) {
            mRepoHalamanUtama.simpanDataDatabase(mLahiran);
        }
    }

    @Override
    public void setStatusDisegarkanData(boolean isDisegarkan) {

        try {
            if (isDisegarkan) {

                isMuatDataInternet = true;

                //ubah status progress bar
                mView.tampilDialogProses(true);
            } else {

                isMuatDataInternet = false;

                //ubah status progress bar
                mView.tampilDialogProses(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tampilPesanPeringatan(int resID) {

        mView.tampilPesanPeringatan(resID);
    }

    @Override
    public String setelTanggalTampilan(int tanggal, int intbulan, int inttahun) {

        String tanggalSetelan;
        DateTime dateTime;

        try {
            dateTime = new DateTime().withDate(inttahun, intbulan, tanggal);
            tanggalSetelan = dateTime.toString(mDateTimeFormatterIN);

        } catch (Exception ex) {
            ex.printStackTrace();
            dateTime = new DateTime();
            tanggalSetelan = dateTime.toString(mDateTimeFormatterIN);
        }

        return tanggalSetelan;
    }
}

