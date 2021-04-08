package com.example.cmsc434health0101_06.Nutrition

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cmsc434health0101_06.R
import java.io.File
import java.io.FileNotFoundException
import java.util.*

class MealManagerActivity : AppCompatActivity() {

    private val fileName = "meals.json"
    private lateinit var listView: ListView
    internal lateinit var mAdapter: MealListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_manager)

        /* EXAMPLE READ/WRITE CODE FOR MEALS ------------------------------------------------------
        // Uncomment to make it work, of course.

        // Create Food and Meals
        var meals = ArrayList<Meal>()

        val foods = ArrayList<Food>()
        val banana = Food("Banana", 105, 0.4, 27.0)
        val apple = Food("Apple", 95, 0.3, 25.0)
        val smokedChicken = Food("Smoked Chicken", 320, 16.0, 11.0)
        foods.addAll(arrayOf(banana, apple, smokedChicken))
        val meal1 = Meal("Breakfast", foods)
        meals.add(meal1)

        val foods2 = ArrayList<Food>()
        val salmon = Food("Salmon", 200, 11.4, 23.0)
        val fries = Food("Fries", 320, 16.0, 48.0)
        val bagel = Food("Bagel", 110, 7.0, 32.0)
        foods2.addAll(arrayOf(salmon, fries, bagel))
        val meal2 = Meal("Lunch", foods2)
        meals.add(meal2)

        // Empty meal reading test
        var readMeals = Meal.getSavedMeals(applicationContext)

        // Write meals!
        // They should appear in /data/user/0/com.example.cmsc434health0101_06/files/meals.json
        Meal.saveMeals(applicationContext, meals)

        // Clear out original meals
        meals = ArrayList<Meal>()

        // Read saved meals
        readMeals = Meal.getSavedMeals(applicationContext)

        // If the list isn't empty, it's valid!
        if (!readMeals.isEmpty()) meals = readMeals

        // Check out the meals object in debug. If it's empty, the read failed. If it's filled, the
        // read was a success!

        // Redundant line for you to use as a breakpoint
        meals = meals

        ---------------------------------------------------------------------------------------- */

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

        // TODO - Attach Listener to FooterView
        /*
        val startAdd = Intent(this, AddToDoActivity::class.java)
        footerView.setOnClickListener {
            startActivityForResult(startAdd,  ADD_TODO_ITEM_REQUEST)
        }
        */

        // TODO - Attach the adapter to this ListActivity's ListView
        listView.adapter = mAdapter

        // Test data - delete later.
        // Create Food and Meals
        var meals = ArrayList<Meal>()

        val foods = ArrayList<Food>()
        val banana = Food("Banana", 105, 0.4, 27.0)
        val apple = Food("Apple", 95, 0.3, 25.0)
        val smokedChicken = Food("Smoked Chicken", 320, 16.0, 11.0)
        foods.addAll(arrayOf(banana, apple, smokedChicken))
        val meal1 = Meal("Breakfast", foods)
        meals.add(meal1)

        val foods2 = ArrayList<Food>()
        val salmon = Food("Salmon", 200, 11.4, 23.0)
        val fries = Food("Fries", 320, 16.0, 48.0)
        val bagel = Food("Bagel", 110, 7.0, 32.0)
        foods2.addAll(arrayOf(salmon, fries, bagel))
        val meal2 = Meal("Lunch", foods2)
        meals.add(meal2)

        Meal.saveMeals(applicationContext, meals)
    }

    public override fun onResume() {
        super.onResume()

        // Load saved ToDoItems, if necessary

        if (mAdapter.count == 0)
            loadItems()
    }

    override fun onPause() {
        super.onPause()
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
        val meals = Meal.getSavedMeals(applicationContext)
        if (meals.isEmpty()) return

        val indices = meals.size - 1
        (0..indices).forEach {
            mAdapter.add(meals[it])
        }
    }

    // Save ToDoItems to file
    private fun saveItems() {

    }

    companion object {
        // IDs for menu items
        private val MENU_DELETE = Menu.FIRST
        private val MENU_DUMP = Menu.FIRST + 1

        private val fileName = "meals.json"
        private val TAG = "HEALTH010106"
    }
}