package com.example.cmsc434health0101_06.Nutrition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.cmsc434health0101_06.R

class UpdateProfile : AppCompatActivity() {

    private lateinit var mGenderRadioButtonGroup : RadioGroup
    private lateinit var mGenderMButton : RadioButton
    private lateinit var mGenderFButton : RadioButton
    private lateinit var mGenderOtherButton : RadioButton

    private lateinit var mUserSaveButton : Button
    private lateinit var mUserCancelButton : Button

    private lateinit var mUserNameEdit : EditText
    private lateinit var mUserAgeEdit : EditText
    private lateinit var mUserHeightFtEdit : EditText
    private lateinit var mUserHeightInEdit : EditText
    private lateinit var mUserWeightEdit : EditText

    private val gender: Gender
        get() {

            when (mGenderRadioButtonGroup!!.checkedRadioButtonId) {
                R.id.genderMButton -> {
                    return Gender.Male
                }
                R.id.genderFButton -> {
                    return Gender.Female
                }
                else -> {
                    return Gender.Other
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        // Bar Title
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Edit Profile"
        }

        mGenderRadioButtonGroup = findViewById(R.id.genderRadioGroup)
        mGenderMButton = findViewById(R.id.genderMButton)
        mGenderFButton = findViewById(R.id.genderFButton)
        mGenderOtherButton = findViewById(R.id.genderOtherButton)

        mUserSaveButton = findViewById(R.id.userSaveButton)
        mUserCancelButton = findViewById(R.id.userCancelButton)
        mUserNameEdit = findViewById(R.id.userNameEdit)
        mUserAgeEdit = findViewById(R.id.userAgeEdit)
        mUserHeightFtEdit = findViewById(R.id.userHeightFtEdit)
        mUserHeightInEdit = findViewById(R.id.userHeightInEdit)
        mUserWeightEdit = findViewById(R.id.userWeightEdit)

        // Populate fields with current user data
        val user = User.getSavedUser(applicationContext)
        mUserNameEdit.setText(user.name)
        mUserAgeEdit.setText(user.age.toString())
        mUserHeightFtEdit.setText(user.heightFt.toString())
        mUserHeightInEdit.setText(user.heightIn.toString())
        mUserWeightEdit.setText(user.weight.toString())
        when (user.gender) {
            Gender.Male.ordinal -> {
                mGenderMButton.isChecked = true
                mGenderFButton.isChecked = false
                mGenderOtherButton.isChecked = false
            }
            Gender.Female.ordinal -> {
                mGenderMButton.isChecked = false
                mGenderFButton.isChecked = true
                mGenderOtherButton.isChecked = false
            }
            else -> {
                mGenderMButton.isChecked = false
                mGenderFButton.isChecked = false
                mGenderOtherButton.isChecked = true
            }
        }

        mUserCancelButton.setOnClickListener {
            finish()
        }

        // Save user data and move to home page
        mUserSaveButton.setOnClickListener {
            val name = mUserNameEdit.text.toString()
            val age = mUserAgeEdit.text.toString().toIntOrNull()
            val heightFt = mUserHeightFtEdit.text.toString().toIntOrNull()
            val heightIn = mUserHeightInEdit.text.toString().toIntOrNull()
            val weight = mUserWeightEdit.text.toString().toIntOrNull()
            val selectedGender = this.gender.ordinal

            // Value checks
            if (name.equals("") || age == null || heightFt == null || heightIn == null || weight == null
                || age <= 0 || heightFt < 0 || heightIn < 0 || heightIn >= 12 || weight <= 0) {
                Toast.makeText(applicationContext, "Please make sure all of your data is valid.",
                    Toast.LENGTH_LONG).show()
            } else {
                val newUser = User(name, age, selectedGender, heightFt, heightIn, weight)
                User.saveUser(applicationContext, newUser)
                finish()
            }
        }
    }
}