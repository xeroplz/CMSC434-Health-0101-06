package com.example.cmsc434health0101_06.Nutrition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.cmsc434health0101_06.Activity.ActivityLogs
import com.example.cmsc434health0101_06.R

class HomeActivity : AppCompatActivity() {

    private lateinit var mNutritionTrackerButton : Button
    private lateinit var mActivityTrackerButton : Button
    private lateinit var mMealManagerButton : Button
    private lateinit var mEditProfileButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val user = User.getSavedUser(applicationContext)

        // Bar Title
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Home - " + user.name
        }

        mNutritionTrackerButton = findViewById(R.id.nutritionActivityButton)
        mActivityTrackerButton = findViewById(R.id.activityActivityButton)
        mMealManagerButton = findViewById(R.id.mealManagerActivityButton)
        mEditProfileButton = findViewById(R.id.editProfileActivityButton)


        mNutritionTrackerButton.setOnClickListener {
            val nutritionIntent = Intent(this, NutritionActivity::class.java)
            startActivity(nutritionIntent)
        }

        mActivityTrackerButton.setOnClickListener {
            val activityIntent = Intent(this, ActivityLogs::class.java)
            startActivity(activityIntent)
        }

        mMealManagerButton.setOnClickListener {
            val mealManager = Intent(this, MealManagerActivity::class.java)
            startActivity(mealManager)
        }

        mEditProfileButton.setOnClickListener {
            val editProfile = Intent(this, UpdateProfile::class.java)
            startActivity(editProfile)
        }
    }

    override fun onResume() {
        super.onResume()

        // Update bar title with name if changed
        val user = User.getSavedUser(applicationContext)

        // Bar Title
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Home - " + user.name
        }
    }
}