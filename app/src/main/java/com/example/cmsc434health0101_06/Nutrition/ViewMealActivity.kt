package com.example.cmsc434health0101_06.Nutrition

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cmsc434health0101_06.R
import java.io.File
import java.io.FileNotFoundException
import java.util.*
import android.view.ContextMenu
import kotlin.collections.ArrayList
import android.content.Intent
import android.widget.*
import org.w3c.dom.Text

class ViewMealActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    internal lateinit var mAdapter: FoodListAdapter
    private lateinit var mViewMealHeader: EditText
    private var foodList = ArrayList<Food>()
    private var clickedMealPos = -1

    private lateinit var mSaveButton: Button
    private lateinit var mDeleteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_meal)

        listView = findViewById(R.id.foodListView)
        mAdapter = FoodListAdapter(applicationContext)
        mViewMealHeader = findViewById(R.id.viewMealHeader)

        val fromIntent = intent
        clickedMealPos = fromIntent.extras!!.getInt("selectedMealPos")

        // Sanity checks
        if (clickedMealPos == -1) finish()

        val mealList = Meal.getSavedMeals(applicationContext)
        if (mealList.isEmpty()) finish()

        foodList = mealList[clickedMealPos].foods
        mViewMealHeader.setText(mealList[clickedMealPos].mealName)

        // Put divider between ToDoItems and FooterView
        listView.setFooterDividersEnabled(true)

        // TODO - Inflate footerView for footer_view.xml file
        val footerView = layoutInflater.inflate(R.layout.food_list_footer, null, false) as TextView

        // TODO - Add footerView to ListView
        listView.addFooterView(footerView)

        // TODO - Attach Listener to FooterView
        footerView.setOnClickListener {
            val dummyFood = Food("", 0, 0.0, 0.0)
            foodList.add(dummyFood)
            mAdapter.add(dummyFood)
            //Toast.makeText(applicationContext, "Added food", Toast.LENGTH_LONG).show()
        }

        // TODO - Attach the adapter to this ListActivity's ListView
        listView.adapter = mAdapter

        // Save button
        // Delete [this] meal, save food data to a new food list, repackage meal.
        // Do not allow foods with blank names.

        // Delete button
        // Delete meal at [clickedMealPos] and finish()
    }

    public override fun onResume() {
        super.onResume()

        // Get items in case the file changed
        mAdapter.clear()
        loadItems()
    }

    override fun onPause() {
        super.onPause()
    }

    // Load stored Meals
    private fun loadItems() {
        // Get meals from JSON
        if (foodList.isEmpty()) return

        val indices = foodList.size - 1
        (0..indices).forEach {
            mAdapter.add(foodList[it])
        }
    }

    // Save ToDoItems to file
    private fun saveItems() {

    }

    companion object {
        private val fileName = "meals.json"
        private val TAG = "HEALTH010106"
    }
}