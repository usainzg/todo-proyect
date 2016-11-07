package com.codi6.proyect.app;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by usainzg on 07/11/2016.
 */

public class App extends Application{

    @Override
    public void onCreate(){

        super.onCreate();

        /*RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);*/
    }
}
