package com.example.isable_capstone.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.isable_capstone.R
import com.example.isable_capstone.model.ArticleAdapter
import com.example.isable_capstone.model.ArticleDataResource
import com.example.isable_capstone.ui.detailArticle.DetailArticleActivity
import com.example.isable_capstone.ui.learning.LearningActivity
import com.example.isable_capstone.ui.maps.MapsActivity
import com.example.isable_capstone.ui.profile.DataProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class HomeFragment : Fragment(), ArticleAdapter.OnItemClickListener{
    private lateinit var rvArticle:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val auth = FirebaseAuth.getInstance()
        val reference = FirebaseDatabase.getInstance().getReference("Users")
        val userID = auth.currentUser?.uid.toString()

        val profile = view.findViewById<ImageView>(R.id.homeProfile)
        val name = view.findViewById<TextView>(R.id.usernameHome)

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
            @SuppressLint("SetTextI18n")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.getValue(DataProfile::class.java)
                val nama = data?.name.toString()
                name.text = "Halo, $nama !"
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, "Profile Error", Toast.LENGTH_SHORT).show()
            }
        })

        //<---Slider--->
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.banner1))
        imageList.add(SlideModel(R.drawable.banner2))
        imageList.add(SlideModel(R.drawable.banner3))
        imageList.add(SlideModel(R.drawable.banner4))

        val imageSlider = view.findViewById<ImageSlider>(R.id.banner_slide)
        imageSlider.setImageList(imageList)

        val delayInMillis  = 2000L
        imageSlider.startSliding(delayInMillis)

        //<---Maps--->
        val btnMap = view.findViewById<ImageButton>(R.id.btn_map)
        btnMap.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            startActivity(intent)
        }

        //<---Learnig--->
        val btnlearn = view.findViewById<ImageButton>(R.id.btn_learning)
        btnlearn.setOnClickListener {
            val intent = Intent(activity, LearningActivity::class.java)
            startActivity(intent)
        }

        //<---RecyclerView--->
        val lm = LinearLayoutManager(activity)
        lm.orientation=LinearLayoutManager.VERTICAL
        rvArticle= view.findViewById(R.id.rv_artikel)

        rvArticle.setHasFixedSize(false)
        rvArticle.layoutManager=lm

        val adapter = ArticleAdapter(this)
        adapter.data = ArticleDataResource.dummyArticle
        rvArticle.adapter = adapter

        return view
    }

    // Implementation of the item click listener
    override fun onItemClick(articleId: Int) {
        val selectedArticle = ArticleDataResource.dummyArticle[articleId]

        // Start DetailActivity and pass the selected article ID
        val intent = Intent(activity, DetailArticleActivity::class.java)
        intent.putExtra("article_id", selectedArticle.id)
        startActivity(intent)
    }
}