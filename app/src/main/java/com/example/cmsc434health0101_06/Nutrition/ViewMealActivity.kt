package com.example.cmsc434health0101_06.Nutrition

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.cmsc434health0101_06.R


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
        mSaveButton = findViewById(R.id.saveButton)
        mDeleteButton = findViewById(R.id.deleteButton)

        val fromIntent = intent
        clickedMealPos = fromIntent.extras!!.getInt("selectedMealPos")

        // Sanity checks
        if (clickedMealPos == -1) finish()

        val mealList = Meal.getSavedMeals(applicationContext)
        if (mealList.isEmpty()) finish()

        val selectedMeal = mealList[clickedMealPos]
        foodList = selectedMeal.foods
        mViewMealHeader.setText(selectedMeal.mealName)

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
        mSaveButton.setOnClickListener {
            Log.i(TAG, "clicked save button")
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
                        Meal.removeMeal(applicationContext, selectedMeal.mealName)
                        Meal.addMeal(applicationContext, Meal(mViewMealHeader.text.toString(), newFoodList))
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Meal names cannot be empty.",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        // Delete button
        // Delete meal at [clickedMealPos] and finish()
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Delete Meal")
        builder.setMessage("Are you sure you want to delete ${selectedMeal.mealName}?")

        builder.setPositiveButton(
            "YES"
        ) { dialog, which -> // Do nothing but close the dialog
            Meal.removeMeal(applicationContext, selectedMeal.mealName)
            finish()
        }

        builder.setNegativeButton(
            "NO"
        ) { dialog, which -> // Do nothing
            dialog.dismiss()
        }

        val alert = builder.create()
        mDeleteButton.setOnClickListener {
            alert.show()
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