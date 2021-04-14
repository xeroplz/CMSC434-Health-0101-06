package com.example.cmsc434health0101_06.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cmsc434health0101_06.R
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private val activityList = ArrayList<ActivityT>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity_logs)

        val activityList: ArrayList<ActivityT> = ArrayList()

        try {
            val jsonString = getJSONFromAssets()!!
            val activities = Gson().fromJson(jsonString,ActivityT::class.java)

            val obj = JSONObject(getJSONFromAssets())
            val activityArray = obj.getJSONArray("activities")
            for( i in 0 until activityArray.length()){
                val activity = activityArray.getJSONObject(i)
                val name = activity.getString("name")
                val date = activity.getString("date")
                val type = activity.getString("type")
                val duration = activity.getString("duration")
                val weight = activity.getString("weight")
                val reps = activity.getString("reps")
            }
            findViewById<RecyclerView>(R.id.rvActivityList).layoutManager = LinearLayoutManager(this)
            val itemAdapter = ActivityAdapter(this,activities)
            findViewById<RecyclerView>(R.id.rvActivityList).adapter = itemAdapter
        }catch (e: JSONException){
            Log.i("hiya","Json setup incorrect")
        }

    }
    private fun getJSONFromAssets(): String? {

        var json: String? = null
        val charset: Charset = Charsets.UTF_8
        try {
            val myUsersJSONFile = assets.open("activities.json")
            val size = myUsersJSONFile.available()
            val buffer = ByteArray(size)
            myUsersJSONFile.read(buffer)
            myUsersJSONFile.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}