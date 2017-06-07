package com.vrgsoft.mygoal.data.db.alerts


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.concurrent.TimeUnit

open class Goal : RealmObject() {
    @PrimaryKey
    var mId: Long = 0
    var mTime: Long = 0
    var mDescr: String = ""
    var mName: String = ""
    var mImage: Int = 0
    var mLatsDays:Long = System.currentTimeMillis()
    var mTwentyOne:Long = TimeUnit.DAYS.toMillis(21)
    var lastCheck:Long = System.currentTimeMillis()


}

