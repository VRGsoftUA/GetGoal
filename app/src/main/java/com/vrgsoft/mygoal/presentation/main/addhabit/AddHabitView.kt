package com.vrgsoft.mygoal.presentation.main.addhabit

import com.vrgsoft.mygoal.data.db.alerts.Goal
import com.vrgsoft.mygoal.presentation.common.BaseView

interface AddHabitView : BaseView {
    fun setGoal(goal: Goal)
}