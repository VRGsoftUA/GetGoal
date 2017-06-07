package com.vrgsoft.mygoal.domain.habits

import com.vrgsoft.mygoal.data.db.alerts.Goal
import io.reactivex.Observable

interface HabitRepository {
    fun saveHabit(goal: Goal)
    fun getGoal(id: Int): Observable<Goal>

}