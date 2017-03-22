package com.firman.zodiaq.database;

import com.firman.zodiaq.database.model.RMDataLahiran;
import com.firman.zodiaq.internet.model.Lahiran;

/**
 * Created by Firman on 3/22/2017.
 */

public class RepositoryContract {

    interface HalUtamaRepos {

        //aktifkan realm
        void aktifkanRealm();

        //hentikan realm
        void hentikanRealm();

        //simpan data ke database
        void simpanDataDatabase(Lahiran lahiran);
    }

    interface BookmarkRepos {

        //aktifkan realm
        void aktifkanRealm();

        //hentikan realm
        void hentikanRealm();

        //ambil database
        void ambilDatabase();

        //hapus Database
        void hapusSemuaDatabase();

        //hapus satu data database
        void hapusSatuDataDatabase(RMDataLahiran rmDataLahiran);
    }
}
