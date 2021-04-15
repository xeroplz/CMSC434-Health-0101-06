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

    fun getCalories() : Int {
        val indices = this.foods.size - 1
        var calories = 0

        (0..indices).forEach {
            calories += this.foods[it].calories
        }

        return calories
    }

    fun getFats() : Double {
        val indices = this.foods.size - 1
        var fats = 0.0

        (0..indices).forEach {
            fats += this.foods[it].fats
        }

        return fats
    }

    fun getCarbs() : Double {
        val indices = this.foods.size - 1
        var carbs = 0.0

        (0..indices).forEach {
            carbs += this.foods[it].carbs
        }

        return carbs
    }

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
        this.foods.add(food)
    }

    init {
        this.mealName = name
        this.foods = foods
    }

    // Static Functions
    companion object {
        private val TAG = "HEALTH010106"
        private val fileName = "meals.json"
        private val dailyFileName = "todayMeals.json"

        // Json for daily meal list instead of the preserved list
        //region Daily Meals
        fun addDailyMeal(context: Context, meal: Meal) {
            val meals = getSavedMeals(context, true)
            val indices = meals.size - 1
            (0..indices).forEach {
                val x = meals.get(it)
                if (x.mealName.equals(meal.mealName)) {
                    Log.i(TAG, "Failed to add '${meal.mealName}'. Meal already exists.")
                    return
                }
            }

            meals.add(meal)
            saveMeals(context, meals, true)
        }

        fun removeDailyMeal(context: Context, mealName: String) {
            val meals = getSavedMeals(context, true)
            val indices = meals.size - 1
            var index = -1
            (0..indices).forEach {
                val x = meals.get(it)
                if (x.mealName.equals(mealName)) {
                    index = it
                }
            }

            if (index == -1) {
                Log.i(TAG, "Failed to remove '${mealName}'. Meal does not exist.")
                return
            } else {
                meals.removeAt(index)
                saveMeals(context, meals, true)
            }
        }
        //endregion

        fun addMeal(context: Context, meal: Meal) {
            val meals = getSavedMeals(context)
            val indices = meals.size - 1
            (0..indices).forEach {
                val x = meals.get(it)
                if (x.mealName.equals(meal.mealName)) {
                    Log.i(TAG, "Failed to add '${meal.mealName}'. Meal already exists.")
                    return
                }
            }

            meals.add(meal)
            saveMeals(context, meals)
        }

        fun removeMeal(context: Context, mealName: String) {
            val meals = getSavedMeals(context)
            val indices = meals.size - 1
            var index = -1
            (0..indices).forEach {
                val x = meals.get(it)
                if (x.mealName.equals(mealName)) {
                    index = it
                }
            }

            if (index == -1) {
                Log.i(TAG, "Failed to remove '${mealName}'. Meal does not exist.")
                return
            } else {
                meals.removeAt(index)
                saveMeals(context, meals)
            }
        }

        // Parses meals.json and retrieves an ArrayList of meals from it.
        // Needs an applicationContext to read from the correct file.
        fun getSavedMeals(context: Context, useDailyList: Boolean = false) : ArrayList<Meal> {
            val filePath = context.filesDir
            val gson = Gson()

            // Normal or daily meal list
            var trueFileName: String
            if (useDailyList) trueFileName = dailyFileName
            else trueFileName = fileName

            // Read file
            try {
                val reader = FileReader(filePath.absolutePath + "/" + trueFileName)
                Log.i(TAG, filePath.absolutePath + "/" + trueFileName)

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

                Log.i(TAG, "Meals successfully retrieved from $trueFileName.")
                return mealsDeserialized
            } catch (e: FileNotFoundException) {
                Log.i(TAG, "Cannot read saved meals. $trueFileName not found.")
                return ArrayList<Meal>()
            }
        }

        // Saves a list of meals to the meals.json file on the phone.
        // Needs an applicationContext to save to the correct file.
        fun saveMeals(context: Context, meals: ArrayList<Meal>, useDailyList: Boolean = false) {
            val filePath = context.filesDir

            // Normal or daily meal list
            var trueFileName: String
            if (useDailyList) trueFileName = dailyFileName
            else trueFileName = fileName

            // Serialize
            val gson = Gson()
            val str = gson.toJson(meals)

            // Get file & write text
            val outFile = File(filePath, trueFileName)
            outFile.writeText(str)
            Log.i(TAG, "Meals successfully written to $trueFileName.")
        }
    }
}