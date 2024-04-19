package com.cacttuseducation.mycircle.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.cacttuseducation.mycircle.R
import com.cacttuseducation.mycircle.models.Post
import com.squareup.picasso.Picasso

class ExplorePageAdapter(private val list: List<Post>) : RecyclerView.Adapter<ExplorePageAdapter.ExplorePostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExplorePostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_post_explore,parent,false)
        return ExplorePostViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExplorePostViewHolder, position: Int) {
        val list = list[position]
        val test = list.name
        Picasso.get().load("http://192.168.0.117:8080/api/post/$test").into(holder.postImage)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ExplorePostViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val postImage: AppCompatImageView = itemView.findViewById(R.id.explorePostImage)
    }
}