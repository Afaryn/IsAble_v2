package com.example.isable_capstone.model

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.isable_capstone.R

class ArticleAdapter(var data: List<Article>, var context:Activity?):RecyclerView.Adapter<ArticleAdapter.MyViewHolder>() {
    class MyViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val judulArticle = view.findViewById<TextView>(R.id.tv_judul_article)
        val gambarArticle = view.findViewById<ImageView>(R.id.iv_article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_artikel,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.judulArticle.text = data[position].title
        holder.gambarArticle.setImageResource(data[position].image)
    }
}