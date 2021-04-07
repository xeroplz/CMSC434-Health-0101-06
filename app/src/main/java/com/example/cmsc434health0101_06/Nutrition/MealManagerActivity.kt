package com.example.cmsc434health0101_06.Nutrition

import android.nfc.Tag
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cmsc434health0101_06.R

class MealManagerActivity : AppCompatActivity() {

    private val fileName = "meals.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_manager)

        /* EXAMPLE READ/WRITE CODE FOR MEALS ------------------------------------------------------
        // Uncomment to make it work, of course.

        // Create Food and Meals
        var meals = ArrayList<Meal>()

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

        // Empty meal reading test
        var readMeals = Meal.getSavedMeals(applicationContext)

        // Write meals!
        // They should appear in /data/user/0/com.example.cmsc434health0101_06/files/meals.json
        Meal.saveMeals(applicationContext, meals)

        // Clear out original meals
        meals = ArrayList<Meal>()

        // Read saved meals
        readMeals = Meal.getSavedMeals(applicationContext)

        // If the list isn't empty, it's valid!
        if (!readMeals.isEmpty()) meals = readMeals

        // Check out the meals object in debug. If it's empty, the read failed. If it's filled, the
        // read was a success!

        // Redundant line for you to use as a breakpoint
        meals = meals

        ---------------------------------------------------------------------------------------- */

        /* Now for the actual work... Just need to have meal add and delete functionality.
        Plus a list of all meals currently in the list. List view will have the meal's name,
        total calories, total fats, and total carbs visible.
        Clicking on a meal will show a context menu that has 3 buttons. View, Delete, Cancel.
        Each of these should be self explanatory
        */
        
    }

    companion object {
        private val TAG = "HEALTH010106"
    }
}