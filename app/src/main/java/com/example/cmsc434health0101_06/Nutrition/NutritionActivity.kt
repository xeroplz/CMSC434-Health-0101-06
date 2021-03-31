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
    }

    fun setPieChart() {

        val nutrientGraph = findViewById<PieChart>(R.id.nutrientGraph)
        val nutrientType = ArrayList<String>()

        nutrientType.add("Carbs")
        nutrientType.add("Fats")
        nutrientType.add("Protiens")

        val nutrientValue = ArrayList<Entry>()
        nutrientValue.add(Entry(22f, 0))
        nutrientValue.add(Entry(43f, 1))
        nutrientValue.add(Entry(35f, 2))



        val pieDataSet = PieDataSet(
            nutrientValue,
            "Nutrients"
        )

        val data =  PieData(nutrientType, pieDataSet)

        nutrientGraph.data = data

    }

}