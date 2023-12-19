package com.example.isable_capstone.ui.authentication.sign_up

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.isable_capstone.MainActivity
import com.example.isable_capstone.R
import com.example.isable_capstone.ui.profile.DataProfile
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView

class sign_up_activity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var btnUpload: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val toolbar = findViewById<Toolbar>(R.id.xml_toolbar)

        auth = FirebaseAuth.getInstance()
        val reference = FirebaseDatabase.getInstance().getReference("Users")

        setSupportActionBar(toolbar)
        supportActionBar?.title=getString(R.string.sign_in)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnUpload = findViewById(R.id.btn_regist)

        findViewById<TextView>(R.id.btnText_uploadphoto).setOnClickListener {
            Toast.makeText(this, "UP photo", Toast.LENGTH_SHORT).show()
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        btnUpload.setOnClickListener{
            val email = findViewById<TextInputEditText>(R.id.email_register).text.toString()
            val pass = findViewById<TextInputEditText>(R.id.password_register).text.toString()
            val nama = findViewById<TextInputEditText>(R.id.fullnameEt).text.toString()
            val username = findViewById<TextInputEditText>(R.id.nicknameEt).text.toString()

            val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
            var valueRadio = ""

            val imgURI = btnUpload.tag as Uri?

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

                        uploadPhoto(userID, imgURI!!)

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

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val uri: Uri = data?.data!!
                findViewById<CircleImageView>(R.id.photoProfile).setImageURI(uri)
                btnUpload.tag = uri
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadPhoto(uId: String, selectedImageUri: Uri) {
        val mStorageRef = FirebaseStorage.getInstance().getReference("Users")
        val uploadTask = mStorageRef.child(uId).putFile(selectedImageUri)
        uploadTask.addOnSuccessListener {
            Log.e("Frebase", "Image Upload success")
        }.addOnFailureListener {
            Log.e("Frebase", "Image Upload fail")
        }
    }

    @Suppress("DEPRECATION")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}