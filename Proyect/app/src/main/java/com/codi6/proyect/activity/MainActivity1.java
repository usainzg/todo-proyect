package com.codi6.proyect.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.codi6.proyect.R;
import com.codi6.proyect.adapters.SearchQueryCompletedAdapter;
import com.codi6.proyect.adapters.TaskRealmAdapter;
import com.codi6.proyect.bbdd.ManagerDb;
import com.codi6.proyect.model.Task;
import com.google.android.gms.vision.text.Line;

import java.util.Date;
import java.util.UUID;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity1 extends AppCompatActivity
        implements OnNavigationItemSelectedListener, OnQueryTextListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;


    private com.getbase.floatingactionbutton.FloatingActionButton fabAdd, fabCamera;
    private com.getbase.floatingactionbutton.FloatingActionsMenu fabMenu;
    private Realm realm;
    private RealmRecyclerView realmRecyclerView;
    private TaskRealmAdapter taskRealmAdapter;
    private ManagerDb managerDb;

    private LinearLayout settings;
    private RelativeLayout task_view;


    // SETTINGS REFERENCIAS
    private ToggleButton musicBtn;
    private MediaPlayer media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        settings = (LinearLayout) findViewById(R.id.settings_view);
        task_view = (RelativeLayout) findViewById(R.id.content_main1);


        musicBtn = (ToggleButton) findViewById(R.id.musicBtn);

        musicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(musicBtn.isChecked()){
                    media = MediaPlayer.create(getApplicationContext(), R.raw.music);
                    media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.start();
                        }
                    });
                    media.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }
                    });
                }else {
                    media.stop();
                }

            }
        });
        managerDb = new ManagerDb();

        fabMenu = (com.getbase.floatingactionbutton.FloatingActionsMenu) findViewById(R.id.menu_add);
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

        RealmResults<Task> realmResults = realm.where(Task.class).findAllSorted("title", Sort.ASCENDING);

        taskRealmAdapter = new TaskRealmAdapter(getApplicationContext(), realmResults, true, false);

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

            task_view.setVisibility(View.VISIBLE);
            fabMenu.setVisibility(View.VISIBLE);
            settings.setVisibility(View.GONE);

        } else if (id == R.id.nav_settings) {

            task_view.setVisibility(View.GONE);
            fabMenu.setVisibility(View.GONE);
            settings.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_help) {

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"unaihtc70@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.support_email_subject));
            i.putExtra(Intent.EXTRA_TEXT   , getResources().getString(R.string.support_email_body));
            try {
                startActivity(Intent.createChooser(i, getResources().getString(R.string.support_email_send)));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity1.this, getResources().getString(R.string.support_email_not_client), Toast.LENGTH_SHORT).show();
            }

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // TODO
    @Override
    public boolean onQueryTextSubmit(String query) {

        if(query.equals("") || query.equals(null)) {
            RealmResults<Task> realmResults = realm.where(Task.class).findAllSorted("title", Sort.ASCENDING);
            taskRealmAdapter = new TaskRealmAdapter(getApplicationContext(), realmResults, false, false);
            realmRecyclerView.setAdapter(taskRealmAdapter);
        }else {
            RealmResults<Task> tasks = managerDb.findTask(query);
            SearchQueryCompletedAdapter searchQueryCompletedAdapter = new SearchQueryCompletedAdapter(
                    getApplicationContext(), tasks, true, false
            );
            realmRecyclerView.setAdapter(searchQueryCompletedAdapter);
        }
        return false;
    }

    // TODO
    @Override
    public boolean onQueryTextChange(String newText) {
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
    }


    // INTENT camara
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
