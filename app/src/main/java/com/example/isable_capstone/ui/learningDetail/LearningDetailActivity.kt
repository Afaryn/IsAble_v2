package com.example.isable_capstone.ui.learningDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.isable_capstone.databinding.ActivityLearningDetailBinding


class LearningDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityLearningDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearningDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()


    }

    private fun setupView(){
        val toolbar = binding.xmlToolbar.root

        setSupportActionBar(toolbar)
        supportActionBar?.title="Learning"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}