package com.vrgsoft.mygoal.presentation.main.habits

import com.vrgsoft.mygoal.data.db.alerts.Goal
import com.vrgsoft.mygoal.domain.habits.HabitsInteractor
import com.vrgsoft.mygoal.presentation.common.BasePresenter
import com.vrgsoft.mygoal.presentation.main.addhabit.AddHabitActivity
import com.vrgsoft.mygoal.presentation.main.habits.common.GoalClickListener
import org.jetbrains.anko.support.v4.intentFor
import javax.inject.Inject

class HabitsPresenter @Inject constructor(habitsInteractor: HabitsInteractor) : BasePresenter<HabitsFragment, HabitsRouter>(), GoalClickListener {


    override fun onGoalClick(mGoal: Goal) {
        router!!.openGoal(false, mGoal.mId);
    }

    val mHabitInteractor: HabitsInteractor = habitsInteractor

    override fun onStart() {

        mHabitInteractor.getGoals().subscribe({ goal: List<Goal> -> view?.setList(goal, this) },
                { t: Throwable? -> t!!.printStackTrace() })
    }

    override fun onStop() {
    }

    fun openGoal() {
        router!!.openGoal(true, 0)
    }

}