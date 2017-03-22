package com.firman.zodiaq.viewcontract;

import com.firman.zodiaq.internet.model.Lahiran;

/**
 * Created by Firman on 3/22/2017.
 */

public interface HalUtamaContract {

    interface View {

        //insialisasi data awal
        void inisialisasiDataAwal();

        //inisialisasi tampilan
        void inisialisasiTampilan();

        //inisialisasi listener
        void inisialisasiListener();

        //tampilkan dialog pilih tanggal
        void tampilDialogPilihTanggal();

        //setel data tanggal ke isian teks tanggal
        void setelDataTanggalTeks();

        //ambil data isian pengguna
        void ambilDataIsianPengguna();

        //tampilakn pesan peringatan
        void tampilPesanPeringatan(int resID);

        //tampilkan hasil cek tanggal lahir
        void tampilkanHasilCekTanggalLahir(Lahiran lahiran);

        //tampilkan dialog proses atau tidak
        void tampilDialogProses(boolean isProses);

    }

    interface Presenter {

        //aktifkan realm
        void aktifkanRealm();

        //hentikan realm
        void hentikanRealm();

        //hentikan proses muat data
        void hentikanProsesMuatData();

        //setel tanggal lahir pengguna
        void setDataIsianPengguna(String namaPengguna, String stringTanggalLahir);

        //cek koneksi internet
        void cekKoneksiInternet();

        //ambil data dari internet
        void ambilDataDariInternet();

        //cek hasil ambil data
        void cekHasilAmbilDataPengguna();

        //setel data ke tampilan
        void setelDataTampilan();

        //simpan data isian hasil ambil data
        void simpanDataIsianHasilAmbilData();

        //set status penyegaran data
        void setStatusDisegarkanData(boolean isDisegarkan);

        //tampilkan pesan peringatan ke view
        void tampilPesanPeringatan(int resID);

        //setel tanggal tampilan ke dalam bentuk format
        String setelTanggalTampilan(int tanggal, int intbulan, int inttahun);
    }
}
