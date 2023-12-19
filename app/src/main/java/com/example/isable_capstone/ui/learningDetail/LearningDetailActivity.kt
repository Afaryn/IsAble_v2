package com.example.isable_capstone.ui.learningDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.isable_capstone.R
import com.example.isable_capstone.databinding.ActivityLearningDetailBinding
import com.google.firebase.auth.FirebaseAuth

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