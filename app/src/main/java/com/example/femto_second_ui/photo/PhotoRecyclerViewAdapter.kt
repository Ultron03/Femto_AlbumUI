package com.example.femto_second_ui.photo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.femto_second_ui.R

class PhotoRecyclerViewAdapter(
    private val context: Context,
    private val listOfDay:List<PhotoData>,
//    private val listOfPhoto:List<Int>
):RecyclerView.Adapter<PhotoRecyclerViewAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(view: View):RecyclerView.ViewHolder(view){
        var day:TextView = view.findViewById(R.id.txt_days)
        var rv:RecyclerView = view.findViewById(R.id.rv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_photo,parent,false)
        return PhotoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfDay.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.day.text = listOfDay[position].day
        val linearLayoutManager:LinearLayoutManager = GridLayoutManager(context,4)
        holder.rv.layoutManager = linearLayoutManager
        holder.rv.adapter = InnerRecyclerViewAdapter(context,listOfDay[position].image)
    }
}