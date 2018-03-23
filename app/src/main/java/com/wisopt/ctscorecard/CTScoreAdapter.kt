package com.wisopt.ctscorecard

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ct_maks_bar.view.*

/**
 * Created by viplo on 09-Mar-18.
 */
class CTScoreAdapter(val list : ArrayList<CTData>) : RecyclerView.Adapter<CTScoreAdapter.ViewHolder>() {
    val colorList = listOf(R.color.aqua,R.color.blue,R.color.dark,R.color.orange)
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)

        val feedView = inflater.inflate(R.layout.ct_maks_bar, parent, false)
        val viewholder = ViewHolder(feedView)

        return viewholder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindData(list[position])
        val mDrawable = ContextCompat.getDrawable(MainActivity.statified.mContext,R.drawable.color_circle)
        DrawableCompat.setTint(mDrawable,ContextCompat.getColor(MainActivity.statified.mContext,colorList[position]))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(ctData : CTData){
            val marksString : String = ""+ctData.marksGot+"/"+ctData.marksOutOf
            itemView.test_name.text = ctData.testName
            itemView.marks.text = marksString
            var isSelected = 0
            itemView.ctMarksBar.setOnClickListener({
                if(isSelected ==0) {
                    itemView.ctMarksBar.setBackgroundColor(Color.parseColor("#f0f3f4"))
                    isSelected = 1
                }else{
                    itemView.ctMarksBar.setBackgroundColor(Color.parseColor("#ffffff"))
                    isSelected = 0
                }
            })
        }
    }


}