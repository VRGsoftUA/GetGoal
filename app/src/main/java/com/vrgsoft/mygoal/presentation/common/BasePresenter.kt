package com.vrgsoft.mygoal.presentation.common

abstract class BasePresenter<View, Router> {
    var view: View? = null
    var router: Router? = null

    abstract fun onStart()

    abstract fun onStop()
}