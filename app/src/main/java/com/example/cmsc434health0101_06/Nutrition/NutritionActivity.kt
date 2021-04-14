package com.example.cmsc434health0101_06.Nutrition

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cmsc434health0101_06.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet

import java.security.KeyStore

class NutritionActivity : AppCompatActivity() {



    @Override
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition)

        setPieChart()

        /*
        Nutrition plan:
        make another "daily" meal list and display the totals for calories, fats, and carbs at the top
        have user create the list by choosing meals from the original meal list. We might not have time
        to do the weekly tracker for it, so daily is fine for now.
         */
    }

    fun setPieChart() {

        val nutrientGraph = findViewById<PieChart>(R.id.nutrientGraph)
        val nutrientType = ArrayList<String>()

        val colors = ArrayList<Int>()
        colors.add( 0xff82ff69.toInt())
        colors.add( 0xffff4747.toInt())
        colors.add( 0xff47cdff.toInt())

        nutrientType.add("Carbs")
        nutrientType.add("Fats")
        nutrientType.add("Proteins")

        val nutrientValue = ArrayList<Entry>()
        nutrientValue.add(Entry(22f, 0))
        nutrientValue.add(Entry(43f, 1))
        nutrientValue.add(Entry(35f, 2))



        val pieDataSet = PieDataSet(
            nutrientValue,
            ""
        )

        pieDataSet.sliceSpace = 2f
        pieDataSet.colors = colors
        nutrientGraph.holeRadius = 5f
        nutrientGraph.setBackgroundColor(0xffffffff.toInt())


        val data =  PieData(nutrientType, pieDataSet)

        nutrientGraph.data = data

    }

}