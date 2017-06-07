package com.vrgsoft.mygoal.presentation.injection

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.vrgsoft.mygoal.data.db.habits.HabitsRepositoryLocalImp
import com.vrgsoft.mygoal.domain.habits.HabitsRepository

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return mApplication.applicationContext
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideHabitRepositoryLocal(): HabitsRepository {
        return HabitsRepositoryLocalImp()
    }

}
