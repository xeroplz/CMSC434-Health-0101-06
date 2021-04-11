package com.example.cmsc434health0101_06.Nutrition

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cmsc434health0101_06.R
import java.io.File
import java.io.FileNotFoundException
import java.util.*
import android.view.ContextMenu
import kotlin.collections.ArrayList
import android.widget.Toast
import android.content.Intent


class MealManagerActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    internal lateinit var mAdapter: MealListAdapter
    private var clickedMealPos = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_manager)

        /* Now for the actual work... Just need to have meal add and delete functionality.
        Plus a list of all meals currently in the list. List view will have the meal's name,
        total calories, total fats, and total carbs visible.
        Clicking on a meal will show a context menu that has 3 buttons. View, Delete, Cancel.
        Each of these should be self explanatory
        */
        listView = findViewById(R.id.mealListView)
        mAdapter = MealListAdapter(applicationContext)

        // Put divider between ToDoItems and FooterView
        listView.setFooterDividersEnabled(true)

        // TODO - Inflate footerView for footer_view.xml file
        val footerView = layoutInflater.inflate(R.layout.meal_list_footer, null, false) as TextView

        // TODO - Add footerView to ListView
        listView.addFooterView(footerView)

        // TODO - Attach the adapter to this ListActivity's ListView
        listView.adapter = mAdapter
        registerForContextMenu(listView)

        // Context menu for every item
        listView.setOnItemClickListener { parent, view, position, id ->
            clickedMealPos = position
            view.showContextMenu()
            //Toast.makeText(applicationContext, "Position: $position", Toast.LENGTH_LONG).show()
        }

        // Overwrite footer behavior since it would also make a context menu
        val startAdd = Intent(this, AddMealActivity::class.java)
        footerView.setOnClickListener {
            startActivityForResult(startAdd,  ADD_MEAL_ITEM_REQUEST)
        }

        // -------------------------------------------------
        // Test data - delete later.
        // Create Food and Meals

        val foods = ArrayList<Food>()
        val banana = Food("Banana", 105, 0.4, 27.0)
        val apple = Food("Apple", 95, 0.3, 25.0)
        val smokedChicken = Food("Smoked Chicken", 320, 16.0, 11.0)
        foods.addAll(arrayOf(banana, apple, smokedChicken))
        val meal1 = Meal("Breakfast", foods)
        Meal.addMeal(applicationContext, meal1)

        val foods2 = ArrayList<Food>()
        val salmon = Food("Salmon", 200, 11.4, 23.0)
        val fries = Food("Fries", 320, 16.0, 48.0)
        val bagel = Food("Bagel", 110, 7.0, 32.0)
        foods2.addAll(arrayOf(salmon, fries, bagel))
        val meal2 = Meal("Lunch", foods2)
        Meal.addMeal(applicationContext, meal2)
        // -------------------------------------------------


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

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val mealList = Meal.getSavedMeals(applicationContext)
        menu!!.setHeaderTitle(mealList[clickedMealPos].mealName)
        menu!!.add(0, v!!.id, 0, "View / Edit")
        menu!!.add(0, v!!.id, 0, "Delete")
        menu!!.add(0, v!!.id, 0, "Cancel")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val mealList = Meal.getSavedMeals(applicationContext)
        val size = mealList.size
        if (item.title === "View / Edit") {
            if (clickedMealPos < 0 || clickedMealPos >= size) return true

            // Open ViewMealActivity
            val startView = Intent(this, ViewMealActivity::class.java)
            startView.putExtra("selectedMealPos", clickedMealPos)
            startActivity(startView)
        } else if (item.title === "Delete") {
            if (clickedMealPos < 0 || clickedMealPos >= size) return true

            // Delete meal
            val mealName = mealList[clickedMealPos].mealName
            Meal.removeMeal(applicationContext, mealName)
            mAdapter.clear()
            loadItems()
        }

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete All")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            MENU_DELETE -> {
                // Delete json file
                val fileDir = applicationContext.filesDir.absolutePath
                try {
                    val file = File(fileDir, fileName)
                    val deleted = file.delete()
                } catch (e: FileNotFoundException) {
                    return true
                }

                mAdapter.clear()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
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

    companion object {
        // IDs for menu items
        private val MENU_DELETE = Menu.FIRST

        private val ADD_MEAL_ITEM_REQUEST = 0

        private val fileName = "meals.json"
        private val TAG = "HEALTH010106"
    }
}