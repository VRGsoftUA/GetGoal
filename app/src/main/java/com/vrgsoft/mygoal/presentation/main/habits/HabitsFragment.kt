package com.vrgsoft.mygoal.presentation.main.habits


import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.Toolbar
import android.view.View
import com.vrgsoft.mygoal.R
import com.vrgsoft.mygoal.data.db.alerts.Goal
import com.vrgsoft.mygoal.databinding.FragmentHabitsBinding
import com.vrgsoft.mygoal.presentation.common.BaseFragment
import com.vrgsoft.mygoal.presentation.common.BasePresenter
import com.vrgsoft.mygoal.presentation.common.BaseView
import com.vrgsoft.mygoal.presentation.common.Layout
import com.vrgsoft.mygoal.presentation.main.addhabit.AddHabitActivity
import com.vrgsoft.mygoal.presentation.main.habits.common.GoalClickListener
import com.vrgsoft.mygoal.presentation.main.habits.common.HabitsAdapter
import org.jetbrains.anko.onClick
import org.jetbrains.anko.support.v4.intentFor
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
@Layout(id = R.layout.fragment_habits)
open class HabitsFragment : BaseFragment<FragmentHabitsBinding>(), HabitsView, GoalClickListener {
    val NEW_EXTRA: String = "isNew"

    companion object {
        fun newInstance(): Fragment {
            val habitsFragment: HabitsFragment = HabitsFragment()
            return habitsFragment
        }
    }


    override fun setList(goals: List<Goal>, mListener: GoalClickListener) {

        val mAdapter: HabitsAdapter = HabitsAdapter(goals, mListener, activity)

        binding!!.habbitsContainer.adapter = mAdapter
        binding!!.habbitsContainer.layoutManager = LinearLayoutManager(context)

    }


    @Inject
    lateinit var habitsPresenter: HabitsPresenter

    override val presenter: BasePresenter<BaseView, *>
        get() = habitsPresenter as BasePresenter<BaseView, *>

    override val toolbar: Toolbar?
        get() = null

    override fun toolbarNavigationActive(): Boolean {
        return false
    }

    override fun initView() {

        binding!!.addHabbit.onClick {
            habitsPresenter.openGoal()
        }
        binding!!.menuButton.onClick {
            showPopupMenu(binding!!.menuButton)
        }
        habitsPresenter.router = activity as HabitsActivity


    }

    override fun onGoalClick(mGoal: Goal) {
        val intent: Intent = intentFor<AddHabitActivity>()
        intent.putExtra(NEW_EXTRA, false)
        activity.startActivity(intent)
    }

    override fun inject() {
        val habitsComponent = (activity as HabitsActivity).habitsComponent
        habitsComponent.inject(this)

    }


    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(context, v)
        popupMenu.inflate(R.menu.main_menu)
        popupMenu
                .setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        else -> false
                    }
                }
        popupMenu.show()
    }
}
