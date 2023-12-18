package com.example.isable_capstone.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.isable_capstone.R
import com.example.isable_capstone.ui.onBoarding.OnBoardingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val auth = FirebaseAuth.getInstance()
        val reference = FirebaseDatabase.getInstance().getReference("Users")
        val userID = auth.currentUser?.uid.toString()

        val akunEmail = auth.currentUser?.email.toString()

        val name = view.findViewById<TextView>(R.id.profileName)
        val nameAtas = view.findViewById<TextView>(R.id.text_fullname)
        val email = view.findViewById<TextView>(R.id.profileEmail)
        val username = view.findViewById<TextView>(R.id.profileUsername)
        val usernameAtas = view.findViewById<TextView>(R.id.text_username)
        val pass = view.findViewById<TextView>(R.id.profilePassword)

//        name.text = userID
        reference.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.getValue(DataProfile::class.java)

                name.text = data?.name.toString()
                nameAtas.text = data?.name.toString()
                email.text = data?.email.toString()
                username.text = data?.username.toString()
                usernameAtas.text = data?.username.toString()
                pass.text = data?.password.toString()
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}