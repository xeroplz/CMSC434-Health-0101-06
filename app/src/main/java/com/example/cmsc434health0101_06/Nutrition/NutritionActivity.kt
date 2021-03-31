package com.example.cmsc434health0101_06.Nutrition

import android.os.Bundle
import android.support.wearable.activity.WearableActivity

class NutritionActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition)

        // Enables Always-on
        setAmbientEnabled()
    }
}