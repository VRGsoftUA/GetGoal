package com.vrgsoft.mygoal.presentation.receivers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*

/**
 * Created by pavlonikitin on 4/4/17.
 */

class TimeChangeReceiver : BroadcastReceiver() {
    val DAY_CHANGE_ALARM_ID:Int = 100220


    override fun onReceive(context: Context?, intent: Intent?) {
        AlarmReceiver.updateRandomDailyReminder(context!!)

    }

    fun  scheduleDayChangeAlarm(context: Context?){
        val calendar:Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.add(Calendar.DAY_OF_YEAR, 1)

        val am:AlarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TimeChangeReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, DAY_CHANGE_ALARM_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        // Don't need this to fire exactly on the dot.
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

    }
}