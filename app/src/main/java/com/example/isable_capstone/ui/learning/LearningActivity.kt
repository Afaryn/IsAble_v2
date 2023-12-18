package com.example.isable_capstone.ui.learning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.isable_capstone.R

class LearningActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learning)

        val toolbar = findViewById<Toolbar>(R.id.xml_toolbar)


        setSupportActionBar(toolbar)
        supportActionBar?.title= "Learn Sign Language"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        findViewById<ImageButton>(R.id.btn_alfabet_learn).setOnClickListener {
            Toast.makeText(this, "Alphabet", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.btn_number_learn).setOnClickListener {
            Toast.makeText(this, "Number", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return true
    }
}