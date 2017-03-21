package com.firman.zodiaq.database;

import android.content.Context;

import io.realm.RealmConfiguration;

/**
 * Created by Firman on 3/22/2017.
 */

public class RealmConfigs {

    public RealmConfigs() {

    }

    public static RealmConfiguration getRealmConfigs(Context context) {
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder(context);
        builder.schemaVersion(1);
        builder.deleteRealmIfMigrationNeeded();
        return builder.build();
    }
}
