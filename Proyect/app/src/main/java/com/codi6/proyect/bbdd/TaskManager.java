package com.codi6.proyect.bbdd;

import com.codi6.proyect.model.Task;

import io.realm.RealmResults;

/**
 * @author:unaisainz
 */

public interface TaskManager {

    /**
     * @return devuelve un objeto task que tenga el titulo que pasamos por parametro
     * @param title
     */
    Task findTask(final String title);

    /**
     * @return: devuelve todos los objetos TASKs que tenemos almacenado en la base de datos
     * @param
     */
    RealmResults<Task> findTasks();

    /**
     * @return: no devuele nada.
     * @param task
     * @throws: lanza una excepcion si el objeto no se ha guardado.
     */
    void insertTask(final Task task);

    /**
     * @param title
     */
    void removeTask(final String title);

    void deleteCompleted();
}
