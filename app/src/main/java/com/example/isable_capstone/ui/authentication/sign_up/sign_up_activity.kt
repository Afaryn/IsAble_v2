package com.example.isable_capstone.ui.authentication.sign_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.isable_capstone.MainActivity
import com.example.isable_capstone.R
import com.example.isable_capstone.ui.profile.DataProfile
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class sign_up_activity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val toolbar = findViewById<Toolbar>(R.id.xml_toolbar)

        auth = FirebaseAuth.getInstance()
        val reference = FirebaseDatabase.getInstance().getReference("Users")

        setSupportActionBar(toolbar)
        supportActionBar?.title=getString(R.string.sign_in)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findViewById<Button>(R.id.btn_regist).setOnClickListener{
            val email = findViewById<TextInputEditText>(R.id.email_register).text.toString()
            val pass = findViewById<TextInputEditText>(R.id.password_register).text.toString()
            val nama = findViewById<TextInputEditText>(R.id.fullnameEt).text.toString()
            val username = findViewById<TextInputEditText>(R.id.nicknameEt).text.toString()

            val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
            var valueRadio = ""

            val selectedRadioButton = radioGroup.checkedRadioButtonId
            if (selectedRadioButton != -1) {
                val radioButton = findViewById<RadioButton>(selectedRadioButton)
                valueRadio = radioButton.text.toString()
            } else {
                Toast.makeText(this, "No option selected", Toast.LENGTH_SHORT).show()
            }

            auth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful){
                        val userID = auth.currentUser?.uid.toString()
                        val user = DataProfile(nama, email, username, pass, valueRadio)
                        reference.child(userID).setValue(user)
                            .addOnCompleteListener {
                                Log.d("Registration", "Successfully registered user")
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }.addOnFailureListener { err ->
                                Log.e("Registration", "Error: ${err.message}")
                            }
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