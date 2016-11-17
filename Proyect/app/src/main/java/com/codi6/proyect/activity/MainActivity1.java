package com.codi6.proyect.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
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
import com.codi6.proyect.app.Data;
import com.codi6.proyect.bbdd.ManagerDb;
import com.codi6.proyect.model.Task;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

@SuppressWarnings("ALL")
public class MainActivity1 extends AppCompatActivity
        implements OnNavigationItemSelectedListener, OnQueryTextListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    // FAB BUTTONS
    private com.getbase.floatingactionbutton.FloatingActionButton fabAdd, fabCamera;
    private com.getbase.floatingactionbutton.FloatingActionsMenu fabMenu;

    // ADAPTER
    private TaskRealmAdapter taskRealmAdapter;
    private ManagerDb managerDb;

    // REALMRECYCLERVIEW
    private RealmRecyclerView realmRecyclerView;

    // LAYOUTS
    private LinearLayout settings;
    private RelativeLayout task_view;

    // SETTINGS REFERENCIAS
    private ToggleButton musicBtn;
    private MediaPlayer media;
    private Button basqueBtn, esBtn, enBtn;

    // REALM INSTACE
    private Realm realm;

    //Settings multi Texviews
    private TextView text_view_languageTextView, text_view_music_settingsTextView;

    // Empty lis view TextView
    private TextView text_view_emptyList;

    // Navigation view
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        realm = Realm.getDefaultInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        settings = (LinearLayout) findViewById(R.id.settings_view);
        task_view = (RelativeLayout) findViewById(R.id.content_main1);

        basqueBtn = (Button) findViewById(R.id.btn_lang_basque);
        esBtn = (Button) findViewById(R.id.btn_lang_spanish);
        enBtn = (Button) findViewById(R.id.btn_lang_english);

        text_view_languageTextView = (TextView) findViewById(R.id.txt_settings_lang);
        text_view_music_settingsTextView = (TextView) findViewById(R.id.txt_settings_music);

        text_view_emptyList = (TextView) findViewById(R.id.txt_empty);

        basqueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.changeLang("eus", MainActivity1.this);
                recreate();
            }
        });

        esBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.changeLang("es", MainActivity1.this);
                recreate();
            }
        });

        enBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.changeLang("en", MainActivity1.this);
                recreate();
            }
        });

        musicBtn = (ToggleButton) findViewById(R.id.musicBtn);

        musicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicBtn.isChecked()) {
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
                } else {
                    media.stop();
                }

            }
        });

        managerDb = new ManagerDb();

        // GET FAB MENU BUTTONS REFERENCES
        fabMenu = (com.getbase.floatingactionbutton.FloatingActionsMenu) findViewById(R.id.menu_add);
        fabCamera = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.action_btn_camera);
        fabAdd = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.action_btn_add);

        // ADD ONCLICKLISTENER´s
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


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // SET REALMRECYCLERVIEW
        realmRecyclerView = (RealmRecyclerView) findViewById(R.id.list);

        // GET TASKS FROM REALM DATABASE
        RealmResults<Task> realmResults = managerDb.findTasks();

        // BUILD ADAPTER PASSING REALMRESULT
        taskRealmAdapter = new TaskRealmAdapter(getApplicationContext(), realmResults, true, false);

        // SET ADAPTER TO REALMRECLYCLERVIEW
        realmRecyclerView.setAdapter(taskRealmAdapter);

        updateTexts();
    }

    @Override
    public void recreate()
    {
        Intent restart_app_intent = new Intent (MainActivity1.this, MainActivity1.class);
        startActivity(restart_app_intent);
        overridePendingTransition(0,0); //Remove animation in transition...
        finish();
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
        getMenuInflater().inflate(R.menu.search_menu, menu);

        // BUILD SEARCH MANAGER
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.actionSearch));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        // SET HINT FOR QUERIES
        searchView.setQueryHint(getResources().getString(R.string.action_search_title));
        // SET ON QUERY LISTENER TO LISTEN SEARCH EVENT´S
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_tasks) {
            // SHOW TASK_VIEW AND FABMENU (FLOATING ICONS)
            task_view.setVisibility(View.VISIBLE);
            fabMenu.setVisibility(View.VISIBLE);
            // HIDE OTHER INCLUDED LAYOUTS
            settings.setVisibility(View.GONE);

        } else if (id == R.id.nav_settings) {
            // HIDE TASK_VIEW AND FABMENU (FLOATING ICONS)
            task_view.setVisibility(View.GONE);
            fabMenu.setVisibility(View.GONE);
            // SHOW SETTINGS LAYOUT
            settings.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_help) {
            // INTENT FOR SEND SUPPORT EMAIL
            Intent i = new Intent(Intent.ACTION_SEND);
            // SET TYPE
            i.setType("message/rfc822");
            // POPULATE WITH SUPPORT EMAIL
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"unaihtc70@gmail.com"});
            // POPULATE WITH SUBJECT HINT AND BODY HINT
            i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.support_email_subject));
            i.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.support_email_body));
            try {
                // START INTENT WITH CHOOSER
                startActivity(Intent.createChooser(i, getResources().getString(R.string.support_email_send)));
            } catch (android.content.ActivityNotFoundException ex) {
                // IF THERE ARE NOT EMAIL CLIENTS INSTALLED...
                Toast.makeText(MainActivity1.this, getResources().getString(R.string.support_email_not_client), Toast.LENGTH_SHORT).show();
            }

        }
        // BUILD DRAWERLAYOUT
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if(query.equals("")){
            RealmResults<Task> allTasks = realm.where(Task.class).findAllSorted("title", Sort.ASCENDING);
            taskRealmAdapter = new TaskRealmAdapter(getApplicationContext(), allTasks, true, false);
            realmRecyclerView.setAdapter(taskRealmAdapter);

        }else{
            RealmResults<Task> tasks = managerDb.findTask(query);
            if (tasks.isEmpty()) {
                Toast.makeText(this, "No se han encontrado registros con ese titulo!", Toast.LENGTH_LONG).show();
                return false;
            }
            SearchQueryCompletedAdapter searchQueryCompletedAdapter = new SearchQueryCompletedAdapter(
                    getApplicationContext(), tasks, true, false
            );
            realmRecyclerView.setAdapter(searchQueryCompletedAdapter);
        }
        return true;

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // CALL onQueryTextSubmit TO EVALUATE CONTENT AND SHOW ALL TASKS
        if(newText.equals("")){
            this.onQueryTextSubmit("");
        }
        return true;
    }


    private void buildAndShowInputDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity1.this);
        builder.setTitle(R.string.dialog_main_title);

        LayoutInflater li = LayoutInflater.from(this);
        View dialogView = li.inflate(R.layout.task_dialog_view, null);
        final EditText inputTitle = (EditText) dialogView.findViewById(R.id.dialogTitleInput);
        final EditText inputDescription = (EditText) dialogView.findViewById(R.id.dialogDescriptionInput);
        final EditText inputLabel = (EditText) dialogView.findViewById(R.id.dialogLabelInput);

        builder.setView(dialogView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String uniqueId = UUID.randomUUID().toString();
                final Task taskToIntro = new Task();
                taskToIntro.setId(uniqueId);
                taskToIntro.setTitle(inputTitle.getText().toString());
                taskToIntro.setDescription(inputDescription.getText().toString());
                taskToIntro.setCreatedAt(new Date());
                taskToIntro.setDone(false);
                taskToIntro.setLabel(inputLabel.getText().toString());

                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(taskToIntro);
                    }
                });

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
        managerDb.closeAll();
    }

    //Use to update text in select language
    private void updateTexts()
    {
        invalidateOptionsMenu();

        Data.loadLocale(getApplicationContext());

        System.out.println("Select language in app is: " + Data.getLocaleLanguage(getApplicationContext()));

        //Update app title
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        //Update Settings texts
        text_view_languageTextView.setText(getResources().getString(R.string.settings_text_view_lenguage));
        text_view_music_settingsTextView.setText(getResources().getString(R.string.settings_text_view_music));

        // Update Empty_list texts
        text_view_emptyList.setText(getResources().getString(R.string.task_empty_list));

        //Update Left navigation menu items texts in select language
        navigationView.getMenu().getItem(0).setTitle(getResources().getString(R.string.drawer_menu_txt_tasks));
        navigationView.getMenu().getItem(1).setTitle(getResources().getString(R.string.drawer_menu_txt_settings));
        navigationView.getMenu().getItem(2).setTitle(getResources().getString(R.string.drawer_menu_txt_help));

        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }
}
