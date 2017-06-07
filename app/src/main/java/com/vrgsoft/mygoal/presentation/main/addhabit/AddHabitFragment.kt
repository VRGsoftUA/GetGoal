package com.vrgsoft.mygoal.presentation.main.addhabit


import android.annotation.SuppressLint
import android.app.DialogFragment
import android.os.Bundle

import android.support.v7.widget.Toolbar
import com.vrgsoft.mygoal.R
import com.vrgsoft.mygoal.data.db.alerts.Goal
import com.vrgsoft.mygoal.databinding.FragmentAddHabitBinding
import com.vrgsoft.mygoal.presentation.common.BaseFragment
import com.vrgsoft.mygoal.presentation.common.BasePresenter
import com.vrgsoft.mygoal.presentation.common.BaseView
import com.vrgsoft.mygoal.presentation.common.Layout
import org.jetbrains.anko.onClick
import javax.inject.Inject
import android.text.format.DateFormat
import android.view.ViewGroup
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog
import com.philliphsu.bottomsheetpickers.time.grid.GridTimePickerDialog
import com.squareup.picasso.Picasso
import com.vrgsoft.mygoal.presentation.main.habits.common.themedialog.ThemeDialogFragment
import com.vrgsoft.mygoal.presentation.main.habits.common.themedialog.ThemeViewModel
import com.vrgsoft.mygoal.presentation.receivers.AlarmReceiver
import org.jetbrains.anko.imageResource
import java.text.SimpleDateFormat

import java.util.*
import java.util.concurrent.TimeUnit


@Layout(id = R.layout.fragment_add_habit)
class AddHabitFragment : BaseFragment<FragmentAddHabitBinding>(), AddHabitView, BottomSheetTimePickerDialog.OnTimeSetListener {


    lateinit var themeDialog: DialogFragment
    var chosenTime: Long = Calendar.getInstance().timeInMillis
    var chosenTheme: Int = R.drawable.other_icon
    val EMOJI_TAG: String = "EmojiDialog"
    val DIALOG_TAG: String = "Dialog"
    override fun onTimeSet(viewGroup: ViewGroup?, hourOfDay: Int, minute: Int) {
        val cal: GregorianCalendar = GregorianCalendar()
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
        cal.set(Calendar.MINUTE, minute)
        binding!!.notifyTime.text = DateFormat.getTimeFormat(context).format(cal.time)
        chosenTime = if (cal.timeInMillis <= System.currentTimeMillis()) cal.timeInMillis + TimeUnit.DAYS.toMillis(1) else cal.timeInMillis

    }

    companion object {
        val ID_EXTRA: String = "id_extra"
        fun newInstance(mId: Long): AddHabitFragment {
            val mBundl: Bundle = Bundle()
            mBundl.putLong(ID_EXTRA, mId)
            val fragment: AddHabitFragment = AddHabitFragment()
            fragment.arguments = mBundl
            return fragment
        }
    }

    @Inject
    lateinit var mHabitPresenter: AddHabitPresenter
    override val toolbar: Toolbar?
        get() = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (this.arguments.getLong(ID_EXTRA, 0) == 0.toLong()) {
            initView()
        } else {
            mHabitPresenter.getGoal(this.arguments.getLong(ID_EXTRA, 0))
        }
    }

    override fun toolbarNavigationActive(): Boolean {
        return false
    }

    override val presenter: BasePresenter<BaseView, *>
        get() = mHabitPresenter as BasePresenter<BaseView, *>

    override fun initView() {

        val currentTime: String = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
        binding!!.notifyTime.text = currentTime
        if (binding!!.checkBox.isChecked) {
            chosenTime = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)
        } else {
            chosenTime = 0
        }
        binding!!.saveHabit.onClick {

            mHabitPresenter.saveHabit(binding!!.habitName.editText!!.text.toString(),
                    binding!!.habitDescription.editText!!.text.toString(), chosenTime, chosenTheme, this.arguments.getLong(ID_EXTRA, 0))

            if (binding!!.checkBox.isChecked) {
                AlarmReceiver.updateDailyReminder(context)
            }

            activity.finish()
        }


        binding!!.notifyTime.onClick {
            GridTimePickerDialog.newInstance(this, Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                    Calendar.getInstance().get(Calendar.MINUTE), DateFormat.is24HourFormat(context)).show(activity.supportFragmentManager, DIALOG_TAG)

        }


        binding!!.emoji.onClick {

            themeDialog = ThemeDialogFragment().newInstance()
            themeDialog.show(activity.fragmentManager, EMOJI_TAG)
        }


    }

    override fun setGoal(goal: Goal) {

        binding!!.goal = goal
        Picasso.with(context).load(goal.mImage).into(binding!!.emoji)
        if (goal.mTime != 0.toLong()) {
            val currentTime: String = SimpleDateFormat("HH:mm").format(goal.mTime)
            binding!!.notifyTime.text = currentTime
        }

    }

    override fun inject() {
        val habitsComponent = (activity as AddHabitActivity).addHabitComponent
        habitsComponent.inject(this)
    }

    fun setThemeImage(themeViewModel: ThemeViewModel) {
        binding!!.emoji.imageResource = themeViewModel.ivTheme
        chosenTheme = themeViewModel.ivTheme
        themeDialog.dismiss()
    }
}
