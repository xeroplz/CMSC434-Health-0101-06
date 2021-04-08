package com.example.cmsc434health0101_06.Nutrition

import android.content.Context
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.RelativeLayout
import android.widget.TextView
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import com.example.cmsc434health0101_06.R

class MealListAdapter(private val mContext: Context) : BaseAdapter() {

    private val mItems = ArrayList<Meal>()

    // Add a Meal to the adapter
    // Notify observers that the data set has changed

    fun add(item: Meal) {

        mItems.add(item)
        notifyDataSetChanged()

    }

    // Clears the list adapter of all items.

    fun clear() {

        mItems.clear()
        notifyDataSetChanged()

    }

    // Returns the number of Meals

    override fun getCount(): Int {

        return mItems.size

    }

    // Retrieve the number of Meals

    override fun getItem(pos: Int): Any {

        return mItems[pos]

    }

    // Get the ID for the Meal
    // In this case it's just the position

    override fun getItemId(pos: Int): Long {

        return pos.toLong()

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {

        // TODO - Get the current Meal
        val curr = mItems[position]

        val viewHolder: ViewHolder

        if (null == convertView) {

            viewHolder = ViewHolder()

            // TODO - Inflate the View for this ToDoItem
            val inflater = LayoutInflater.from(mContext)
            viewHolder.mItemLayout = (inflater.inflate(R.layout.meal_item, null, false)) as RelativeLayout
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        // TODO - Fill in specific Meal data
        // Remember that the data that goes in this View
        // corresponds to the user interface elements defined
        // in the layout file
        viewHolder.mTitleView = viewHolder.mItemLayout!!.findViewById(R.id.titleView) as TextView
        viewHolder.mCaloriesView = viewHolder.mItemLayout!!.findViewById(R.id.caloriesView) as TextView
        viewHolder.mFatsView = viewHolder.mItemLayout!!.findViewById(R.id.fatsView) as TextView
        viewHolder.mCarbsView = viewHolder.mItemLayout!!.findViewById(R.id.carbsView) as TextView

        // Set view title
        viewHolder.mTitleView!!.text = curr.mealName

        // Get total calories, fats, and carbs in the meal
        var calories = 0
        var fats = 0.0
        var carbs = 0.0
        val indices = curr.foods.size - 1
        (0..indices).forEach {
            calories += curr.foods[it].calories
            fats += curr.foods[it].fats
            carbs += curr.foods[it].carbs
        }

        viewHolder.mCaloriesView!!.text = calories.toString()
        viewHolder.mFatsView!!.text = fats.toString() + "g"
        viewHolder.mCarbsView!!.text = carbs.toString() + "g"

        return viewHolder.mItemLayout

    }

    internal class ViewHolder {
        var position: Int = 0
        var mItemLayout: RelativeLayout? = null
        var mTitleView: TextView? = null
        var mCaloriesView: TextView? = null
        var mFatsView: TextView? = null
        var mCarbsView: TextView? = null
    }
}