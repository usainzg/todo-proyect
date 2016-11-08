package com.codi6.proyect.bbdd;

import android.util.Log;

import com.codi6.proyect.model.Task;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by unaisainz on 8/11/16.
 */

public class ManagerDb {

    public void addTask(final Task task){

        try {
            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();

            realm.copyToRealm(task);

            realm.commitTransaction();

            realm.close();

        }catch(Exception e){
            Log.e("ERROR --> ", "error" + e.getMessage());
        }

    }

    public void removeTask(final String id){

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        RealmResults<Task> tasksToRemove =  realm.where(Task.class).equalTo("id", id).findAll();

        if(!tasksToRemove.isEmpty()){
            for(int i = tasksToRemove.size() - 1; i >= 0; i++) {
                tasksToRemove.get(i).deleteFromRealm();
            }
        }
        realm.commitTransaction();
        realm.close();
    }


    public RealmResults<Task> getTasks(){
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        RealmResults<Task> tasks = realm.where(Task.class).findAll();
        realm.commitTransaction();
        realm.close();
        return tasks;
    }






}
