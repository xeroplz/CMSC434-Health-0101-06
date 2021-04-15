package com.example.cmsc434health0101_06.Activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cmsc434health0101_06.Nutrition.Meal
import com.example.cmsc434health0101_06.R
import java.util.ArrayList

class ActivityAdapter(private val mContext: Context) : BaseAdapter() {

    private val mItems = ArrayList<ActivityT>()

    // Add an ActivityT to the adapter
    // Notify observers that the data set has changed

    fun add(item: ActivityT) {

        mItems.add(item)
        notifyDataSetChanged()

    }

    // Clears the list adapter of all items.

    fun clear() {

        mItems.clear()
        notifyDataSetChanged()

    }

    // Returns the number of Activities

    override fun getCount(): Int {

        return mItems.size

    }

    // Retrieve the number of Activities

    override fun getItem(pos: Int): Any {

        return mItems[pos]

    }

    // Get the ID for the activity
    // In this case it's just the position

    override fun getItemId(pos: Int): Long {

        return pos.toLong()

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {

        // TODO - Get the current Meal
        val curr = mItems[position]

        val viewHolder: ViewHolder
        viewHolder = ViewHolder()

        // TODO - Inflate the View for this Meal
        val inflater = LayoutInflater.from(mContext)
        viewHolder.mItemLayout = (inflater.inflate(R.layout.activity_item, null, false)) as RelativeLayout

        // TODO - Fill in specific Meal data
        // Remember that the data that goes in this View
        // corresponds to the user interface elements defined
        // in the layout file
        viewHolder.mNameView = viewHolder.mItemLayout!!.findViewById(R.id.activityNameLabel) as TextView
        viewHolder.mDateView = viewHolder.mItemLayout!!.findViewById(R.id.activityDateView) as TextView
        viewHolder.mTypeView = viewHolder.mItemLayout!!.findViewById(R.id.activityTypeView) as TextView
        viewHolder.mDurationView = viewHolder.mItemLayout!!.findViewById(R.id.activityDurationView) as TextView
        viewHolder.mWeightView = viewHolder.mItemLayout!!.findViewById(R.id.activityWeightView) as TextView
        viewHolder.mRepsView = viewHolder.mItemLayout!!.findViewById(R.id.activityRepsView) as TextView

        // Set view title
        viewHolder.mNameView!!.text = curr.activityName
        viewHolder.mDateView!!.text = curr.activityDate
        viewHolder.mTypeView!!.text = curr.activityType
        viewHolder.mDurationView!!.text = curr.activityDuration.toString() + " minutes"
        viewHolder.mWeightView!!.text = curr.activityWeight.toString() + " lbs"
        viewHolder.mRepsView!!.text = curr.activityRep.toString()

        return viewHolder.mItemLayout
    }

    internal class ViewHolder {
        var position: Int = 0
        var mItemLayout: RelativeLayout? = null
        var mNameView: TextView? = null
        var mDateView: TextView? = null
        var mTypeView: TextView? = null
        var mDurationView: TextView? = null
        var mWeightView: TextView? = null
        var mRepsView: TextView? = null
    }
}