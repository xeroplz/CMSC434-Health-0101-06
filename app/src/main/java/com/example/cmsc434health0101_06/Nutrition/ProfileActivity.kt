package com.example.cmsc434health0101_06.Nutrition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cmsc434health0101_06.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Bar Title
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "User Profile"
        }
    }
}