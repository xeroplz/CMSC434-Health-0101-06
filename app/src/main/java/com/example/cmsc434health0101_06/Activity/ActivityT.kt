package com.example.cmsc434health0101_06.Activity

import android.content.Context
import android.util.Log
import com.example.cmsc434health0101_06.Nutrition.Food
import com.example.cmsc434health0101_06.Nutrition.Meal
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.time.Duration
import java.util.*
import kotlin.collections.ArrayList

class ActivityT(name: String, date: String, type: String, duration: Int, weight: Int, reps: Int) {
    lateinit var activityName: String
    lateinit var activityDate: String
    lateinit var activityType: String
    var activityDuration = 0
    var activityWeight = 0
    var activityRep = 0

    init {
        this.activityName = name
        this.activityDate = date
        this.activityType = type
        this.activityDuration = duration
        this.activityWeight = weight
        this.activityRep = reps
    }

    companion object {
        private val TAG = "HEALTH010106"
        private val fileName = "activities.json"

        fun addActivity(context: Context, activity: ActivityT) {
            val activityList = getSavedActivities(context)
            val indices = activityList.size - 1
            (0..indices).forEach {
                val x = activityList.get(it)
                if (x.activityName.equals(activity.activityName)) {
                    Log.i(TAG, "Failed to add '${activity.activityName}'. Activity already exists.")
                    return
                }
            }

            activityList.add(activity)
            saveActivities(context, activityList)
        }

        fun removeActivity(context: Context, activityName: String) {
            val activityList = getSavedActivities(context)
            val indices = activityList.size - 1
            var index = -1
            (0..indices).forEach {
                val x = activityList.get(it)
                if (x.activityName.equals(activityName)) {
                    index = it
                }
            }

            if (index == -1) {
                Log.i(TAG, "Failed to remove '${activityName}'. Activity does not exist.")
                return
            } else {
                activityList.removeAt(index)
                saveActivities(context, activityList)
            }
        }

        // Parses meals.json and retrieves an ArrayList of meals from it.
        // Needs an applicationContext to read from the correct file.
        fun getSavedActivities(context: Context) : ArrayList<ActivityT> {
            val filePath = context.filesDir
            val gson = Gson()

            // Read file
            try {
                val reader = FileReader(filePath.absolutePath + "/" + fileName)
                Log.i(TAG, filePath.absolutePath + "/" + fileName)

                // Input comes in as an ArrayList, but each value in it will be turned into a
                // LinkedTreeMap by GSON
                val activitiesSerialized = gson.fromJson<ArrayList<ActivityT>>(reader, ArrayList::class.java)

                val activitiesDeserialized = ArrayList<ActivityT>()
                val activitiesIndices = activitiesSerialized.size - 1

                // Re-map activities into a new list
                (0..activitiesIndices).forEach {
                    val activityT = activitiesSerialized[it] as LinkedTreeMap<String, Object>

                    val activityName = activityT.getValue("activityName") as String
                    val activityDate = activityT.getValue("activityDate") as String
                    val activityType = activityT.getValue("activityType") as String
                    val activityDuration = activityT.getValue("activityDuration") as Double
                    val activityWeight = activityT.getValue("activityWeight") as Double
                    val activityReps = activityT.getValue("activityRep") as Double

                    activitiesDeserialized.add(ActivityT(activityName, activityDate, activityType,
                        activityDuration.toInt(), activityWeight.toInt(), activityReps.toInt()))
                }

                Log.i(TAG, "Activities successfully retrieved from $fileName.")
                return activitiesDeserialized
            } catch (e: FileNotFoundException) {
                Log.i(TAG, "Cannot read saved activities. $fileName not found.")
                return ArrayList<ActivityT>()
            }
        }

        // Saves a list of activities to the activites.json file on the phone.
        // Needs an applicationContext to save to the correct file.
        fun saveActivities(context: Context, activities: ArrayList<ActivityT>) {
            val filePath = context.filesDir

            // Serialize
            val gson = Gson()
            val str = gson.toJson(activities)

            // Get file & write text
            val outFile = File(filePath, fileName)
            outFile.writeText(str)
            Log.i(TAG, "Activities successfully written to $fileName.")
        }
    }
}

