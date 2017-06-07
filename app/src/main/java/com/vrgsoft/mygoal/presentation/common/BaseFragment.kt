package com.vrgsoft.mygoal.presentation.common

import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vrgsoft.mygoal.data.db.alerts.Goal


import com.vrgsoft.mygoal.presentation.events.BaseEvent

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class BaseFragment<B : ViewDataBinding> : Fragment(), BaseView {
    private var mIsInjected = false
    protected var binding: B? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            mIsInjected = onInjectView()
        } catch (e: IllegalStateException) {
            mIsInjected = false
        }

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mIsInjected) {
            onViewInjected(savedInstanceState)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val cls = javaClass
        if (!cls.isAnnotationPresent(Layout::class.java)) {
            return null
        }
        val annotation = cls.getAnnotation(Layout::class.java)
        binding = DataBindingUtil.inflate(inflater, annotation.id, container, false)

        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!mIsInjected) {
            mIsInjected = onInjectView()
            if (mIsInjected) {
                onViewInjected(savedInstanceState)
            }
        }
        initView()
    }

    abstract val toolbar: Toolbar?

    abstract fun toolbarNavigationActive(): Boolean

    protected abstract val presenter: BasePresenter<BaseView, *>

    protected abstract fun initView()

    @Throws(IllegalStateException::class)
    protected abstract fun inject()

    @Throws(IllegalStateException::class)
    protected fun onInjectView(): Boolean {
        inject()
        return true
    }

    protected fun onViewInjected(savedInstanceState: Bundle?) {
        presenter.view = this
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
        EventBus.getDefault().register(this)
    }


    override fun onStop() {
        super.onStop()
        presenter.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onEvent(event: BaseEvent) {

    }

    override fun onNetworkFailure() {
        //showAlertDialog(getString(R.string.network_failure));
    }

    override fun onRequestFailure() {
        //showAlertDialog(getString(R.string.request_error));
    }

    protected fun showAlertDialog(message: String) {
        AlertDialog.Builder(context)
                .setMessage(message)
                .setCancelable(false)
                .show()
    }
}
