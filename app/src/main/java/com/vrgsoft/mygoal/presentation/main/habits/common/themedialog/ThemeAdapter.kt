package com.vrgsoft.mygoal.presentation.main.habits.common.themedialog

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.vrgsoft.mygoal.R

import com.squareup.picasso.Picasso
import org.jetbrains.anko.onClick



class ThemeAdapter constructor(private val themeViewModel: List<ThemeViewModel>, val context: Context) : RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder>() {

    override fun onBindViewHolder(holder: ThemeViewHolder?, position: Int) {
        Picasso.with(context).load(themeViewModel[position].ivTheme).into(holder!!.ivImageTheme)
        holder.tvTextTheme.text = themeViewModel[position].tvTheme

        holder.itemView.onClick {
            context as OnThemeChooseCallBack
            context.onThemeClick(themeViewModel[position])
        }

    }

    override fun getItemCount(): Int {
        return themeViewModel.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ThemeViewHolder {
        val layoutInflater: View = LayoutInflater.from(parent!!.context).inflate(R.layout.item, parent, false)
        return ThemeViewHolder(layoutInflater)
    }

    class ThemeViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var ivImageTheme: ImageView = v.findViewById(R.id.theme_icon) as ImageView
        var tvTextTheme: TextView = v.findViewById(R.id.theme_name) as TextView


    }
}