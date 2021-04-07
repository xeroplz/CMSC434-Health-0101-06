package com.example.cmsc434health0101_06.Nutrition

import android.content.Context
import android.util.Log
import android.os.*;
import kotlin.collections.*;
import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader

class Tuple<K, T>(value1: K, value2: T) {
    var value1 = value1
    var value2 = value2

    init {
        this.value1 = value1
        this.value2 = value2
    }
}

class Food(name: String, calories: Int, fats: Double, carbs: Double) {
    lateinit var foodName: String
    var calories = 0
    var fats = 0.0
    var carbs = 0.0

    init {
        this.foodName = name
        this.calories = calories
        this.fats = fats
        this.carbs = carbs
    }
}

class Meal(name: String, foods: ArrayList<Food>) {
    lateinit var mealName: String
    lateinit var foods: ArrayList<Food>

    fun hasFood(foodName: String) : Tuple<Boolean, Int> {
        val indices = this.foods.size - 1

        (0..indices).forEach {
            val foodItem = this.foods.get(it)
            if (foodItem.foodName.equals(foodName)) {
                return Tuple(true, it)
            }
        }

        return Tuple(false, -1)
    }

    fun removeFood(foodName: String) {
        val hasFoodResult = this.hasFood(foodName)

        if (hasFoodResult.value1) {
            this.foods.removeAt(hasFoodResult.value2)
        } else {
            Log.i(TAG, "Failed to remove '$foodName'. Food not found in meal.")
            return
        }
    }

    fun addFood(food: Food) {
        val hasFoodResult = this.hasFood(food.foodName)

        if (hasFoodResult.value1) {
            Log.i(TAG, "Failed to add '${food.foodName}'. Food already exists in meal.")
            return
        } else {
            this.foods.add(food)
        }
    }

    init {
        this.mealName = name
        this.foods = foods
    }

    // Static Functions
    companion object {
        private val TAG = "HEALTH010106"
        private val fileName = "meals.json"

        // Parses meals.json and retrieves an ArrayList of meals from it.
        // Needs an applicationContext to read from the correct file.
        fun getSavedMeals(context: Context) : ArrayList<Meal> {
            val filePath = context.filesDir
            val gson = Gson()

            // Read file
            try {
                val reader = FileReader(filePath.absolutePath + "/" + fileName)
                Log.i(TAG, filePath.absolutePath + "/" + fileName)

                // Input comes in as an ArrayList, but each value in it will be turned into a
                // LinkedTreeMap by GSON
                val mealsSerialized = gson.fromJson<ArrayList<Meal>>(reader, ArrayList::class.java)

                val mealsDeserialized = ArrayList<Meal>()
                val mealsIndices = mealsSerialized.size - 1
                // Re-map meals into a new list
                (0..mealsIndices).forEach {
                    val mealT = mealsSerialized[it] as LinkedTreeMap<String, Object>

                    val mealName = mealT.getValue("mealName") as String
                    val foodsSerialized = mealT.getValue("foods") as ArrayList<Food>

                    // We need to place foods back in their intended objects as well
                    val foodsDeserialized = ArrayList<Food>()
                    val foodIndices = foodsSerialized.size - 1
                    (0..foodIndices).forEach { it2: Int ->
                        val foodT = foodsSerialized[it2] as LinkedTreeMap<String, Object>

                        val foodName = foodT.getValue("foodName") as String
                        val calories = foodT.getValue("calories") as Double
                        val fats = foodT.getValue("fats") as Double
                        val carbs = foodT.getValue("carbs") as Double

                        foodsDeserialized.add(Food(foodName, calories.toInt(), fats, carbs))
                    }

                    mealsDeserialized.add(Meal(mealName, foodsDeserialized))
                }

                Log.i(TAG, "Meals successfully retrieved from $fileName.")
                return mealsDeserialized
            } catch (e: FileNotFoundException) {
                Log.i(TAG, "Cannot read saved meals. $fileName not found.")
                return ArrayList<Meal>()
            }
        }

        // Saves a list of meals to the meals.json file on the phone.
        // Needs an applicationContext to save to the correct file.
        fun saveMeals(context: Context, meals: ArrayList<Meal>) {
            val filePath = context.filesDir

            // Serialize
            val gson = Gson()
            val str = gson.toJson(meals)

            // Get file & write text
            val outFile = File(filePath, fileName)
            outFile.writeText(str)
            Log.i(TAG, "Meals successfully written to $fileName.")
        }
    }
}