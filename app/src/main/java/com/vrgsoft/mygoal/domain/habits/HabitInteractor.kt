
package com.vrgsoft.mygoal.domain.habits

import com.vrgsoft.mygoal.data.db.alerts.Goal
import com.vrgsoft.mygoal.data.db.habits.HabitsRepositoryLocalImp
import io.reactivex.Observable
import javax.inject.Inject

class HabitInteractor @Inject constructor(habitsRepositoryLocalImp: HabitsRepositoryLocalImp) {
    private val mHabitsRepository: HabitsRepositoryLocalImp = habitsRepositoryLocalImp

     fun saveHabit(habit: Goal) {
        mHabitsRepository.saveHabit(habit)
    }

    fun getGoal(mId:Long): Observable<Goal> {
        return mHabitsRepository.getGoal(mId)
    }

    fun getGoalById(id:Long):Goal{
        return mHabitsRepository.getGoalById(id)
    }

    fun getAllHabits():List<Goal>{
        return mHabitsRepository.getAllHabits()
    }

    fun check(goal: Goal){
        mHabitsRepository.check(goal)
    }

    fun deleteGoalById(goal: Goal){
        mHabitsRepository.deleteGoalById(goal)
    }
}