package com.example.cmsc434health0101_06.Nutrition

import kotlin.collections.*;

class Food(name: String, calories: Int, fats: Int, carbs: Int) {
    lateinit var foodName: String
    var calories = 0
    var fats = 0
    var carbs = 0

    init {
        this.foodName = name
        this.calories = calories
        this.fats = fats
        this.carbs = carbs
    }
}

class Meal(name: String) {
    lateinit var mealName: String
    private lateinit var foods: ArrayList<Food>

    init {
        this.mealName = name
        this.foods = arrayListOf<Food>()
    }
}