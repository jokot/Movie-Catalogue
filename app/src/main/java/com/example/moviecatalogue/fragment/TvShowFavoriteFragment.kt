package com.example.moviecatalogue.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviecatalogue.DetailTvShowActivity
import com.example.moviecatalogue.MainApp

import com.example.moviecatalogue.R
import com.example.moviecatalogue.adapter.TvShowAdapter
import com.example.moviecatalogue.database.database
import com.example.moviecatalogue.model.TvShow
import kotlinx.android.synthetic.main.fragment_tv_show_favorite.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

/**
 * A simple [Fragment] subclass.
 */
class TvShowFavoriteFragment : Fragment() {

    private var mutableList = mutableListOf<TvShow>()
    private lateinit var tvAdapter: TvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecycler()
        getFavorite()
    }

    private fun getFavorite() {
        context?.database?.use {
            val result = select(TvShow.TABLE_FAVORITE_TV_SHOW)
            val favorite = result.parseList(classParser<TvShow>())
            mutableList.addAll(favorite)
            tvAdapter.notifyDataSetChanged()
        }
    }

    private fun initRecycler() {
        tvAdapter = TvShowAdapter(mutableList) {
            val intent =
                Intent(context, DetailTvShowActivity::class.java)
            intent.putExtra(MainApp.TV_SHOW, it)
            startActivity(intent)
        }
        rv_main.adapter = tvAdapter
        rv_main.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}
