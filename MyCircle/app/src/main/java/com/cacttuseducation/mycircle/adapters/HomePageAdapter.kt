package com.cacttuseducation.mycircle.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.cacttuseducation.mycircle.R
import com.cacttuseducation.mycircle.models.Post
import com.squareup.picasso.Picasso

class HomePageAdapter(private val list: List<Post>) : RecyclerView.Adapter<HomePageAdapter.HomePostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.posts,parent,false)
        return HomePostViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomePostViewHolder, position: Int) {
        val list = list[position]

        holder.tvUsername.text = list.username
        holder.tvTimePosted.text = list.date
        holder.tvPostDescription.text = list.description

        val test = list.name
        Picasso.get().load("http://192.168.0.117:8080/api/post/$test").into(holder.postImage)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class HomePostViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val tvUsername: AppCompatTextView = itemView.findViewById(R.id.tvUsername)
        val tvTimePosted: AppCompatTextView = itemView.findViewById(R.id.tvTimePosted)
        val tvPostDescription: AppCompatTextView = itemView.findViewById(R.id.tvPostDescription)
        val postImage: AppCompatImageView = itemView.findViewById(R.id.imagePosted)

    }
}