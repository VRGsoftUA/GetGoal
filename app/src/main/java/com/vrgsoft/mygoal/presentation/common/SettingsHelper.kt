package com.vrgsoft.mygoal.presentation.common

import android.content.Context


class SettingsHelper {


    var adsDisabled: CachedValue<Boolean>? = null
    var dateReminder: CachedValue<Long>? = null

    fun init(context: Context) {
        CachedValue.initialize(context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE))
        adsDisabled = CachedValue(ADS_DISABLED, false, Boolean::class.java)
        dateReminder = CachedValue(REMINDER, 0.toLong(), Long::class.java)
    }

    val isAdsDisabled: Boolean
        get() = adsDisabled!!.getValue()!!

    fun setDisabledAds(disabled: Boolean) {
        adsDisabled!!.setValue(disabled)
    }




    fun setReminder(now: Long) {
        dateReminder!!.setValue(now)
    }




    companion object {
        private val APP_PREFERENCES = "DailyBibleInspirations"
        private val ADS_DISABLED = "removeAds"
        private val REMINDER = "reminder"

    }
}