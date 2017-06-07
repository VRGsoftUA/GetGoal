package com.vrgsoft.mygoal.presentation.main.habits

import com.vrgsoft.mygoal.data.db.alerts.Goal
import com.vrgsoft.mygoal.presentation.common.BaseView
import com.vrgsoft.mygoal.presentation.main.habits.common.GoalClickListener

interface HabitsView : BaseView {

    fun setList(goals: List<Goal>,mListener:GoalClickListener)
}