package com.vrgsoft.mygoal.presentation.main.habits.common.resultdialogs

import android.content.Intent
import android.databinding.generated.callback.OnClickListener
import android.graphics.Color
import android.os.Bundle
import android.graphics.drawable.ColorDrawable
import android.support.v4.app.DialogFragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.vrgsoft.mygoal.R
import com.vrgsoft.mygoal.presentation.main.addhabit.AddHabitActivity
import com.vrgsoft.mygoal.presentation.main.habits.common.themedialog.ThemeDialogFragment

class ResultDialog : DialogFragment(), View.OnClickListener {
    val NEW_EXTRA: String = "new_extra"
    companion object {
        val FAILED_EXTRA = "failed_extra"
        fun newInstance(idFailed: Boolean): DialogFragment {
            val resultDialog: ResultDialog = ResultDialog()
            val bundle: Bundle = Bundle()
            bundle.putBoolean(FAILED_EXTRA, idFailed)
            resultDialog.arguments = bundle
            return resultDialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = if (arguments.getBoolean(FAILED_EXTRA)) R.layout.dialog_failed else R.layout.dialog_success
        val v = inflater!!.inflate(layout, null)
        v.findViewById(R.id.ok).setOnClickListener(this)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return v
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, 0)
    }

    override fun onClick(view: View) {
        val intentLaunchApp = Intent(activity, AddHabitActivity::class.java)
        intentLaunchApp.putExtra(NEW_EXTRA, true)
        activity.startActivity(intentLaunchApp)
        dismiss()
        activity.finish()
    }
}