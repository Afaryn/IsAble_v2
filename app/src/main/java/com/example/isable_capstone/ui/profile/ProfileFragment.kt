package com.example.isable_capstone.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.isable_capstone.R
import com.example.isable_capstone.ui.onBoarding.OnBoardingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val auth = FirebaseAuth.getInstance()
        val reference = FirebaseDatabase.getInstance().getReference("Users")
        val userID = auth.currentUser?.uid.toString()

        val name = view.findViewById<TextView>(R.id.profileName)
        val nameAtas = view.findViewById<TextView>(R.id.text_fullname)
        val email = view.findViewById<TextView>(R.id.profileEmail)
        val username = view.findViewById<TextView>(R.id.profileUsername)
        val usernameAtas = view.findViewById<TextView>(R.id.text_username)
        val disability = view.findViewById<TextView>(R.id.profileDisability)

        val profile = view.findViewById<ImageView>(R.id.iv_profile)

        val mStorageRef = FirebaseStorage.getInstance().getReference("Users")
        val imageRef = mStorageRef.child(userID)

        imageRef.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(this)
                .load(uri)
                .into(profile)
        }.addOnFailureListener { exception ->
            Log.e("Firebase", "Error getting download URL: ${exception.message}")
        }

        reference.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.getValue(DataProfile::class.java)

                name.text = data?.name.toString()
                nameAtas.text = data?.name.toString()
                email.text = data?.email.toString()
                username.text = data?.username.toString()
                usernameAtas.text = data?.username.toString()
                disability.text = data?.disabilitas.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, "Profile Error", Toast.LENGTH_SHORT).show()
            }
        })

        view.findViewById<Button>(R.id.btn_logout).setOnClickListener {
            auth.signOut()
            val intent = Intent (context, OnBoardingActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}