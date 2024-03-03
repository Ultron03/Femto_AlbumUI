package com.example.femto_second_ui.photo

import android.icu.number.IntegerWidth
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.femto_second_ui.DataExtractionCode
import com.example.femto_second_ui.R

import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import java.io.File
import java.text.SimpleDateFormat
import java.time.Clock
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


class PhotoFragment : Fragment() {

    private lateinit var rv: RecyclerView

    private lateinit var photoRecyclerViewAdapter: PhotoRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_albums,container,false)
        rv = view.findViewById(R.id.rv)
        val data = DataExtractionCode.store_files_with_date_
        val moredata = DataExtractionCode.all_files_
        Log.i("Hello",DataExtractionCode.collect_all_photos_mediastore_way(requireContext()).toString())
        printHashMap(data)


        val list = mutableListOf<PhotoData>()


        val allImages = DataExtractionCode.collect_all_photos_mediastore_way(requireContext())

        val groupedImages = allImages.groupBy { image ->
            val file = File(image.image_path_)
            val date = file.lastModified()
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = date
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            formatter.format(calendar.time)
        }

        groupedImages.forEach { (day, images) ->
            println("Day: $day")
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = formatter.parse(day)
            val calendar = Calendar.getInstance()
            calendar.time = date
            val day_01= calendar.get(Calendar.DAY_OF_MONTH)

            println("Day: $day_01")
            println("Images:")
            val listImg = mutableListOf<String>()
            images.forEach { image ->
                println("Path: ${image.image_path_}")
                println("Compression status: ${image.already_compressed_status_}")
                println("Selection status: ${image.selection_status_}")
                listImg.add(image.image_path_)
            }
            list.add(
                PhotoData(
                    day = getDayOfWeek(day),
                    image = listImg
                )
            )

        }






        photoRecyclerViewAdapter = PhotoRecyclerViewAdapter(requireContext(),list)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = photoRecyclerViewAdapter
        return view
    }
    fun printHashMap(hashMap : HashMap<String, Long>){
        // isEmpty() function to check whether
        // the hashMap is empty or not
        if(hashMap.isEmpty()){
            println("hashMap is empty")
        }else{
            Log.i("HelloW","hashMap : " + hashMap.toSortedMap())
        }
    }
    fun getDayOfWeek(date: String): String {
        val format = "yyyy-MM-dd"
        val localDate = LocalDate.parse(date)
        val now = LocalDate.now(ZoneId.systemDefault())
        return when {
            localDate == now -> "Today"
            localDate == now.minusDays(1) -> "Yesterday"
            else -> localDate.dayOfWeek.name.toLowerCase().capitalize()
        }
}
}


