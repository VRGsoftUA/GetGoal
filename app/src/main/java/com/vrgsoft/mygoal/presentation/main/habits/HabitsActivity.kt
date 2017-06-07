package com.vrgsoft.mygoal.presentation.main.habits

import android.content.Intent
import android.os.Bundle
import com.vrgsoft.mygoal.R
import com.vrgsoft.mygoal.databinding.ActivityHabitsBinding
import com.vrgsoft.mygoal.presentation.common.BaseActivity
import com.vrgsoft.mygoal.presentation.common.Layout
import com.vrgsoft.mygoal.presentation.injection.DaggerHabitsComponent
import com.vrgsoft.mygoal.presentation.injection.HabitsComponent
import com.vrgsoft.mygoal.presentation.main.addhabit.AddHabitActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.support.v4.intentFor


@Layout(id = R.layout.activity_habits)
class HabitsActivity : BaseActivity<ActivityHabitsBinding, HabitsActivity>(), HabitsRouter {
    val NEW_EXTRA: String = "new_extra"
    val ID_EXTRA: String = "id_extra"
    lateinit var habitsComponent: HabitsComponent

    override fun initComponent() {
        habitsComponent = DaggerHabitsComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(R.id.container, HabitsFragment.newInstance()).commit()


    }


    override fun onBackPressed() {
        finishAffinity()

    }

    override val activity: HabitsActivity
        get() = this

    override fun openGoal(isNew: Boolean, id: Long) {
        val intent: Intent = intentFor<AddHabitActivity>()
        intent.putExtra(ID_EXTRA, id)
        intent.putExtra(NEW_EXTRA, isNew)
        startActivity(intent)
    }

}
