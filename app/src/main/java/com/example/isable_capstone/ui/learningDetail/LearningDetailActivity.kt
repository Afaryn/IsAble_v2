package com.example.isable_capstone.ui.learningDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.isable_capstone.databinding.ActivityLearningDetailBinding

class LearningDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLearningDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearningDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val link = intent.getStringExtra("foto")

        setupView()

        Glide.with(this)
            .load(link)
            .into(binding.btnAlfabetLearn)
    }

    private fun setupView(){
        val toolbar = binding.xmlToolbar.root

        setSupportActionBar(toolbar)
        supportActionBar?.title="Learning"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}