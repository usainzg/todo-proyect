package com.codi6.proyect.bbdd;

import com.codi6.proyect.model.Task;

import io.realm.RealmResults;

/**
 * Created by unaisainz on 9/11/16.
 */

public interface TaskManager {
    Task findTask(final String id);
    RealmResults<Task> findTasks();
    void insertTask(final Task task);
    void removeTask(final String id);
    void deleteCompleted();
}
