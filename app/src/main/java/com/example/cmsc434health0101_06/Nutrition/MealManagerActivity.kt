package com.example.cmsc434health0101_06.Nutrition

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cmsc434health0101_06.R
import java.io.*;
import org.json.*;
import com.google.gson.*;

class MealManagerActivity : AppCompatActivity() {

    private val fileName = "meals.json"
    protected lateinit var myExternalFile: File
    internal var myData = ""
    private val isExternalStorageReadOnly: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            return if (Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState) {
                true
            } else false
        }

    private val isExternalStorageAvailable: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            return if (Environment.MEDIA_MOUNTED == extStorageState) {
                true
            } else false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_manager)

        // Test write data
        val meals = ArrayList<Meal>()

        val foods = ArrayList<Food>()
        val banana = Food("Banana", 105, 0.4, 27.0)
        val apple = Food("Apple", 95, 0.3, 25.0)
        val smokedChicken = Food("Smoked Chicken", 320, 16.0, 11.0)
        foods.addAll(arrayOf(banana, apple, smokedChicken))
        val meal1 = Meal("Breakfast", foods)
        meals.add(meal1)

        val foods2 = ArrayList<Food>()
        val salmon = Food("Salmon", 200, 11.4, 23.0)
        val fries = Food("Fries", 320, 16.0, 48.0)
        val bagel = Food("Bagel", 110, 7.0, 32.0)
        foods2.addAll(arrayOf(salmon, fries, bagel))
        val meal2 = Meal("Lunch", foods2)
        meals.add(meal2)

        val filePath = applicationContext.filesDir
        Log.i(TAG, "Meals json located at $filePath/$fileName.")

        // Serialize
        val gson = Gson()
        val str = gson.toJson(meals)

        // Get file & write text
        val outFile = File(filePath, fileName)
        outFile.writeText(str)
        Log.i(TAG, "Meal write success!")
    }

    companion object {
        private val TAG = "HEALTH010106"
    }
}