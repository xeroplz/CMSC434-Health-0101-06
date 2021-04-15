package com.example.cmsc434health0101_06.Nutrition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ListView
import com.example.cmsc434health0101_06.R

class NutritionSelectMealActivity : AppCompatActivity() {

    private lateinit var mListView : ListView
    private lateinit var mCancelButton : Button
    internal lateinit var mAdapter: MealListAdapter
    private var clickedMealPos = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition_select_meal)

        // Bar Title
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Select Meal"
        }

        mListView = findViewById(R.id.mealListView)
        mCancelButton = findViewById(R.id.cancelButton)
        mAdapter = MealListAdapter(applicationContext)
        mListView.adapter = mAdapter

        registerForContextMenu(mListView)

        mCancelButton.setOnClickListener {
            finish()
        }

        // Context menu for every item
        mListView.setOnItemClickListener { parent, view, position, id ->
            clickedMealPos = position
            view.showContextMenu()
        }
    }

    public override fun onResume() {
        super.onResume()

        // Get items in case the file changed
        mAdapter.clear()
        loadItems()
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val mealList = Meal.getSavedMeals(applicationContext)
        menu!!.setHeaderTitle(mealList[clickedMealPos].mealName)
        menu!!.add(0, v!!.id, 0, "Select")
        menu!!.add(0, v!!.id, 0, "Cancel")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val mealList = Meal.getSavedMeals(applicationContext)
        val size = mealList.size

        if (item.title === "Select") {
            if (clickedMealPos < 0 || clickedMealPos >= size) return true

            // Add selected meal to daily meal list and finish
            val selectedMeal = mealList[clickedMealPos]
            Meal.addDailyMeal(applicationContext, selectedMeal)
            finish()
        }

        return true
    }

    // Load stored Meals
    private fun loadItems() {
        // Get meals from JSON
        val mealList = Meal.getSavedMeals(applicationContext)
        if (mealList.isEmpty()) return

        val indices = mealList.size - 1
        (0..indices).forEach {
            mAdapter.add(mealList[it])
        }
    }
}