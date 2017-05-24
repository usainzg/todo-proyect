package com.codi6.proyect.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.SearchView.OnQueryTextListener
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.codi6.proyect.R
import com.codi6.proyect.app.Data
import com.codi6.proyect.listeners.ActivityMainOnClickListener
import com.codi6.proyect.model.Task
import java.util.*

class ActivityMain : AppCompatActivity(), OnQueryTextListener {

    // FAB BUTTONS
    private var fabAdd: com.getbase.floatingactionbutton.FloatingActionButton? = null

    private val basqueBtn: Button? = null
    private val esBtn: Button? = null
    private val enBtn: Button? = null

    //Settings multi Texviews
    private val text_view_languageTextView: TextView? = null
    private val text_view_music_settingsTextView: TextView? = null


    // Navigation view
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        updateTexts()
    }

    private fun initViews() {
        bottomNavigationView = findViewById(R.id.main_navigation_view) as BottomNavigationView
        fabAdd = findViewById(R.id.action_btn_add) as com.getbase.floatingactionbutton.FloatingActionButton
    }

    private fun setOnClick() {
        fabAdd!!.setOnClickListener { buildAndShowInputDialog() }
        bottomNavigationView!!.setOnClickListener(ActivityMainOnClickListener(this))
    }

    override fun recreate() {
        val restart_app_intent = Intent(this@ActivityMain, ActivityMain::class.java)
        startActivity(restart_app_intent)
        overridePendingTransition(0, 0) //Remove animation in transition...
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        // BUILD SEARCH MANAGER
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = MenuItemCompat.getActionView(menu.findItem(R.id.actionSearch)) as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        // SET HINT FOR QUERIES
        searchView.queryHint = resources.getString(R.string.action_search_title)
        // SET ON QUERY LISTENER TO LISTEN SEARCH EVENT´S
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        if (query == "") {

        } else {
        }
        return true

    }

    override fun onQueryTextChange(newText: String): Boolean {
        // CALL onQueryTextSubmit TO EVALUATE CONTENT AND SHOW ALL TASKS
        if (newText == "") {
            this.onQueryTextSubmit("")
        }
        return true
    }


    private fun buildAndShowInputDialog() {
        val builder = AlertDialog.Builder(this@ActivityMain)
        builder.setTitle(R.string.dialog_main_title)

        val li = LayoutInflater.from(this)
        val dialogView = li.inflate(R.layout.task_dialog_view, null)
        val inputTitle = dialogView.findViewById(R.id.dialogTitleInput) as EditText
        val inputDescription = dialogView.findViewById(R.id.dialogDescriptionInput) as EditText
        val inputLabel = dialogView.findViewById(R.id.dialogLabelInput) as EditText

        builder.setView(dialogView)
        builder.setPositiveButton("OK") { dialogInterface, i ->
            val uniqueId = UUID.randomUUID().toString()
            val taskToIntro = Task()
            taskToIntro.title = inputTitle.text.toString()
            taskToIntro.description = inputDescription.text.toString()
            taskToIntro.createdAt = Date()
            taskToIntro.isDone = false
            taskToIntro.label = inputLabel.text.toString()
        }

        builder.setNegativeButton("Cancel") { dialogInterface, i -> dialogInterface.cancel() }

        val dialog = builder.show()
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    //Use to update text in select language
    private fun updateTexts() {
        invalidateOptionsMenu()

        Data.loadLocale(applicationContext)

        Log.d("IDIOMA--> ", "Select language in app is: " + Data.getLocaleLanguage(applicationContext))

    }

    companion object {

        internal val REQUEST_IMAGE_CAPTURE = 1
    }
}
