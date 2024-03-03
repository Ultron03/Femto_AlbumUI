package com.example.femto_second_ui.photo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.femto_second_ui.R
import com.example.femto_second_ui.albums.AlbumData

class InnerRecyclerViewAdapter (
    private val context:Context,
    private val listOfMedia:List<String>

):RecyclerView.Adapter<InnerRecyclerViewAdapter.InnerViewHolder>() {


    class InnerViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.images_in_each_day)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_photo_each_month,parent,false)
        return InnerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfMedia.size
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        Glide.with(context)
            .load(listOfMedia[position])
            .into(holder.image)

    }

}