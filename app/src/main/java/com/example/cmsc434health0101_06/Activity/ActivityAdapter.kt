package com.example.cmsc434health0101_06.Activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cmsc434health0101_06.R

class ActivityAdapter(val context: Context, private val items:ArrayList<ActivityT>):
    RecyclerView.Adapter<ActivityAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.activity_item_layout,
                        parent,
                        false
                )
            )
        }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items.get(position)
        holder.itemName.text = item.activityName
        holder.itemDate.text = item.activityDate
        holder.itemType.text = item.activityType
        holder.itemDuration.text = item.activityDuration.toString()
        holder.itemWeight.text = item.activityWeight.toString()
        holder.itemRep.text = item.activityRep.toString()

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName = view.cardName
        val itemDate = view.cardDate
        val itemType = view.cardType
        val itemDuration = view.cardDuration
        val itemWeight = view.cardWeight
        val itemRep = view.cardRep


    }

    override fun getItemCount(): Int {
        return items.size
    }

}