package com.codi6.proyect.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.codi6.proyect.R;
import com.codi6.proyect.bbdd.ManagerDb;
import com.codi6.proyect.model.Label;
import com.codi6.proyect.model.Task;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private ManagerDb managerDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        managerDb = new ManagerDb();
        Task task1 = new Task();
        task1.setId("t001");
        task1.setCreatedAt(new Date());
        task1.setLabel(new Label("Tareas"));
        task1.setDone(false);
        task1.setDescription("TAREA 1");
        task1.setTitle("Task 1 title");

        managerDb.addTask(task1);

        RealmResults<Task> resulta = managerDb.getTasks();

        for(Task t: resulta){
            Log.i("TASK", t.getTitle());
        }
    }
}
