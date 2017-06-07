package com.vrgsoft.mygoal.presentation.injection

import com.vrgsoft.mygoal.presentation.main.habits.HabitsActivity
import com.vrgsoft.mygoal.presentation.main.habits.HabitsFragment
import dagger.Component
import javax.inject.Singleton

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface HabitsComponent {
    fun inject(habitsActivity: HabitsActivity)
    fun inject(fragment: HabitsFragment)
}