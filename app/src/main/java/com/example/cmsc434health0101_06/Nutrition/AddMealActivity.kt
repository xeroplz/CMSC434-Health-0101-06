package com.example.cmsc434health0101_06.Nutrition

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.cmsc434health0101_06.R

class AddMealActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    internal lateinit var mAdapter: FoodListAdapter
    private lateinit var mViewMealHeader: EditText
    private var foodList = ArrayList<Food>()

    private lateinit var mSaveButton: Button
    private lateinit var mCancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_meal)

        // Bar Title
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Add New Meal"
        }

        listView = findViewById(R.id.foodListView)
        mAdapter = FoodListAdapter(applicationContext)
        mViewMealHeader = findViewById(R.id.viewMealHeader)
        mSaveButton = findViewById(R.id.saveButton)
        mCancelButton = findViewById(R.id.cancelButton)

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
            // TODO - Clicking this will reset current entries in the list... rip
        }

        // TODO - Attach the adapter to this ListActivity's ListView
        listView.adapter = mAdapter

        // Save button
        // Save completely new meal.
        // Do not allow foods with blank names.
        mSaveButton.setOnClickListener {
            if (mAdapter.count != 0) {
                val children = mAdapter.count
                val listIndices = children - 1
                val newFoodList = ArrayList<Food>()

                (0..listIndices).forEach {
                    val v = listView.getChildAt(it)
                    val foodName = v.findViewById(R.id.titleView) as EditText
                    val calories = v.findViewById(R.id.caloriesView) as EditText
                    val fats = v.findViewById(R.id.fatsView) as EditText
                    val carbs = v.findViewById(R.id.carbsView) as EditText

                    val foodNameString = foodName.text.toString()
                    val caloriesNumeric = calories.text.toString().toIntOrNull()
                    val fatsNumeric = fats.text.toString().toDoubleOrNull()
                    val carbsNumeric = carbs.text.toString().toDoubleOrNull()

                    if (foodNameString != null && caloriesNumeric != null && fatsNumeric != null
                        && carbsNumeric != null) {
                        if (foodNameString.equals("")) {
                            Toast.makeText(applicationContext, "Food names cannot be empty.",
                                Toast.LENGTH_LONG).show()
                        } else {
                            newFoodList.add(
                                Food(foodNameString, caloriesNumeric, fatsNumeric, carbsNumeric)
                            )
                        }
                    } else {
                        Toast.makeText(applicationContext, "There is an invalid value in your food entry.",
                            Toast.LENGTH_LONG).show()
                    }
                }

                val mealName = mViewMealHeader.text.toString()
                if (newFoodList.size == children && mealName != null) {
                    if (!mealName.equals("")) {
                        val meals = Meal.getSavedMeals(applicationContext)
                        if (meals.find { m: Meal -> m.mealName.equals(mealName) } == null) {
                            Meal.addMeal(applicationContext, Meal(mViewMealHeader.text.toString(), newFoodList))
                            finish()
                        } else {
                            Toast.makeText(applicationContext, "You already have this meal added.",
                                Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Meal names cannot be empty.",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        // Cancel button
        mCancelButton.setOnClickListener {
            finish()
        }
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

    companion object {
        private val fileName = "meals.json"
        private val TAG = "HEALTH010106"
    }
}