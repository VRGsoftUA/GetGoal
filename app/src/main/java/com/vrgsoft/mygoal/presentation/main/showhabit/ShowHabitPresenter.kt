package com.vrgsoft.mygoal.presentation.main.showhabit

import com.vrgsoft.mygoal.data.db.alerts.Goal
import com.vrgsoft.mygoal.domain.habits.HabitInteractor
import com.vrgsoft.mygoal.presentation.common.BasePresenter
import com.vrgsoft.mygoal.presentation.main.habits.HabitsRouter
import javax.inject.Inject

/**
 * Created by Павел on 04.04.2017.
 */
class ShowHabitPresenter @Inject constructor(habitsInteractor: HabitInteractor) : BasePresenter<ShowHabbitView, HabitsRouter>(){

    val mHabitInteractor: HabitInteractor = habitsInteractor


    override fun onStart() {

    }

    override fun onStop() {

    }

    fun showHabit() {

    }

    fun getGoal(mId: Long) {
        mHabitInteractor.getGoal(mId).subscribe({ goal: Goal -> view!!.setGoal(goal) }, Throwable::printStackTrace)

    }

    fun check(goal: Goal){
        mHabitInteractor.check(goal)
    }

    fun deleteGoalById(goal: Goal){
        mHabitInteractor.deleteGoalById(goal)
    }

    fun getGoalById(id:Long){
        mHabitInteractor.getGoalById(id)
    }






}