package com.example.cmsc434health0101_06.Nutrition

import android.content.Context
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.CompoundButton.OnCheckedChangeListener
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import com.example.cmsc434health0101_06.R

class FoodListAdapter(private val mContext: Context) : BaseAdapter() {

    private val mItems = ArrayList<Food>()

    // Add a Meal to the adapter
    // Notify observers that the data set has changed

    fun add(item: Food) {

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

        // TODO - Get the current Food
        val curr = mItems[position]

        val viewHolder: ViewHolder

        viewHolder = ViewHolder()

        // TODO - Inflate the View for this Food
        val inflater = LayoutInflater.from(mContext)
        viewHolder.mItemLayout = (inflater.inflate(R.layout.food_item, null, false)) as RelativeLayout

        // TODO - Fill in specific Food data
        // Remember that the data that goes in this View
        // corresponds to the user interface elements defined
        // in the layout file
        viewHolder.mTitleView = viewHolder.mItemLayout!!.findViewById(R.id.titleView) as EditText
        viewHolder.mCaloriesView = viewHolder.mItemLayout!!.findViewById(R.id.caloriesView) as EditText
        viewHolder.mFatsView = viewHolder.mItemLayout!!.findViewById(R.id.fatsView) as EditText
        viewHolder.mCarbsView = viewHolder.mItemLayout!!.findViewById(R.id.carbsView) as EditText

        viewHolder.mTitleView!!.setText("${curr.foodName}")
        viewHolder.mCaloriesView!!.setText("${curr.calories}")
        viewHolder.mFatsView!!.setText("${curr.fats}")
        viewHolder.mCarbsView!!.setText("${curr.carbs}")

        viewHolder.mTitleView!!.hint = "Food Name"
        viewHolder.mCaloriesView!!.hint = "0"
        viewHolder.mFatsView!!.hint = "0.0"
        viewHolder.mCarbsView!!.hint = "0.0"

        return viewHolder.mItemLayout
    }

    internal class ViewHolder {
        var position: Int = 0
        var mItemLayout: RelativeLayout? = null
        var mTitleView: EditText? = null
        var mCaloriesView: EditText? = null
        var mFatsView: EditText? = null
        var mCarbsView: EditText? = null
    }
}