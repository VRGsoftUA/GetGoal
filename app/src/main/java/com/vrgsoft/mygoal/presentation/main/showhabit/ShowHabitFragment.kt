package com.vrgsoft.mygoal.presentation.main.showhabit


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.Toolbar
import com.squareup.picasso.Picasso
import com.vrgsoft.mygoal.R
import com.vrgsoft.mygoal.data.db.alerts.Goal
import com.vrgsoft.mygoal.databinding.FragmentShowHabitBinding
import com.vrgsoft.mygoal.presentation.common.BaseFragment
import com.vrgsoft.mygoal.presentation.common.BasePresenter
import com.vrgsoft.mygoal.presentation.common.BaseView
import com.vrgsoft.mygoal.presentation.common.Layout
import com.vrgsoft.mygoal.presentation.main.addhabit.AddHabitActivity
import com.vrgsoft.mygoal.presentation.main.habits.common.resultdialogs.ResultDialog
import com.vrgsoft.mygoal.presentation.receivers.AlarmReceiver
import org.jetbrains.anko.onClick
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import az.plainpie.animation.PieAngleAnimation
import com.vrgsoft.mygoal.presentation.main.addhabit.AddHabitFragment.Companion.ID_EXTRA


@Layout(id = R.layout.fragment_show_habit)
class ShowHabitFragment : BaseFragment<FragmentShowHabitBinding>(), ShowHabbitView {
    val TAG_FAILED = "Failed"
    override fun setGoal(goal: Goal) {
        binding!!.goal = goal
        if (goal.mTime != 0.toLong()) {
            val currentTime: String = SimpleDateFormat("HH:mm").format(goal.mTime)
            binding!!.notifyTimeToShow.text = currentTime
        } else {
            binding!!.notifyTimeToShow.text = getString(R.string.none)
        }

        Picasso.with(context).load(goal.mImage).into(binding!!.emojiShow)


        var percentage: Float = (((goal.lastCheck - goal.mLatsDays) / TimeUnit.DAYS.toMillis(1)) * 100 / 21).toFloat()
        if (percentage == 0.toFloat()) {
            percentage = 0.1.toFloat()
        }
        binding!!.pieView.percentage = percentage

        val animation = PieAngleAnimation(binding!!.pieView)
        animation.duration = 1500
        binding!!.pieView.startAnimation(animation)

        val calLastCheck: Calendar = Calendar.getInstance()
        calLastCheck.timeInMillis = goal.lastCheck
        calLastCheck.set(Calendar.HOUR_OF_DAY, 0)
        calLastCheck.set(Calendar.MINUTE, 1)

        if ((System.currentTimeMillis() - calLastCheck.timeInMillis) > TimeUnit.DAYS.toMillis(2)) {

            val failedDialog: DialogFragment = ResultDialog.newInstance(true)
            failedDialog.show(fragmentManager, TAG_FAILED)
            mHabitPresenter.deleteGoalById(goal)
        } else {
            if ((calLastCheck.timeInMillis + TimeUnit.DAYS.toMillis(1)) <= System.currentTimeMillis()) {
                binding!!.checkHabbit.isEnabled = true
                binding!!.checkHabbit.onClick {
                    mHabitPresenter.check(goal)
                    AlarmReceiver.updateDailyReminder(context)
                    if ((goal.mLatsDays + goal.mTwentyOne) <= System.currentTimeMillis()) {
                        val failedDialog: DialogFragment = ResultDialog.newInstance(false)
                        failedDialog.show(fragmentManager, TAG_FAILED)
                        mHabitPresenter.deleteGoalById(goal)
                    }
                    binding!!.checkHabbit.isEnabled = false
                    activity.finish()
                }
            } else {
                binding!!.checkHabbit.isEnabled = false
            }
        }

    }

    override val toolbar: Toolbar?
        get() = null

    companion object {
        fun newInstance(mId: Long): ShowHabitFragment {
            val mBundl: Bundle = Bundle()
            mBundl.putLong(ID_EXTRA, mId)
            val fragment: ShowHabitFragment = ShowHabitFragment()
            fragment.arguments = mBundl
            return fragment
        }
    }

    @Inject
    lateinit var mHabitPresenter: ShowHabitPresenter

    override fun toolbarNavigationActive(): Boolean {
        return false
    }

    override fun initView() {
        binding!!.ivEdit.onClick {
            val tmp: AddHabitActivity = activity as AddHabitActivity
            tmp.showEditFragment()
        }
    }

    override fun inject() {
        val habitsComponent = (activity as AddHabitActivity).addHabitComponent
        habitsComponent.inject(this)
    }

    override val presenter: BasePresenter<BaseView, *>
        get() = mHabitPresenter as BasePresenter<BaseView, *>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mHabitPresenter.getGoal(this.arguments.getLong(ID_EXTRA, 0))

        initView()
    }


}