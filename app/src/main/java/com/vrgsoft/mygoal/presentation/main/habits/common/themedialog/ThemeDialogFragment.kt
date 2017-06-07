package com.vrgsoft.mygoal.presentation.main.habits.common.themedialog

import android.app.DialogFragment
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.model.stream.StreamStringLoader
import com.vrgsoft.mygoal.R
import com.vrgsoft.mygoal.databinding.FragmentDialogThemeBinding
import com.vrgsoft.mygoal.databinding.ItemBinding
import org.jetbrains.anko.imageResource


class ThemeDialogFragment : DialogFragment() {


    lateinit var binding:FragmentDialogThemeBinding

    fun newInstance(): DialogFragment {
        val themeDialogFragment: ThemeDialogFragment = ThemeDialogFragment()
        return themeDialogFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, 0)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate<FragmentDialogThemeBinding>(inflater, R.layout.fragment_dialog_theme, container, false)
        binding.recyclerTheme.layoutManager = LinearLayoutManager(activity)
        binding.recyclerTheme.adapter=ThemeAdapter(createListViewModels(), activity)
        return binding.root

    }


    fun createListViewModels():ArrayList<ThemeViewModel>{
        val themeListModel:ArrayList<ThemeViewModel> = ArrayList()

        val themeIcon:IntArray = intArrayOf(R.drawable.healthy_icon, R.drawable.money_icon,
                R.drawable.self_improvment_icon, R.drawable.sex_dating_icon, R.drawable.work_and_study_icon,
                R.drawable.arts_icon, R.drawable.social_icon, R.drawable.home_icon, R.drawable.other_icon)
        val themeNames:Array<String> = activity.resources.getStringArray(R.array.themesNames)

        (0..8).mapTo(themeListModel) { ThemeViewModel(themeIcon[it], themeNames[it]) }

        return themeListModel
    }


}