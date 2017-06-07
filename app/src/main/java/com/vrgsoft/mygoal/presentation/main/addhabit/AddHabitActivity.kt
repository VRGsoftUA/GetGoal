package com.vrgsoft.mygoal.presentation.main.addhabit

import android.os.Bundle

import com.vrgsoft.mygoal.R
import com.vrgsoft.mygoal.databinding.ActivityAddHabitBinding
import com.vrgsoft.mygoal.presentation.common.BaseActivity
import com.vrgsoft.mygoal.presentation.common.Layout
import com.vrgsoft.mygoal.presentation.injection.AddHabitComponent
import com.vrgsoft.mygoal.presentation.injection.DaggerAddHabitComponent
import com.vrgsoft.mygoal.presentation.main.habits.common.themedialog.OnThemeChooseCallBack
import com.vrgsoft.mygoal.presentation.main.habits.common.themedialog.ThemeViewModel
import com.vrgsoft.mygoal.presentation.main.showhabit.ShowHabitFragment

@Layout(id = R.layout.activity_add_habit)
class AddHabitActivity : BaseActivity<ActivityAddHabitBinding, AddHabitActivity>(), OnThemeChooseCallBack {
    lateinit var addHabitComponent: AddHabitComponent
    lateinit var addHabitFragment: AddHabitFragment

    lateinit var showHabitFragment: ShowHabitFragment
    val NEW_EXTRA: String = "new_extra"
    val ID_EXTRA: String = "id_extra"
    override fun initComponent() {
        addHabitComponent = DaggerAddHabitComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
    }

    override val activity: AddHabitActivity
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.getBooleanExtra(NEW_EXTRA, true)) {
            addHabitFragment = AddHabitFragment.newInstance(intent.getLongExtra(ID_EXTRA, 0))
            supportFragmentManager.beginTransaction().replace(R.id.container, addHabitFragment).commit()
        } else {
            showHabitFragment = ShowHabitFragment.newInstance(intent.getLongExtra(ID_EXTRA, 0))
            supportFragmentManager.beginTransaction().replace(R.id.container, showHabitFragment).commit()
        }
    }

    override fun onThemeClick(themeViewModel: ThemeViewModel) {
        addHabitFragment.setThemeImage(themeViewModel)
    }

    fun showEditFragment() {
        addHabitFragment = AddHabitFragment.newInstance(intent.getLongExtra(ID_EXTRA, 0))
        supportFragmentManager.beginTransaction().replace(R.id.container, addHabitFragment).commit()
    }


}
