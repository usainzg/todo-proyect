package com.codi6.proyect.bbdd;

import com.codi6.proyect.model.Task;

import io.realm.RealmResults;

/**
 * @author:unaisainz
 */

public interface TaskManager {

    /**
     * @param title
     * @return devuelve un objeto task que tenga el titulo que pasamos por parametro
     */
    RealmResults<Task> findTask(final String title);

    /**
     * @param
     * @return: devuelve todos los objetos TASKs que tenemos almacenado en la base de datos
     */
    RealmResults<Task> findTasks();
}