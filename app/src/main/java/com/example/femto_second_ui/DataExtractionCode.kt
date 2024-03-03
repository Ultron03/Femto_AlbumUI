package com.example.femto_second_ui

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import java.io.File

object DataExtractionCode {


    var store_files_with_date_ = HashMap<String,Long>()
    val all_files_ = ArrayList<image_list_show_datamodel>()

    fun collect_all_photos_mediastore_way(context_ : Context, collect_optimized : Boolean = true) : ArrayList<image_list_show_datamodel> {

        // Define the columns you want to retrieve from the Video MediaStore
        val projection = arrayOf(MediaStore.Images.Media.DATA)

        val contentResolver: ContentResolver = context_.contentResolver
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI



        // Query the MediaStore to retrieve video information
        val cursor = contentResolver.query(uri, projection, null, null, "${MediaStore.Images.Media.DATE_TAKEN} DESC")

        if (cursor != null) {
            if (cursor.moveToNext())

                do {
                    val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
                    val image_Path = cursor.getString(dataColumn)

                    store_files_with_date_[image_Path] = File(image_Path).lastModified()
                }
                while (cursor.moveToNext())
        }

        //   also search all the compressed images
//        if(collect_optimized == true) new_get_all_compressed_images(context_)

        // now sort it according to the modified date
        val all_list_ = store_files_with_date_.toList().sortedByDescending { it.second }.toMap()

        //  now make the list of all photos
        all_files_.clear()

        all_list_.forEach { it ->
            var compression_status_ = false
            val take_first_5_char_ = File(it.key).name.take(5)

            if(take_first_5_char_ == "femto") compression_status_ = true

            val data_ = image_list_show_datamodel(it.key,compression_status_,false)
            all_files_.add(data_)
        }

        return all_files_
    }



}
data class image_list_show_datamodel(
    var image_path_ : String,
    var already_compressed_status_ : Boolean = false,
    var selection_status_ : Boolean = false
)