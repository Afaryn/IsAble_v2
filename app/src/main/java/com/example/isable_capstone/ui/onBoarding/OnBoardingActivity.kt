package com.example.isable_capstone.ui.onBoarding

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.isable_capstone.MainActivity
import com.example.isable_capstone.R
import com.example.isable_capstone.ui.authentication.sign_in.sign_in_activity
import com.example.isable_capstone.ui.authentication.sign_up.sign_up_activity
import com.google.firebase.auth.FirebaseAuth
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator



@Suppress("DEPRECATION")
class OnBoardingActivity :AppCompatActivity() {

    private lateinit var viewPager : ViewPager2
    private lateinit var pagerAdapter: OnBoardingPagerAdapter
    private lateinit var wormDotsIndicator: WormDotsIndicator
    private lateinit var firebaseAuth: FirebaseAuth
    private val handler = Handler()
    private val delay : Long = 3000

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding_page)

        firebaseAuth = FirebaseAuth.getInstance()

        viewPager = findViewById(R.id.viewPager2)
        pagerAdapter = OnBoardingPagerAdapter(supportFragmentManager,lifecycle)
        viewPager.adapter = pagerAdapter

        wormDotsIndicator = findViewById(R.id.worm_dots_indicator)
        wormDotsIndicator.attachTo(viewPager)

        val btnSignIn : Button = findViewById(R.id.btn_signin)
        val btnSignUp : Button = findViewById(R.id.btn_signup)

        btnSignIn.setOnClickListener{
            val intent = Intent (this,sign_in_activity::class.java)
            startActivity(intent)
        }

        btnSignUp.setOnClickListener{
            val intent = Intent (this, sign_up_activity::class.java)
            startActivity(intent)
        }

        setupView()
    }

    override fun onResume() {
        super.onResume()
        startAutoScroll()
    }

    override fun onPause() {
        super.onPause()
        stopAutoScroll()
    }

    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            val currentItem = viewPager.currentItem
            val nextItem = if (currentItem < pagerAdapter.itemCount - 1) currentItem + 1 else 0
            viewPager.currentItem = nextItem
            handler.postDelayed(this, delay)
        }
    }

    private fun startAutoScroll() {
        handler.postDelayed(autoScrollRunnable, delay)
    }

    private fun stopAutoScroll() {
        handler.removeCallbacks(autoScrollRunnable)
    }

    private fun setupView() {
        window.insetsController?.hide(WindowInsets.Type.statusBars())
        supportActionBar?.hide()
    }

    override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}