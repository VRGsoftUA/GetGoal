package com.vrgsoft.mygoal.presentation.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

import com.vrgsoft.mygoal.R
import com.vrgsoft.mygoal.presentation.main.habits.HabitsActivity

class SplashActivity : AppCompatActivity() {

    lateinit var ivSplashCircle:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        setContentView(R.layout.activity_splash)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        ivSplashCircle = findViewById(R.id.iv_cirlce) as ImageView

        val animation = AnimationUtils.loadAnimation(this, R.anim.move_anim)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                startActivity(Intent(this@SplashActivity, HabitsActivity::class.java))
                finish()
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        ivSplashCircle.startAnimation(animation)

    }
}
