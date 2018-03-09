package com.wisopt.ctscorecard

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.github.mikephil.charting.data.Entry
import kotlinx.android.synthetic.main.activity_main.*
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import android.widget.Toast
import com.github.mikephil.charting.highlight.Highlight
import kotlinx.android.synthetic.main.ct_maks_bar.*


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

        val dataSet = PieDataSet(yvalues, "")
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS)

        val data = PieData(xVals, dataSet)
        data.setValueTextSize(10f)
        //data.setValueTextColor(Color.WHITE)
        data.setValueFormatter(PercentFormatter())
        ctChart.animateXY(1400, 1400)
        ctChart.data = data
        ctChart.setDescription("")
        ctChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {

            override fun onValueSelected(e: Entry?, dataSetIndex: Int, h: Highlight) {
                // display msg when value selected
                if (e == null)
                    return

                marks_recycler_view.visibility = View.VISIBLE
                marks_expandless_button.visibility = View.VISIBLE
                runLayoutAnimation(marks_recycler_view)

                /*Toast.makeText(this@MainActivity,
                        xVals[e.xIndex] + " is " + e.`val` + "", Toast.LENGTH_SHORT).show()*/
            }

            override fun onNothingSelected() {

            }
        })

        marks_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        marks_recycler_view.adapter = CTScoreAdapter(marksList[0].ctDataArray)

        /*marksText.setOnClickListener({
            if (marks_recycler_view.visibility == View.GONE) {
                marks_recycler_view.visibility = View.VISIBLE
                marks_expandless_button.visibility = View.VISIBLE
            } else {
                marks_recycler_view.visibility = View.GONE
                marks_expandless_button.visibility = View.GONE
            }
        })*/
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
    private fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

        recyclerView.layoutAnimation = controller
        recyclerView.adapter.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }
}
