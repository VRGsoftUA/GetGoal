package com.vrgsoft.mygoal.presentation.common

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import com.vrgsoft.mygoal.presentation.injection.App
import com.vrgsoft.mygoal.presentation.injection.ApplicationComponent

abstract class BaseActivity<B : ViewDataBinding, A : Activity> : AppCompatActivity(), BaseView {
    protected var dataBinding: B? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cls = javaClass
        if (!cls.isAnnotationPresent(Layout::class.java)) {
            return
        }
        val annotation = cls.getAnnotation(Layout::class.java)
        dataBinding = DataBindingUtil.setContentView(activity, annotation.id)
        initComponent()
    }

    fun resolveToolbar(fragment: BaseFragment<*>) {
        val toolbar = fragment.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

    }

    fun addFragment(viewGroupId: Int, fragment: BaseFragment<*>) {
        supportFragmentManager
                .beginTransaction()
                .replace(viewGroupId, fragment)
                .commit()
    }

    abstract fun initComponent()

    abstract val activity: A
    override fun onNetworkFailure() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestFailure() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    protected val applicationComponent: ApplicationComponent
        get() = App.applicationComponent
}
