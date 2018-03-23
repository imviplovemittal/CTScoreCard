package com.wisopt.ctscorecard

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.subject_marks_element.view.*

class CTSubjectAdapter(val list : ArrayList<SubjectWiseMarks>,val marksText: TextView, val ctChart : com.github.mikephil.charting.charts.PieChart) : RecyclerView.Adapter<CTSubjectAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)

        val feedView = inflater.inflate(R.layout.subject_marks_element, parent, false)
        val viewholder = CTSubjectAdapter.ViewHolder(feedView)

        return viewholder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindData(list[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindData(subMarks : SubjectWiseMarks){
            itemView.marks_slot_name.text = subMarks.subSlot
            itemView.marks_sub_name.text = subMarks.subCode
            var cardSelected = 0
            itemView.sub_selector_card.setOnClickListener {
                //0onClickSetData(itemView)
                if(cardSelected == 0){
                    itemView.marks_slot_name.setBackgroundResource(R.drawable.subject_background_color_dark)
                    itemView.marks_sub_name.setBackgroundResource(R.drawable.slot_background_dark)
                    itemView.marks_slot_name.setTextColor(Color.parseColor("#ffffff"))
                    itemView.marks_sub_name.setTextColor(Color.parseColor("#ffffff"))
                    cardSelected = 1
                }
                else{
                    cardSelected = 0
                    itemView.marks_slot_name.setBackgroundResource(R.drawable.subject_background_circle)
                    itemView.marks_sub_name.setBackgroundResource(R.drawable.slot_background)
                    itemView.marks_slot_name.setTextColor(Color.parseColor("#757575"))
                    itemView.marks_sub_name.setTextColor(Color.parseColor("#757575"))
                }
            }
        }
    }
    fun onClickSetData(itemView:View) {
        for (element in MainActivity.statified.marksList) {
            Log.d("", element.subSlot)
            if (element.subSlot.equals(itemView.marks_slot_name.text)) {
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
    }
}