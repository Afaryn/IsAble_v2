

package com.example.isable_capstone.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.isable_capstone.databinding.ItemArtikelBinding

class ArticleAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<ArticleAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(articleId: Int)
    }

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

                // Set click listener on the item
                itemView.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position)
                    }
                }
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
