package com.codi6.proyect.bbdd;

import com.codi6.proyect.model.Task;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by unaisainz on 8/11/16.
 */

public class ManagerDb implements TaskManager {

    private Realm realm;

    public ManagerDb(){
        realm = Realm.getDefaultInstance();
    }

    @Override
    public RealmResults<Task> findTask(String title) {
        RealmResults<Task> tasks = realm.where(Task.class).equalTo("title", title).findAll();
        return tasks;
    }

    @Override
    public RealmResults<Task> findTasks() {

        RealmResults<Task> tasks = realm.where(Task.class).findAllSorted("title", Sort.ASCENDING);
        return tasks;
    }


    public void closeAll(){
        if(realm != null) realm.close();
     }
}