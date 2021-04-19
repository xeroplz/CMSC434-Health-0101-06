package com.example.cmsc434health0101_06.Nutrition

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cmsc434health0101_06.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import java.io.File
import java.io.FileNotFoundException

import java.security.KeyStore

class NutritionActivity : AppCompatActivity() {

    private lateinit var mCaloriesNum : TextView
    private lateinit var mFatsNum : TextView
    private lateinit var mCarbsNum : TextView
    private lateinit var mAddMealButton : Button
    private lateinit var mClearButton : Button
    private lateinit var mListView : ListView
    internal lateinit var mAdapter: MealListAdapter
    private var clickedMealPos = -1

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition)

        // Bar Title
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Nutrition"
        }

        /*
        Nutrition plan:
        make another "daily" meal list and display the totals for calories, fats, and carbs at the top
        have user create the list by choosing meals from the original meal list. We might not have time
        to do the weekly tracker for it, so daily is fine for now.
         */

        mCaloriesNum = findViewById(R.id.nutritionCaloriesNumLabel)
        mFatsNum = findViewById(R.id.nutritionFatsNumLabel)
        mCarbsNum = findViewById(R.id.nutritionCarbsNumLabel)
        mAddMealButton = findViewById(R.id.nutritionAddMealButton)
        mClearButton = findViewById(R.id.nutritionClearButton)
        mListView = findViewById(R.id.nutritionMealListView)
        mAdapter = MealListAdapter(applicationContext)

        mListView.adapter = mAdapter
        registerForContextMenu(mListView)

        // Context menu for every item
        mListView.setOnItemClickListener { parent, view, position, id ->
            clickedMealPos = position
            view.showContextMenu()
        }

        mAddMealButton.setOnClickListener {
            val startSelect = Intent(this, NutritionSelectMealActivity::class.java)
            startActivity(startSelect)
        }

        mClearButton.setOnClickListener {
            // Delete json file
            val fileDir = applicationContext.filesDir.absolutePath
            try {
                val file = File(fileDir, fileName)
                val deleted = file.delete()
            } catch (e: FileNotFoundException) {
                return@setOnClickListener
            }

            mAdapter.clear()
            loadItems()
            reloadLabels()
        }
    }

    public override fun onResume() {
        super.onResume()

        // Get items in case the file changed
        mAdapter.clear()
        loadItems()
        reloadLabels()
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val mealList = Meal.getSavedMeals(applicationContext, true)
        menu!!.setHeaderTitle(mealList[clickedMealPos].mealName)
        menu!!.add(0, v!!.id, 0, "Delete")
        menu!!.add(0, v!!.id, 0, "Cancel")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val mealList = Meal.getSavedMeals(applicationContext, true)
        val size = mealList.size

        if (item.title === "Delete") {
            if (clickedMealPos < 0 || clickedMealPos >= size) return true

            // Delete meal
            val mealName = mealList[clickedMealPos].mealName
            Meal.removeDailyMeal(applicationContext, mealName)
            mAdapter.clear()
            loadItems()
            reloadLabels()
        }

        return true
    }

    // Load stored Meals
    private fun loadItems() {
        // Get meals from JSON
        val mealList = Meal.getSavedMeals(applicationContext, true)
        if (mealList.isEmpty()) return

        val indices = mealList.size - 1
        (0..indices).forEach {
            mAdapter.add(mealList[it])
        }
    }

    private fun reloadLabels() {
        // Update total meal data
        val meals = Meal.getSavedMeals(applicationContext, true)
        val indices = meals.size - 1
        var calories = 0
        var fats = 0.0
        var carbs = 0.0

        (0..indices).forEach {
            calories += meals[it].getCalories()
            fats += meals[it].getFats()
            carbs += meals[it].getCarbs()
        }

        mCaloriesNum.text = calories.toString()
        mFatsNum.text = fats.toString() + "g"
        mCarbsNum.text = carbs.toString() + "g"
    }

    companion object {
        private val fileName = "todayMeals.json"
    }

    /*
    fun setPieChart() {

        val nutrientGraph = findViewById<PieChart>(R.id.nutrientGraph)
        val nutrientType = ArrayList<String>()

        val colors = ArrayList<Int>()
        colors.add( 0xff82ff69.toInt())
        colors.add( 0xffff4747.toInt())
        colors.add( 0xff47cdff.toInt())

        nutrientType.add("Carbs")
        nutrientType.add("Fats")
        nutrientType.add("Proteins")

        val nutrientValue = ArrayList<Entry>()
        nutrientValue.add(Entry(22f, 0))
        nutrientValue.add(Entry(43f, 1))
        nutrientValue.add(Entry(35f, 2))



        val pieDataSet = PieDataSet(
            nutrientValue,
            ""
        )

        pieDataSet.sliceSpace = 2f
        pieDataSet.colors = colors
        nutrientGraph.holeRadius = 5f
        nutrientGraph.setBackgroundColor(0xffffffff.toInt())


        val data =  PieData(nutrientType, pieDataSet)

        nutrientGraph.data = data

    }
    */
}