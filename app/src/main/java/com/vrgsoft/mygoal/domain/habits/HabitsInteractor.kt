package com.vrgsoft.mygoal.domain.habits

import com.vrgsoft.mygoal.data.db.alerts.Goal
import com.vrgsoft.mygoal.data.db.habits.HabitsRepositoryLocalImp
import io.reactivex.Observable
import javax.inject.Inject

class HabitsInteractor @Inject constructor(habitsRepositoryLocalImp: HabitsRepositoryLocalImp) {
    private val mHabitsRepository: HabitsRepositoryLocalImp = habitsRepositoryLocalImp

    fun getGoals(): Observable<List<Goal>> {
        return mHabitsRepository.getGoals()
    }

}