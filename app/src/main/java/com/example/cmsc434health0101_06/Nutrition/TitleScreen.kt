package com.example.cmsc434health0101_06.Nutrition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.cmsc434health0101_06.R
import org.w3c.dom.Text

class TitleScreen : AppCompatActivity() {

    private lateinit var mTitleButton : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title_screen)

        mTitleButton = findViewById(R.id.titleScreenInvisibleButton)

        // Send to Registration page if there is no user
        // Send to Home Page if there is a user
        mTitleButton.setOnClickListener {
            // Just a test
            Toast.makeText(applicationContext, "Title screen clicked.",
                Toast.LENGTH_LONG).show()
        }
    }
}