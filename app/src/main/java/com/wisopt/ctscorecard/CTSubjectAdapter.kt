package com.wisopt.ctscorecard

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.subject_marks_element.view.*

/**
 * Created by viplo on 21-Mar-18.
 */
class CTSubjectAdapter(val list : ArrayList<SubjectWiseMarks>) : RecyclerView.Adapter<CTSubjectAdapter.ViewHolder>(){
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
                if(cardSelected == 0){
                    itemView.sub_selector_card.setCardBackgroundColor(Color.parseColor("#eeeeee"))
                    cardSelected = 1
                }
                else{
                    cardSelected = 0
                    itemView.sub_selector_card.setCardBackgroundColor(Color.parseColor("#ffffff"))
                }
            }
        }
    }
}