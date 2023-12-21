package com.example.isable_capstone.ui.detailArticle

import android.content.Intent
import android.content.Intent.EXTRA_TEXT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.bumptech.glide.Glide
import com.example.isable_capstone.R
import com.example.isable_capstone.databinding.ActivityDetailArticleBinding
import com.example.isable_capstone.model.ArticleDataResource

@Suppress("DEPRECATION")
class DetailArticleActivity : AppCompatActivity() {

    private val viewModel = DetailArticleViewModel()
    private lateinit var binding: ActivityDetailArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgToolbarBtnBack.setOnClickListener{onBackPressed()}
        binding.fabShare.setOnClickListener{
            val messageTitle = binding.tvJudulArticle.text.toString()
            val messageDesc = binding.tvDesc.text.toString()
            val berita = messageTitle + "\n\n" +messageDesc
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(EXTRA_TEXT,berita)
            intent.type="text/plain"

            startActivity(Intent.createChooser(intent,"Share to:"))
        }

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


