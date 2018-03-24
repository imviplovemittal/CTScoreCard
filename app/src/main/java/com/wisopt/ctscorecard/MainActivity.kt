package com.wisopt.ctscorecard

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
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
import com.wisopt.ctscorecard.MainActivity.statified.marksList
import com.wisopt.ctscorecard.R.id.marksText
import kotlinx.android.synthetic.main.ct_maks_bar.*


class MainActivity : AppCompatActivity(), MainView {

    object statified {
        var mContext: Context? = null
        val marksList = ArrayList<SubjectWiseMarks>()
    }

    var yvalues : ArrayList<Entry> ?= null
    var xvals : ArrayList<String> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statified.mContext = baseContext
        ctChart.setUsePercentValues(true)

        yvalues = ArrayList<Entry>()
        xvals = ArrayList<String>()

        val subMarksList = ArrayList<CTData>()
        subMarksList.add(CTData("CYCLE TEST - 1", 10f, 15f))
        subMarksList.add(CTData("CYCLE TEST - 2", 20f, 25f))
        /*subMarksList.add(CTData("Q/A", 5f, 5f))
        subMarksList.add(CTData("SURPRIZE TEST", 5f, 5f))*/

        val subMarksList2 = ArrayList<CTData>()
        subMarksList2.add(CTData("CYCLE TEST - 1", 8.5f, 15f))
        subMarksList2.add(CTData("CYCLE TEST - 2", 25f, 25f))
        marksList.add(SubjectWiseMarks("A", "15CS205J", "Microprocessors", "Theory", subMarksList))
        marksList.add(SubjectWiseMarks("B", "15SE205J", "Computer System Architechture", "Theory", subMarksList2))
        marksText.text = calculateData(subMarksList, yvalues!!, xvals!!)

        setChartData()
        ctChart.setOnClickListener {
            marks_recycler_view.visibility = View.VISIBLE
            marks_expandless_button.visibility = View.VISIBLE
            expand_view.visibility = View.VISIBLE
        }

        marks_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        marks_recycler_view.adapter = CTScoreAdapter(marksList[0].ctDataArray)
        subject_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        subject_recycler_view.adapter = CTSubjectAdapter(marksList,marksText,ctChart,this)
        marks_expandless_button.setOnClickListener({
            marks_recycler_view.visibility = View.GONE
            marks_expandless_button.visibility = View.GONE
            expand_view.visibility = View.GONE
        })
    }

    fun calculateData(subMarksList: ArrayList<CTData>, yValues: ArrayList<Entry> = yvalues!!, xVals: ArrayList<String> = xvals!!) : String {
        var totalMarksGot = 0.0
        var totalMarks = 0f
        for (test in subMarksList) {
            totalMarksGot += test.marksGot
            totalMarks += test.marksOutOf
        }
        val marksString = "" + totalMarksGot + "/" + totalMarks
        for (i in 0..subMarksList.size - 1) {
            yValues.add(Entry((subMarksList[i].marksGot / totalMarks) * 100f, i))
            xVals.add(subMarksList[i].testName)
        }
        return marksString
    }

    private fun setChartData(){
        val dataSet = PieDataSet(yvalues, "")
        val colorsList: IntArray = intArrayOf(Color.parseColor("#FF5733"), Color.parseColor("#1CFFD6"), Color.parseColor("#1C38FF"), Color.parseColor("#010730"))
        dataSet.setColors(colorsList)

        val data = PieData(xvals, dataSet)
        data.setValueTextSize(0f)
        //data.setValueTextColor(Color.WHITE)
        data.setValueFormatter(PercentFormatter())
        ctChart.animateXY(1400, 1400)
        ctChart.data = data
        ctChart.setDescription("")
        ctChart.setDrawSliceText(false)
        ctChart.elevation = 0f
        ctChart.setTouchEnabled(false)
    }
    private fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

        recyclerView.layoutAnimation = controller
        recyclerView.adapter.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }

    override fun updateView(element:SubjectWiseMarks) {
        val yvalues = ArrayList<Entry>()
        val xvals = ArrayList<String>()
        var totalMarksGot = 0.0
        var totalMarks = 0f
        for (test in element.ctDataArray) {
            totalMarksGot += test.marksGot
            totalMarks += test.marksOutOf
        }
        val marksString = "" + totalMarksGot + "/" + totalMarks
        for (i in 0..element.ctDataArray.size - 1) {
            yvalues.add(Entry((element.ctDataArray[i].marksGot / totalMarks) * 100f, i))
            xvals.add(element.ctDataArray[i].testName)
        }
        marksText.text = marksString
        val dataSet = PieDataSet(yvalues, "")
        val colorsList: IntArray = intArrayOf(Color.parseColor("#FF5733"), Color.parseColor("#1CFFD6"), Color.parseColor("#1C38FF"), Color.parseColor("#010730"))
        dataSet.setColors(colorsList)

        val data = PieData(xvals, dataSet)
        data.setValueTextSize(0f)
        //data.setValueTextColor(Color.WHITE)
        data.setValueFormatter(PercentFormatter())
        ctChart.animateXY(1400, 1400)
        ctChart.data = data
        ctChart.setDescription("")
        ctChart.setDrawSliceText(false)
        ctChart.elevation = 0f
        ctChart.setTouchEnabled(false)
    }
}
