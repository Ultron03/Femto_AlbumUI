package com.example.femto_second_ui.albums

import android.content.Context
import android.icu.number.IntegerWidth
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.femto_second_ui.R
import java.lang.ProcessBuilder.Redirect

class AlbumRecyclerViewAdapter(
    val context: Context,
    private val listOfMedia:List<AlbumData>
):RecyclerView.Adapter<AlbumRecyclerViewAdapter.AlbumViewHolder>() {


    class AlbumViewHolder(view: View):RecyclerView.ViewHolder(view){
        val image:ImageView = view.findViewById(R.id.content_image)
        val name:TextView = view.findViewById(R.id.content_name)
        val amount:TextView = view.findViewById(R.id.content_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_albums,parent,false)
        return AlbumViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfMedia.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        Glide.with(context)
            .load(listOfMedia[position].img)
            .into(holder.image)
        holder.name.text = listOfMedia[position].name
        holder.amount.text = listOfMedia[position].amount
    }

}