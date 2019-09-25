package com.example.moviecatalogue.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.moviecatalogue.R
import com.example.moviecatalogue.fragment.MovieFavoriteFragment

class TabAdapter(fm: FragmentManager, private val numOfTabs: Int, private val context: Context) :
    FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MovieFavoriteFragment()
            else -> MovieFavoriteFragment()
        }
    }

    override fun getCount(): Int {
        return numOfTabs
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return when (position) {
            0 -> context.getString(R.string.movie)
            else -> context.getString(R.string.tv_show)
        }

    }
}