package com.example.cmsc434health0101_06.Activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cmsc434health0101_06.R

class ActivityAdapter(val context: Context, val items:ArrayList<ActivityT>):
    RecyclerView.Adapter<ActivityAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.activity_item_layout,
                null,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemName!!.text = holder.mItemLayout!!.findViewById<TextView>(R.id.cardName).toString()
        holder.itemDate!!.text = holder.mItemLayout!!.findViewById<TextView>(R.id.cardDate).toString()
        holder.itemType!!.text = holder.mItemLayout!!.findViewById<TextView>(R.id.cardType).toString()
        holder.itemDuration!!.text = holder.mItemLayout!!.findViewById<TextView>(R.id.cardDuration).toString()
        holder.itemWeight!!.text = holder.mItemLayout!!.findViewById<TextView>(R.id.cardWeight).toString()
        holder.itemRep!!.text = holder.mItemLayout!!.findViewById<TextView>(R.id.cardRep).toString()

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mItemLayout: LinearLayout? = null
        val itemName: TextView? = null
        val itemDate: TextView? = null
        val itemType: TextView? = null
        val itemDuration: TextView? = null
        val itemWeight: TextView? = null
        val itemRep: TextView? = null

    }

    override fun getItemCount(): Int {
        return items.size
    }

}