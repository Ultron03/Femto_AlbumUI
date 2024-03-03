package com.example.femto_second_ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.femto_second_ui.albums.AlbumsFragment
import com.example.femto_second_ui.photo.PhotoFragment

class ViewPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PhotoFragment()
            else -> AlbumsFragment()
        }
    }
}