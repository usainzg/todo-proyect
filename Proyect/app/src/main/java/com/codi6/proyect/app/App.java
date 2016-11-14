package com.codi6.proyect.app;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by usainzg on 07/11/2016.
 */

public class App extends Application {
    @Override
    public void onCreate() {

        super.onCreate();

        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("myrealm.realm")
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm realm = Realm.getInstance(config);
    }

}
