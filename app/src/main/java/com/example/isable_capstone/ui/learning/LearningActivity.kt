package com.example.isable_capstone.ui.learning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import com.example.isable_capstone.R
import com.example.isable_capstone.ui.learningDetail.SubLearning

class LearningActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learning)

        val toolbar = findViewById<Toolbar>(R.id.xml_toolbar)


        setSupportActionBar(toolbar)
        supportActionBar?.title= "Learn Sign Language"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        findViewById<ImageButton>(R.id.btn_alfabet_learn).setOnClickListener {
            val intent = Intent(this, SubLearning::class.java)
            intent.putExtra("path", "abjad")
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.btn_number_learn).setOnClickListener {
            val intent = Intent(this, SubLearning::class.java)
            intent.putExtra("path", "angka")
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return true
    }
}