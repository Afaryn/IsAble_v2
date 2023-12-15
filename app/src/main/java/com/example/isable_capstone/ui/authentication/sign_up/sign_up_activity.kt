package com.example.isable_capstone.ui.authentication.sign_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.isable_capstone.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class sign_up_activity : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val toolbar = findViewById<Toolbar>(R.id.xml_toolbar)

        auth = FirebaseAuth.getInstance()

        setSupportActionBar(toolbar)
        supportActionBar?.title=getString(R.string.sign_in)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findViewById<Button>(R.id.btn_regist).setOnClickListener{
            val email = findViewById<TextInputEditText>(R.id.email_register).text.toString()
            val pass = findViewById<TextInputEditText>(R.id.password_register).text.toString()

            auth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful){
                        Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    @Suppress("DEPRECATION")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Tindakan yang akan diambil saat tombol back ditekan
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}