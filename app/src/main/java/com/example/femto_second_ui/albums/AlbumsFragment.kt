package com.example.femto_second_ui.albums

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.femto_second_ui.DataExtractionCode.all_files_

import com.example.femto_second_ui.R
import java.io.File





class AlbumsFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var albumRecyclerViewAdapter: AlbumRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_albums,container,false)
        rv = view.findViewById(R.id.rv)
        val list = mutableListOf<AlbumData>()

        val files= all_files_
        val imageRes = mutableListOf<String>()
        for (i in files){
            imageRes.add(i.image_path_)
        }
        val uniqueParent = mapImageResourcesToParentFolderNames(imageRes)
        Log.i("Hello--",uniqueParent.toString())
        for ((parentName, imagePaths) in uniqueParent) {
            println("Parent Name: $parentName")
            list.add(AlbumData(
                img = imagePaths[0],
                name = parentName,
                amount = imagePaths.size.toString()
            ))
        }






        albumRecyclerViewAdapter = AlbumRecyclerViewAdapter(requireContext(),list)
        val linearLayoutManager:LinearLayoutManager = GridLayoutManager(requireContext(),3)
        rv.layoutManager = linearLayoutManager
        rv.adapter = albumRecyclerViewAdapter
        return view
    }


    fun mapImageResourcesToParentFolderNames(imageResources: MutableList<String>): Map<String, MutableList<String>> {
        val parentToImageMap = mutableMapOf<String, MutableList<String>>()

        for (imageResource in imageResources) {
            val file = File(imageResource)
            val parent = file.parentFile
            if (parent != null) {
                val parentName = parent.name
                if (!parentToImageMap.containsKey(parentName)) {
                    parentToImageMap[parentName] = mutableListOf()
                }
                parentToImageMap[parentName]?.add(imageResource)
            }
        }

        return parentToImageMap
    }
}