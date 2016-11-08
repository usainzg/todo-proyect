package com.codi6.proyect.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by unaisainz on 8/11/16.
 */

public class Label extends RealmObject {

    @PrimaryKey
    private String nombre;
    private RealmList<Task> tasks;

    public Label(){}

    public Label(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public RealmList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(RealmList<Task> tasks) {
        this.tasks = tasks;
    }
}
