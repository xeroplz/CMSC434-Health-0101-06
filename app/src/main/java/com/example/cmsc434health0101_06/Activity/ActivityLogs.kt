package com.example.cmsc434health0101_06.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cmsc434health0101_06.R
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmsc434health0101_06.Nutrition.Meal
import com.example.cmsc434health0101_06.Nutrition.MealListAdapter
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.nio.charset.Charset



class ActivityLogs: AppCompatActivity() {

    private lateinit var mAddActivityButton : Button
    private lateinit var mListView: ListView
    internal lateinit var mAdapter: ActivityAdapter
    private var clickedItemPos = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity_logs)

        // Bar Title
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Activity Tracker"
        }

        mListView = findViewById(R.id.activityListView)
        mAddActivityButton = findViewById(R.id.addActivityButton)
        mAdapter = ActivityAdapter(applicationContext)
        mListView.adapter = mAdapter

        registerForContextMenu(mListView)

        // Context menu for every item
        mListView.setOnItemClickListener { parent, view, position, id ->
            clickedItemPos = position
            view.showContextMenu()
        }

        mAddActivityButton.setOnClickListener {
            val activityIntent = Intent(this, AddActivity::class.java)
            startActivity(activityIntent)
        }
    }

    override fun onResume() {
        super.onResume()

        mAdapter.clear()
        loadItems()
    }

    // Load stored activities
    private fun loadItems() {
        // Get activities from JSON
        val activityList = ActivityT.getSavedActivities(applicationContext)
        if (activityList.isEmpty()) return

        val indices = activityList.size - 1
        (0..indices).forEach {
            mAdapter.add(activityList[it])
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val activityList = ActivityT.getSavedActivities(applicationContext)
        menu!!.setHeaderTitle(activityList[clickedItemPos].activityName)
        menu!!.add(0, v!!.id, 0, "Delete")
        menu!!.add(0, v!!.id, 0, "Cancel")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val activityList = ActivityT.getSavedActivities(applicationContext)
        val size = activityList.size

        if (item.title === "Delete") {
            if (clickedItemPos < 0 || clickedItemPos >= size) return true

            // Delete meal
            val activityName = activityList[clickedItemPos].activityName
            ActivityT.removeActivity(applicationContext, activityName)
            mAdapter.clear()
            loadItems()
        }

        return true
    }

    companion object {
        private val TAG = "HEALTH010106"
    }
}