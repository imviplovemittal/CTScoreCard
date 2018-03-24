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
import kotlinx.android.synthetic.main.subject_marks_element.view.*

class CTSubjectAdapter(val list : ArrayList<SubjectWiseMarks>,val marksText: TextView, val ctChart : PieChart,val view :MainView) : RecyclerView.Adapter<CTSubjectAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)

        val feedView = inflater.inflate(R.layout.subject_marks_element, parent, false)
        val viewholder = CTSubjectAdapter.ViewHolder(feedView, view)

        return viewholder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindData(list[position])
    }

    class ViewHolder(itemView: View,val view: MainView) : RecyclerView.ViewHolder(itemView){
        fun bindData(subMarks : SubjectWiseMarks){
            itemView.marks_slot_name.text = subMarks.subSlot
            itemView.marks_sub_name.text = subMarks.subCode
            var cardSelected = 0
            itemView.sub_selector_card.setOnClickListener {
                onClickSetData(itemView)
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
        fun onClickSetData(itemView:View) {
            for (element in MainActivity.statified.marksList) {
                Log.d("", element.subSlot)
                if (element.subSlot.equals(itemView.marks_slot_name.text)) {
                    view.updateView(element)
                }
            }
    }

    }
}