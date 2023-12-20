package com.example.isable_capstone.ui.learningDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.isable_capstone.R
import com.example.isable_capstone.databinding.ItemSublearningBinding
import com.example.isable_capstone.model.ArticleAdapter
import com.example.isable_capstone.response.AllAngkaResponseItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SubLearningAdapter: ListAdapter<AllAngkaResponseItem, SubLearningAdapter.MyViewHolder>(DIFF_CALLBACK){

    private var onItemClickCallback: ((AllAngkaResponseItem) -> Unit)? = null

    fun setOnItemClickCallback(callback: (AllAngkaResponseItem) -> Unit) {
        this.onItemClickCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemSublearningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class MyViewHolder(private val binding: ItemSublearningBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                @Suppress("DEPRECATION")
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val user = getItem(position)
                    onItemClickCallback?.invoke(user)
                }
            }
        }
//        fun formatDate(dateString: String?): String {
//            if (dateString == null) {
//                return ""
//            }
//
//            try {
//                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
//                val outputFormat = SimpleDateFormat("dd MMMM yyyy' - 'HH:mm", Locale.getDefault())
//
//                val date: Date = inputFormat.parse(dateString)
//                return outputFormat.format(date)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//            return ""
//        }

        fun bind(data: AllAngkaResponseItem) {
            binding.apply {
//                tvJudul.text = "${data.name}"
//                tvJudul2.text="${data.name}"
//                tvTotal.text = "${itemCount}"
//                tvCounter.text = "${adapterPosition}"
                Glide.with(itemView)
                    .load(data.link)
                    .into(ivArticle)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AllAngkaResponseItem>() {
            override fun areItemsTheSame(oldItem: AllAngkaResponseItem, newItem: AllAngkaResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: AllAngkaResponseItem, newItem: AllAngkaResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}