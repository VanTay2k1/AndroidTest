package com.example.androidtest.ex2.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidtest.ex2.fragment.AllAppFragment
import com.example.androidtest.ex2.fragment.RecentlyAppFragment

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllAppFragment()
            1 -> RecentlyAppFragment()
            else -> throw IllegalStateException("Invalid position")
        }
    }

    fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "All Apps"
            1 -> "Recent Apps"
            else -> throw IllegalStateException("Invalid position")
        }
    }
}
