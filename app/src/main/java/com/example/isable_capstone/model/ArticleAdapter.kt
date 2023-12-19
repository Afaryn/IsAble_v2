package com.example.isable_capstone.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.isable_capstone.R
import com.example.isable_capstone.databinding.ItemArtikelBinding
import com.example.isable_capstone.ui.home.HomeFragment

class ArticleAdapter(private val context: HomeFragment) : RecyclerView.Adapter<ArticleAdapter.MyViewHolder>() {

    private val mDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }
    })

    var data: List<Article>
        get() = mDiffer.currentList
        set(value) {
            mDiffer.submitList(value)
        }

    inner class MyViewHolder(private val binding: ItemArtikelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            with(binding) {
                tvJudulArticle.text = article.title
                Glide.with(itemView)
                    .load(article.image)
                    .into(ivArticle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemArtikelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val article = mDiffer.currentList[position]
        holder.bind(article)
    }
}