package com.vrgsoft.mygoal.presentation.injection

import com.vrgsoft.mygoal.presentation.main.addhabit.AddHabitActivity
import com.vrgsoft.mygoal.presentation.main.addhabit.AddHabitFragment
import com.vrgsoft.mygoal.presentation.main.habits.HabitsActivity
import com.vrgsoft.mygoal.presentation.main.showhabit.ShowHabitFragment
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface AddHabitComponent {
    fun inject(habitsActivity: AddHabitActivity)
    fun inject(habitsActivity: AddHabitFragment)
    fun inject(habitsActivity: ShowHabitFragment)
}