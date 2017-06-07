package com.vrgsoft.mygoal.domain.habits

import com.vrgsoft.mygoal.data.db.alerts.Goal
import io.reactivex.Observable

interface HabitsRepository {
    fun saveHabit(goal: Goal)
    fun getGoal(id: Long): Observable<Goal>
    fun getGoals(): Observable< List<Goal>>
}