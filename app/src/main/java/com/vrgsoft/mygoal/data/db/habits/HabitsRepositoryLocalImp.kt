package com.vrgsoft.mygoal.data.db.habits

import com.vrgsoft.mygoal.data.db.alerts.Goal
import com.vrgsoft.mygoal.domain.habits.HabitsRepository
import com.vrgsoft.mygoal.presentation.receivers.AlarmReceiver
import io.reactivex.Observable
import io.realm.Realm
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class HabitsRepositoryLocalImp() : HabitsRepository {
    lateinit private var mRealm: Realm

    @Inject
    constructor(mRealm: Realm) : this() {
        this.mRealm = mRealm
    }

    override fun saveHabit(goal: Goal) {
        mRealm.executeTransaction {

            if(goal.mId == 0.toLong()){
                val currentIdNum = mRealm.where(Goal::class.java).max("mId")
                val nextId: Long
                if (currentIdNum == null) {
                    nextId = 1
                } else {
                    nextId = currentIdNum.toLong() + 1
                }
                goal.mId = nextId
            }

            mRealm.copyToRealmOrUpdate(goal)
        }
    }


    override fun getGoal(id: Long): Observable<Goal> {
        var goal:Goal = Realm.getDefaultInstance().where(Goal::class.java).equalTo("mId", id).findFirst();
        if(goal == null){
            return Observable.empty();
        }else{
            return Observable.just(Realm.getDefaultInstance().copyFromRealm(goal))
        }

    }

    override fun getGoals(): Observable< List<Goal>> {
        return Observable.just(mRealm.copyFromRealm(mRealm.where(Goal::class.java).findAll()))
    }

    fun getGoalById(id : Long): Goal {
        return mRealm.where(Goal::class.java).equalTo("mId", id).findFirst()
    }

    fun getAllHabits():List<Goal>{
        var castRealm : Realm = Realm.getDefaultInstance()
        return castRealm.where(Goal::class.java).findAll()
    }

    fun check(goal: Goal){
        mRealm.beginTransaction()
        goal.lastCheck=System.currentTimeMillis()
        goal.mTime = goal.mTime+TimeUnit.DAYS.toMillis(1)
        mRealm.copyToRealmOrUpdate(goal)
        mRealm.commitTransaction()


    }


    fun deleteGoalById(goal: Goal){
        mRealm.beginTransaction()
        var goalToDel:Goal = mRealm.where(Goal::class.java).equalTo("mId", goal.mId).findFirst()
        goalToDel.deleteFromRealm()
        mRealm.commitTransaction()
    }
}