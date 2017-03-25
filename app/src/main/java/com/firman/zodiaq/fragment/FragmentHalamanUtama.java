package com.firman.zodiaq.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.firman.zodiaq.R;
import com.firman.zodiaq.internet.model.Lahiran;
import com.firman.zodiaq.presenter.HalUtamaPresenter;
import com.firman.zodiaq.utils.UtilanView;
import com.firman.zodiaq.viewcontract.HalUtamaContract;

import org.joda.time.DateTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Firman on 3/25/2017.
 */

public class FragmentHalamanUtama extends Fragment implements HalUtamaContract.View {

    private Unbinder mUnbinder;

    @BindView(R.id.edit_namalengkap)
    EditText mEditTextNamaOrang;
    @BindView(R.id.teks_isian_tanggalahir)
    TextView mTextViewWaktuLahir;
    @BindView(R.id.tombol_setelkalender)
    Button mButtonTombolSetelTanggal;
    @BindView(R.id.tombol_cek_lahir)
    Button mButtonTombolCekLahir;

    @BindView(R.id.teks_datahasil_namalengkap)
    TextView mTextViewHasilNamaLengkap;
    @BindView(R.id.teks_datahasil_waktulahir)
    TextView mTextViewHasilWaktuLahir;
    @BindView(R.id.teks_datahasil_zodiak)
    TextView mTextViewHasilZodiak;
    @BindView(R.id.teks_datahasil_usia)
    TextView mTextViewHasilUsia;
    @BindView(R.id.tombol_simpan_kelahiran)
    Button mButtonTombolSimpan;


    private HalUtamaPresenter mPresenter;

    private View.OnClickListener mOnClickListenerTombol;
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private DialogInterface.OnCancelListener mOnCancelListenerDialogProgress;

    private DatePickerDialog mDatePickerDialogTanggal;
    private ProgressDialog mProgressDialogInternet;

    private DateTime mDateTimeSet;
    private int yearDate;
    private int monthOfYearDate;
    private int dayOfMonthDate;

    private String mStringNamaPengguna;
    private String mStringTanggalLahiran;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mUnbinder = ButterKnife.bind(FragmentHalamanUtama.this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new HalUtamaPresenter(FragmentHalamanUtama.this);
        mPresenter.setContext(FragmentHalamanUtama.this.getActivity());

        //inisialisasi data awal
        inisialisasiDataAwal();

        //inisialisasi listener
        inisialisasiListener();

        //inisialisasi tampilan
        inisialisasiTampilan();
    }

    @Override
    public void onStart() {
        super.onStart();

        mPresenter.aktifkanRealm();
    }

    @Override
    public void onStop() {
        super.onStop();

        mPresenter.hentikanRealm();
        mPresenter.hentikanProsesMuatData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void inisialisasiDataAwal() {

        mDateTimeSet = new DateTime();
    }

    @Override
    public void inisialisasiTampilan() {

        setelDataTanggalTeks();

        mProgressDialogInternet = new ProgressDialog(FragmentHalamanUtama.this.getActivity());
        mProgressDialogInternet.setMessage("Memuat data kelahiran...");
        mProgressDialogInternet.setOnCancelListener(mOnCancelListenerDialogProgress);

        mDatePickerDialogTanggal = new DatePickerDialog(FragmentHalamanUtama.this.getActivity(),
                mOnDateSetListener, yearDate, monthOfYearDate - 1, dayOfMonthDate);

        mButtonTombolSetelTanggal.setOnClickListener(mOnClickListenerTombol);
        mButtonTombolCekLahir.setOnClickListener(mOnClickListenerTombol);
        mButtonTombolSimpan.setOnClickListener(mOnClickListenerTombol);
    }

    @Override
    public void inisialisasiListener() {
        mOnClickListenerTombol = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UtilanView.sembunyikeyboard(FragmentHalamanUtama.this.getActivity(), view);

                int id = view.getId();

                switch (id) {

                    case R.id.tombol_setelkalender:

                        tampilDialogPilihTanggal();
                        break;

                    case R.id.tombol_cek_lahir:

                        ambilDataIsianPengguna();
                        break;

                    case R.id.tombol_simpan_kelahiran:

                        mPresenter.simpanDataIsianHasilAmbilData();
                        break;
                }
            }
        };

        mOnCancelListenerDialogProgress = new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {

                mPresenter.hentikanProsesMuatData();
            }
        };

        mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                mDateTimeSet = new DateTime().withDate(year, monthOfYear + 1, dayOfMonth);

                setelDataTanggalTeks();
            }
        };

    }

    @Override
    public void tampilDialogPilihTanggal() {

        try {
            mDatePickerDialogTanggal.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setelDataTanggalTeks() {

        yearDate = mDateTimeSet.year().get();
        monthOfYearDate = mDateTimeSet.monthOfYear().get();
        dayOfMonthDate = mDateTimeSet.dayOfMonth().get();

        String tanggalpertamakaliMuncul = mPresenter.setelTanggalTampilan(dayOfMonthDate, monthOfYearDate,
                yearDate);

        mTextViewWaktuLahir.setText(tanggalpertamakaliMuncul);
    }

    @Override
    public void ambilDataIsianPengguna() {

        mStringNamaPengguna = mEditTextNamaOrang.getText().toString();
        mStringTanggalLahiran = mTextViewWaktuLahir.getText().toString();

        mPresenter.setDataIsianPengguna(mStringNamaPengguna, mStringTanggalLahiran);
    }

    @Override
    public void tampilPesanPeringatan(int resID) {

        Snackbar.make(mEditTextNamaOrang, resID, Snackbar.LENGTH_LONG)
                .setAction("OK", mOnClickListenerTombol)
                .setActionTextColor(ContextCompat.getColor(FragmentHalamanUtama.this.getContext(), R.color.colorAccent))
                .show();
    }

    @Override
    public void tampilkanHasilCekTanggalLahir(Lahiran lahiran) {

        Log.w("AMBIL OK", "SUKSES AMBIL DATA");

        String nama = lahiran.getNama();
        String lahir = lahiran.getLahir();
        String usia = lahiran.getUsia();
        String zodiak = lahiran.getZodiak();

        mTextViewHasilNamaLengkap.setText(nama);
        mTextViewHasilWaktuLahir.setText(lahir);
        mTextViewHasilUsia.setText(usia);
        mTextViewHasilZodiak.setText(zodiak);
    }

    @Override
    public void tampilDialogProses(boolean isProses) {

        try {
            if (isProses) {
                mProgressDialogInternet.show();
            } else {
                mProgressDialogInternet.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
