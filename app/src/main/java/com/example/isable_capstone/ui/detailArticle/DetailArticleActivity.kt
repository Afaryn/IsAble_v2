package com.example.isable_capstone.ui.detailArticle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.isable_capstone.R
import com.example.isable_capstone.databinding.ActivityDetailArticleBinding
import com.example.isable_capstone.model.ArticleDataResource

class DetailArticleActivity : AppCompatActivity() {

    private val viewModel = DetailArticleViewModel()
    private lateinit var binding: ActivityDetailArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articleId = intent.getIntExtra("article_id", -1)

        // Dapatkan data artikel berdasarkan ID dari intent
        viewModel.setArticleId(articleId)

        // Observe changes in selectedArticle LiveData
        viewModel.selectedArticle.observe(this) { article ->
            // Update UI when selectedArticle changes
            binding.tvJudulArticle.text = article.title
            binding.tvDesc.text = article.deskripsi

            // Use Glide to load the image
            Glide.with(this)
                .load(article.image)
                .into(binding.ivImgItem)
        }

    }
}