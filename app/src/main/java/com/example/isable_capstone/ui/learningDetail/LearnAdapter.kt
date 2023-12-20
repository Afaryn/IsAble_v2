package com.example.isable_capstone.ui.learningDetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.isable_capstone.R
import com.example.isable_capstone.api.response.LearningResponseItem

class LearnAdapter(val context: Context, private val datalist: ArrayList<LearningResponseItem>): RecyclerView.Adapter<LearnAdapter.MyViewHolder>() {
    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val tvphoto: ImageView = view.findViewById(R.id.iv_article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itewview = layoutInflater.inflate(R.layout.item_sublearning, parent, false)
        return MyViewHolder(itewview)
    }

    override fun getItemCount(): Int = datalist.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val result = datalist[position]
        Glide.with(holder.view)
            .load(result.link)
            .into(holder.tvphoto)
        holder.view.setOnClickListener{
            val detail = Intent(holder.view.context, LearningDetailActivity::class.java)
            detail.putExtra("foto", result.link)
            holder.view.context.startActivity(detail)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<LearningResponseItem>){
        this.datalist.clear()
        this.datalist.addAll(data)
        notifyDataSetChanged()
    }
}


