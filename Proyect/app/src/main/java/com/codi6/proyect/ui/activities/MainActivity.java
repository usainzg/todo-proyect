package com.codi6.proyect.ui.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.codi6.proyect.R;
import com.codi6.proyect.app.Data;
import com.codi6.proyect.model.Task;

import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements OnQueryTextListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    // FAB BUTTONS
    private com.getbase.floatingactionbutton.FloatingActionButton fabAdd;

    private Button basqueBtn, esBtn, enBtn;

    //Settings multi Texviews
    private TextView text_view_languageTextView, text_view_music_settingsTextView;


    // Navigation view
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        basqueBtn = (Button) findViewById(R.id.btn_lang_basque);
        esBtn = (Button) findViewById(R.id.btn_lang_spanish);
        enBtn = (Button) findViewById(R.id.btn_lang_english);

        text_view_languageTextView = (TextView) findViewById(R.id.txt_settings_lang);

        basqueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.changeLang("eus", MainActivity.this);
                recreate();
            }
        });

        esBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.changeLang("es", MainActivity.this);
                recreate();
            }
        });

        enBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.changeLang("en", MainActivity.this);
                recreate();
            }
        });


        fabAdd = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.action_btn_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildAndShowInputDialog();
            }
        });

        updateTexts();
    }

    @Override
    public void recreate() {
        Intent restart_app_intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(restart_app_intent);
        overridePendingTransition(0, 0); //Remove animation in transition...
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.equals("")) {

        } else {
        }
        return true;

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // CALL onQueryTextSubmit TO EVALUATE CONTENT AND SHOW ALL TASKS
        if (newText.equals("")) {
            this.onQueryTextSubmit("");
        }
        return true;
    }


    private void buildAndShowInputDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                taskToIntro.setTitle(inputTitle.getText().toString());
                taskToIntro.setDescription(inputDescription.getText().toString());
                taskToIntro.setCreatedAt(new Date());
                taskToIntro.setDone(false);
                taskToIntro.setLabel(inputLabel.getText().toString());


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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //Use to update text in select language
    private void updateTexts() {
        invalidateOptionsMenu();

        Data.loadLocale(getApplicationContext());

        Log.d("IDIOMA--> ", "Select language in app is: " + Data.getLocaleLanguage(getApplicationContext()));

        //Update Settings texts
        text_view_languageTextView.setText(getResources().getString(R.string.settings_text_view_lenguage));

    }
}
