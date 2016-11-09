package com.codi6.proyect.activity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.Snackbar;
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
import com.codi6.proyect.model.Task;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity1 extends AppCompatActivity
        implements OnNavigationItemSelectedListener, OnQueryTextListener {

    private com.getbase.floatingactionbutton.FloatingActionButton fabAdd;
    private Realm realm;
    private RealmRecyclerView realmRecyclerView;
    private TaskRealmAdapter taskRealmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fabAdd = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.action_btn_add);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buildAndShowInputDialog();

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

        RealmResults<Task> realmResults = realm.where(Task.class).findAllSorted("title", Sort.DESCENDING);


        taskRealmAdapter = new TaskRealmAdapter(this, realmResults, true, true);

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
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.actionSearch));
        searchView.setOnQueryTextListener(this);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i("QUERY COMPLETED", query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.i("ON THE FLY", newText);
        return false;
    }

    private void buildAndShowInputDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity1.this);
        builder.setTitle(R.string.dialog_main_title);

        LayoutInflater li = LayoutInflater.from(this);
        View dialogView = li.inflate(R.layout.task_dialog_view, null);
        final EditText input = (EditText) dialogView.findViewById(R.id.dialogTitleInput);

        builder.setView(dialogView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // TODO implement add todo
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        final AlertDialog dialog = builder.show();
        input.setOnEditorActionListener(
                new EditText.OnEditorActionListener(){

                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                        // TODO implement add todo
                        return false;
                    }
                }
        );
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        realm.close();
        realm = null;
    }
}
