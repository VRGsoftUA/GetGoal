package com.vrgsoft.mygoal.presentation.main.addhabit

import com.vrgsoft.mygoal.data.db.alerts.Goal
import com.vrgsoft.mygoal.domain.habits.HabitInteractor
import com.vrgsoft.mygoal.presentation.common.BasePresenter
import com.vrgsoft.mygoal.presentation.main.habits.HabitsRouter
import javax.inject.Inject

class AddHabitPresenter @Inject constructor(habitsInteractor: HabitInteractor) : BasePresenter<AddHabitView, HabitsRouter>() {
    val mHabitInteractor: HabitInteractor = habitsInteractor

    override fun onStart() {

    }

    override fun onStop() {
    }

    fun saveHabit(habitName: String, habitDescr: String, time:Long, theme:Int, id :Long) {
        val goal: Goal = Goal()
        goal.mId = id
        goal.mDescr = habitDescr
        goal.mName = habitName
        goal.mTime = time
        goal.mImage = theme
        mHabitInteractor.saveHabit(goal)
    }

    fun getGoal(mId: Long) {
        mHabitInteractor.getGoal(mId).subscribe({ goal: Goal -> view!!.setGoal(goal) }, Throwable::printStackTrace)
    }

    fun getGoalBuId(id:Long):Goal{
        return mHabitInteractor.getGoalById(id)
    }

    fun getAllHabits():List<Goal>{
        return mHabitInteractor.getAllHabits()
    }


}
