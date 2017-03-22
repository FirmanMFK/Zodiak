package com.firman.zodiaq.viewcontract;

import com.firman.zodiaq.database.model.RMDataLahiran;

import java.util.List;

/**
 * Created by Firman on 3/22/2017.
 */

public interface HalBookmarkContract {

    interface View {
        //Inisialisasi Data Awal
        void inisialisasiDataAwal();

        //inisialisasi tampilan
        void inisialisasiTampilan();

        //inisialisasi listener
        void inisialisasiListener();

        //tambah data baru dari database
        void addDataBaruDatabase(List<RMDataLahiran> list);

        //hapus data database
        void hapusDataDatabase();

        //hapus satu data database
        void hapusSatuDataDatabase();

        //tampil pesan peringatan
        void tampilPesanPeringatanView(int resID);
    }

    interface Presenter {

        //aktifkan realmdb
        void aktifkanRealm();

        //hentikan realm
        void hentikanRealm();

        //ambil data dari database
        void ambilDataDatabase();

        //setel data ke tampilan
        void setelDataTampilan(List<RMDataLahiran> list);

        //hapus data database
        void hapusDataDatabase();

        //hapus satu data database
        void hapusSatuDataDatabase(RMDataLahiran rmDataLahiran);

        //tampil pesan alert
        void tampilPesanPeringatanView(int resID);
    }
}
