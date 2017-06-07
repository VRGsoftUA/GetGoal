package com.vrgsoft.mygoal.presentation.receivers

import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.vrgsoft.mygoal.R
import com.vrgsoft.mygoal.data.db.alerts.Goal
import com.vrgsoft.mygoal.data.db.habits.HabitsRepositoryLocalImp
import com.vrgsoft.mygoal.domain.habits.HabitInteractor
import com.vrgsoft.mygoal.domain.habits.HabitsInteractor
import com.vrgsoft.mygoal.presentation.common.SettingsHelper
import com.vrgsoft.mygoal.presentation.injection.App
import com.vrgsoft.mygoal.presentation.main.addhabit.AddHabitActivity
import com.vrgsoft.mygoal.presentation.main.addhabit.AddHabitPresenter
import com.vrgsoft.mygoal.presentation.main.habits.HabitsActivity
import com.vrgsoft.mygoal.presentation.main.habits.HabitsPresenter
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent?) {
        var context = context
        if (intent == null || intent.action == null) {
            return
        }

        // When device boots, need to reschedule alarm
        context = context.applicationContext
        when (intent.action) {
            Intent.ACTION_DATE_CHANGED -> updateReminder(context)
            UPDATE_REMINDER -> updateReminder(context)
            UPDATE_RANDOM_REMINDER -> updateRandomReminder(context)
            ACTION_SHOW_DAILY_REMINDER ->
                showDailyReminderNotification(context, intent)

        }
    }

    private fun updateReminder(context: Context) {
        scheduleDailyReminderAlarm(context)
    }

    private fun updateRandomReminder(context: Context) {
        scheduleRandomReminderAlarms(context)
    }


    private fun scheduleDailyReminderAlarm(context: Context) {

        val habitsRepository: HabitsRepositoryLocalImp = HabitsRepositoryLocalImp()
        val habitInteractor: HabitInteractor = HabitInteractor(habitsRepository)
        val mHabitPresenter: AddHabitPresenter = AddHabitPresenter(habitInteractor)
        val listAlarm: List<Goal> = mHabitPresenter.getAllHabits()
        listAlarm
                .filter { it.mTime != 0.toLong() }
                .forEach {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        makeMarshMallowNotif(it, context)
                    } else {
                        scheduleAlarm(context, createDailyReminderIntent(context, it), it.mTime)
                    }
                }
    }

    @RequiresApi(api = 23)
    fun makeMarshMallowNotif(goal: Goal, context: Context) {
        for (i in 1..22) {
            val pendingIntent = createDailyReminderIntentMarshmalow(context, goal, i)
            val timeToCall: Long = goal.mTime + TimeUnit.DAYS.toMillis(i.toLong())
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeToCall, pendingIntent);
        }

    }

    private fun scheduleRandomReminderAlarms(context: Context) {
        val hour = 8
        val minute = 30
        scheduleAlarmTwoDays(context, createRandomReminderIntent(context), hour, minute)

    }

    private fun scheduleAlarm(context: Context, intent: PendingIntent, reminderTime: Long) {
        //var nextTime : Long = reminderTime+TimeUnit.DAYS.toMillis(1);
        // Will override existing alarm if necessary.
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, reminderTime, AlarmManager.INTERVAL_DAY, intent)

    }

    private fun scheduleAlarmTwoDays(context: Context, intent: PendingIntent, hourOfDay: Int, minute: Int) {
        // get a calendar instance, which defaults to "now"
        val calendar = Calendar.getInstance()
        // get a date to represent "today"
        val today = calendar.time
        println("today:    " + today)
        // add one day to the date/calendar
        calendar.add(Calendar.DAY_OF_YEAR, 2)
        // now get "tomorrow"
        val tomorrow = calendar.time

        val reminderTime = Calendar.getInstance() // Start with right now.
        reminderTime.set(Calendar.DATE, tomorrow.date)
        reminderTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
        reminderTime.set(Calendar.MINUTE, minute)
        reminderTime.set(Calendar.SECOND, 0)
        // Start from tomorrow if today's alarm time has already passed.
        if (reminderTime.before(Calendar.getInstance())) {
            reminderTime.add(Calendar.DAY_OF_MONTH, 1)
        }
        // Will override existing alarm if necessary.
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, reminderTime.timeInMillis, AlarmManager.INTERVAL_DAY, intent)
        App.settings.setReminder(System.currentTimeMillis() + 1000 * 60 * 60 * 24)
    }

    private fun createDailyReminderIntentMarshmalow(context: Context, goal: Goal, counter: Int): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = ACTION_SHOW_DAILY_REMINDER
        intent.putExtra("theme", goal.mName)
        intent.putExtra("question", goal.mDescr)
        intent.putExtra("id", goal.mId)
        intent.putExtra("time", goal.mTime)
        intent.putExtra("startTime", goal.mLatsDays)

        return PendingIntent.getBroadcast(context, goal.mId.toInt() * 1000 + counter, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT)
    }

    // Use same pending intent for all queries to AlarmManager to ensure only one is being updated/cancelled.
    private fun createDailyReminderIntent(context: Context, goal: Goal): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = ACTION_SHOW_DAILY_REMINDER
        intent.putExtra("theme", goal.mName)
        intent.putExtra("question", goal.mDescr)
        intent.putExtra("id", goal.mId)
        intent.putExtra("time", goal.mTime)
        intent.putExtra("startTime", goal.mLatsDays)

        return PendingIntent.getBroadcast(context, goal.mId.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT)
    }

    private fun createRandomReminderIntent(context: Context): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = ACTION_SHOW_DAILY_RANDOM_REMINDER
        return PendingIntent.getBroadcast(context, RANDOM_ALARM_BASE_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT)
    }

    companion object {

        val UPDATE_REMINDER = ".UPDATE_REMINDER"
        val UPDATE_RANDOM_REMINDER = ".UPDATE_RANDOM_REMINDER"
        private val ACTION_SHOW_DAILY_REMINDER = ".ACTION_SHOW_DAILY_REMINDER"
        private val ACTION_SHOW_DAILY_RANDOM_REMINDER = ".ACTION_SHOW_RANDOM_DAILY_REMINDER"

        private val ALARM_ID = 100218
        private val RANDOM_ALARM_BASE_ID = 210223

        fun updateDailyReminder(context: Context) {
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.action = UPDATE_REMINDER
            context.sendBroadcast(intent)
        }

        fun updateRandomDailyReminder(context: Context) {
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.action = UPDATE_RANDOM_REMINDER
            context.sendBroadcast(intent)
        }

        fun showDailyReminderNotification(context: Context, intent: Intent) {
            // Show daily reminder notification to user.
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val intentLaunchApp = Intent(context, AddHabitActivity::class.java)
            intentLaunchApp.putExtra("isNew", false)
            intentLaunchApp.putExtra("id", intent.getLongExtra("id", 0))
            val pendingIntent = PendingIntent.getActivity(context, ALARM_ID, intentLaunchApp, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT)
            val notification = createNotification(context, intent.getStringExtra("question"), intent.getStringExtra("theme"), pendingIntent)
            //         Sets an ID for the notification, so it can be updated
            notificationManager.notify(ALARM_ID, notification)

        }


        private fun createNotification(context: Context, text: String, textTheme: String, pendingIntent: PendingIntent): Notification {
            val builder = NotificationCompat.Builder(context)
            builder.setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.icon))
            builder.setContentTitle(textTheme)
            builder.setSmallIcon(R.drawable.icon)
            builder.setDefaults(Notification.DEFAULT_SOUND)
            builder.setWhen(System.currentTimeMillis())
            builder.setContentIntent(pendingIntent)
            builder.setContentText(text)
            builder.setAutoCancel(true)
            return builder.build()
        }
    }


}

