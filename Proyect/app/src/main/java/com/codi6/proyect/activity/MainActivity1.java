package com.codi6.proyect.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codi6.proyect.R;
import com.codi6.proyect.adapters.TaskRealmAdapter;
import com.codi6.proyect.bbdd.ManagerDb;
import com.codi6.proyect.model.Label;
import com.codi6.proyect.model.Task;

import java.util.Date;
import java.util.UUID;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import co.moonmonkeylabs.realmsearchview.RealmSearchView;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity1 extends AppCompatActivity
        implements OnNavigationItemSelectedListener, OnQueryTextListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;


    private com.getbase.floatingactionbutton.FloatingActionButton fabAdd, fabCamera;
    private Realm realm;
    private RealmRecyclerView realmRecyclerView;
    private TaskRealmAdapter taskRealmAdapter;
    private ManagerDb managerDb;
    private RealmSearchView realmSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        realmSearchView = (RealmSearchView) findViewById(R.id.search_view);

        managerDb = new ManagerDb();


        fabCamera = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.action_btn_camera);
        fabAdd = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.action_btn_add);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buildAndShowInputDialog();

            }
        });

        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCamera();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        realmRecyclerView = (RealmRecyclerView) findViewById(R.id.list);

        realm = Realm.getDefaultInstance();


        // hardcoded data for test
        /*
        Task task = new Task();
        task.setId("001");
        task.setTitle("TITLE 1");
        task.setCreatedAt(new Date());
        task.setLabel(new Label("Clase"));
        task.setDescription("Test task to prove app.");


        Task task2 = new Task();
        task2.setId("002");
        task2.setTitle("TITLE 2");
        task2.setCreatedAt(new Date());
        task2.setLabel(new Label("Trabajo"));
        task2.setDescription("Test task to prove app.");


        Task task3 = new Task();
        task3.setId("003");
        task3.setTitle("TITLE 3");
        task3.setCreatedAt(new Date());
        task3.setLabel(new Label("Deporte"));
        task3.setDescription("Test task to prove app.");


        Task task4 = new Task();
        task4.setId("004");
        task4.setTitle("TITLE 4");
        task4.setCreatedAt(new Date());
        task4.setLabel(new Label("Academia"));
        task4.setDescription("Test task to prove app.");

        Task task5 = new Task();
        task5.setId("005");
        task5.setTitle("TITLE 5");
        task5.setCreatedAt(new Date());
        task5.setLabel(new Label("Academia0"));
        task5.setDescription("Test task to prove app.");

        Task task6 = new Task();
        task6.setId("006");
        task6.setTitle("TITLE 6");
        task6.setCreatedAt(new Date());
        task6.setLabel(new Label("Academia1"));
        task6.setDescription("Test task to prove app.");

        Task task7 = new Task();
        task7.setId("007");
        task7.setTitle("TITLE 7");
        task7.setCreatedAt(new Date());
        task7.setLabel(new Label("Academia2"));
        task7.setDescription("Test task to prove app.");

        Task task8 = new Task();
        task8.setId("008");
        task8.setTitle("TITLE 8");
        task8.setCreatedAt(new Date());
        task8.setLabel(new Label("Ingles"));
        task8.setDescription("Test task to prove app.");
        // end test
        */

        /*
        managerDb.insertTask(task);
        managerDb.insertTask(task2);
        managerDb.insertTask(task3);
        managerDb.insertTask(task4);
        managerDb.insertTask(task5);
        managerDb.insertTask(task6);
        managerDb.insertTask(task7);
        managerDb.insertTask(task8);
        */

        RealmResults<Task> realmResults = realm.where(Task.class).findAllSorted("title", Sort.ASCENDING);

        taskRealmAdapter = new TaskRealmAdapter(getApplicationContext(), realmResults, false, false);

        realmRecyclerView.setAdapter(taskRealmAdapter);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);

        // TODO
        //realmSearchView
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tasks) {

        } else if (id == R.id.nav_labels) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_help) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // TODO
    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i("QUERY COMPLETED", query);
        return false;
    }

    // TODO
    @Override
    public boolean onQueryTextChange(String newText) {
        Log.i("ON THE FLY", newText);
        return false;
    }


    private void buildAndShowInputDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity1.this);
        builder.setTitle(R.string.dialog_main_title);

        LayoutInflater li = LayoutInflater.from(this);
        View dialogView = li.inflate(R.layout.task_dialog_view, null);
        final EditText inputTitle = (EditText) dialogView.findViewById(R.id.dialogTitleInput);
        final EditText inputDescription = (EditText) dialogView.findViewById(R.id.dialogDescriptionInput);

        builder.setView(dialogView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String uniqueId = UUID.randomUUID().toString();
                Task taskToIntro = new Task();
                taskToIntro.setId(uniqueId);
                taskToIntro.setTitle(inputTitle.getText().toString());
                taskToIntro.setDescription(inputDescription.getText().toString());
                taskToIntro.setCreatedAt(new Date());
                taskToIntro.setDone(false);

                managerDb.insertTask(taskToIntro);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        final AlertDialog dialog = builder.show();
        inputTitle.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                        // TODO implement add todo
                        return false;
                    }
                }
        );
    }

    private void showCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        realm = null;
    }
}
