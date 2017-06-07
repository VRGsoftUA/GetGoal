package com.vrgsoft.mygoal.presentation.main.habits.common

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.vrgsoft.mygoal.R
import com.vrgsoft.mygoal.data.db.alerts.Goal
import com.vrgsoft.mygoal.databinding.ItemHabitBinding
import java.util.concurrent.TimeUnit

/**
 * Created by pavlonikitin on 3/21/17.
 */

class HabitsAdapter(private val mItems: List<Goal>,private val listener: GoalClickListener, val context : Context) : RecyclerView.Adapter<HabitsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.goal = mItems[position]
        Picasso.with(context).load(mItems[position].mImage).into(holder.binding.imageView)

        val leftDays:String = (((mItems[position].lastCheck-mItems[position].mLatsDays)/ TimeUnit.DAYS.toMillis(1))*100/21).toString()+" %"
        holder.binding.tvDayLeft.text = leftDays
        holder.binding.listener = listener
    }

    override fun getItemCount(): Int {
       return mItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_habit, parent, false))
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding: ItemHabitBinding = DataBindingUtil.bind(view)

    }
}