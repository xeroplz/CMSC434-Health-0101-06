package com.example.cmsc434health0101_06.Nutrition

import android.util.Log
import kotlin.collections.*;

private val TAG = "HEALTH010106"

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
}