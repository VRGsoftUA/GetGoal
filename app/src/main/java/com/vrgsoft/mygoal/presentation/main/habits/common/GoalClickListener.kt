package com.vrgsoft.mygoal.presentation.main.habits.common

import com.vrgsoft.mygoal.data.db.alerts.Goal


interface GoalClickListener {

    fun onGoalClick (mGoal: Goal)

}