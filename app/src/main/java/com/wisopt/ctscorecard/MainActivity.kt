package com.wisopt.ctscorecard

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Adapter
import android.widget.LinearLayout
import com.github.mikephil.charting.data.Entry
import kotlinx.android.synthetic.main.activity_main.*
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet
import com.github.mikephil.charting.utils.ColorTemplate


class MainActivity : AppCompatActivity() {

    object statified{
        var mContext : Context?= null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statified.mContext = baseContext

        ctChart.setUsePercentValues(true)

        val yvalues = ArrayList<Entry>()
        val xVals = ArrayList<String>()
        val marksList = ArrayList<SubjectWiseMarks>()
        val subMarksList = ArrayList<CTData>()
        subMarksList.add(CTData("CYCLE TEST - 1", 10f, 15f))
        subMarksList.add(CTData("CYCLE TEST - 2", 20f, 25f))
        subMarksList.add(CTData("Q/A", 5f, 5f))
        subMarksList.add(CTData("SURPRIZE TEST", 5f, 5f))

        marksList.add(SubjectWiseMarks("15CS205J", "Microprocessors", "Theory", subMarksList))
        calculateData(subMarksList, yvalues, xVals)

        val dataSet = PieDataSet(yvalues, "Contribution in Total")
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS)

        val data = PieData(xVals, dataSet)
        data.setValueTextSize(10f)
        //data.setValueTextColor(Color.WHITE)
        data.setValueFormatter(PercentFormatter())
        ctChart.animateXY(1400, 1400)
        ctChart.data = data
        ctChart.setDescription("Sem 4")

        marks_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        marks_recycler_view.adapter = CTScoreAdapter(marksList[0].ctDataArray)

        marksText.setOnClickListener({
            if (marks_recycler_view.visibility == View.GONE) {
                marks_recycler_view.visibility = View.VISIBLE
                marks_expandless_button.visibility = View.VISIBLE
            } else {
                marks_recycler_view.visibility = View.GONE
                marks_expandless_button.visibility = View.GONE
            }
        })
        marks_expandless_button.setOnClickListener({
            marks_recycler_view.visibility = View.GONE
            marks_expandless_button.visibility = View.GONE
        })
    }

    fun calculateData(subMarksList: ArrayList<CTData>, yValues: ArrayList<Entry>, xVals: ArrayList<String>) {
        var totalMarksGot = 0.0
        var totalMarks = 0f
        for (test in subMarksList) {
            totalMarksGot += test.marksGot
            totalMarks += test.marksOutOf
        }
        val marksString = "" + totalMarksGot + "/" + totalMarks
        marksText.text = marksString
        for (i in 0..subMarksList.size - 1) {
            yValues.add(Entry((subMarksList[i].marksGot / totalMarks) * 100f, i))
            xVals.add(subMarksList[i].testName)
        }
    }
}
