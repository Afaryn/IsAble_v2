package com.example.isable_capstone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.isable_capstone.R
import com.example.isable_capstone.ui.onBoarding.OnBoardingActivity

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        actionBar?.hide()
        val iv_note = findViewById<ImageView>(R.id.iv_note)
        iv_note.alpha=0f
        iv_note.animate().setDuration(2500).alpha(1f).withEndAction{
            val i = Intent(this@SplashScreen, OnBoardingActivity::class.java)
            startActivity(i)
            this.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
}