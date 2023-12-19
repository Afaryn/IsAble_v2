package com.example.isable_capstone.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.isable_capstone.R
import com.example.isable_capstone.model.ArticleAdapter
import com.example.isable_capstone.model.ArticleDataResource
import com.example.isable_capstone.ui.detailArticle.DetailArticleActivity

import com.example.isable_capstone.ui.learning.LearningActivity
import com.example.isable_capstone.ui.maps.MapsActivity

class HomeFragment : Fragment(), ArticleAdapter.OnItemClickListener {

    private lateinit var rvArticle: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //<---Slider--->
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.banner1))
        imageList.add(SlideModel(R.drawable.banner2))
        imageList.add(SlideModel(R.drawable.banner3))
        imageList.add(SlideModel(R.drawable.banner4))

        val imageSlider = view.findViewById<ImageSlider>(R.id.banner_slide)
        imageSlider.setImageList(imageList)

        val delayInMillis = 2000L
        imageSlider.startSliding(delayInMillis)

        //<---Maps--->
        val btnMap = view.findViewById<ImageButton>(R.id.btn_map)
        btnMap.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            startActivity(intent)
        }

        //<---Learning--->
        val btnLearn = view.findViewById<ImageButton>(R.id.btn_learning)
        btnLearn.setOnClickListener {
            val intent = Intent(activity, LearningActivity::class.java)
            startActivity(intent)
        }

        //<---RecyclerView--->
        val lm = LinearLayoutManager(activity)
        lm.orientation = LinearLayoutManager.VERTICAL
        rvArticle = view.findViewById(R.id.rv_artikel)

        rvArticle.setHasFixedSize(false)
        rvArticle.layoutManager = lm

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
