package com.example.cmsc434health0101_06.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cmsc434health0101_06.R
import android.widget.*
import com.google.gson.*
import java.io.File
import android.content.Context
import android.util.Log
import java.io.IOException
import com.google.gson.reflect.TypeToken

class AddActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_activity)



        val nameOfActivity = findViewById<EditText>(R.id.activityName)
        val duration = findViewById<EditText>(R.id.duration)
        val weights = findViewById<EditText>(R.id.weight)
        val reps = findViewById<EditText>(R.id.reps)
        val button = findViewById<Button>(R.id.SubmitActivity)

        val spinner: Spinner = findViewById(R.id.exerciseType)
        ArrayAdapter.createFromResource(
                this,
                R.array.exercise_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }


        button.setOnClickListener{
            val name = nameOfActivity.toString()
            val durationString = duration.toString().toIntOrNull()
            val weightString = weights.toString().toIntOrNull()
            val repString = reps.toString().toIntOrNull()
            val spinnerSelect = spinner.getSelectedItem().toString()
            val currActivity = ActivityT(name,spinnerSelect,durationString,weightString,repString)
            saveToJson(applicationContext,currActivity.toString(),"activities.json")

            val intent = Intent(this, ActivityLogs::class.java)
            startActivity(intent)
        }


    }
    private fun saveToJson(context:Context,activity:String?,fileName:String){
        var filePath = context.filesDir
        Log.i("hiya", filePath.toString())
        val jsonUpload = Gson().toJson(activity)
        val fileToUse = File(filePath,fileName)

        Log.i("hiya", fileToUse.toString())
        fileToUse.writeText(jsonUpload)
    }


}