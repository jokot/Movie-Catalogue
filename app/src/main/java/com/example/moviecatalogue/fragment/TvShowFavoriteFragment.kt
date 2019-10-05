package com.example.moviecatalogue.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviecatalogue.BottomNavigationActivity
import com.example.moviecatalogue.DetailTvShowActivity
import com.example.moviecatalogue.MainApp
import com.example.moviecatalogue.R
import com.example.moviecatalogue.adapter.TvShowAdapter
import com.example.moviecatalogue.database.TvShowHelper
import com.example.moviecatalogue.helper.MappingHelper
import com.example.moviecatalogue.model.TvShow
import kotlinx.android.synthetic.main.fragment_tv_show_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class TvShowFavoriteFragment : Fragment() {

    private lateinit var tvAdapter: TvShowAdapter

    private lateinit var tvShowHelper: TvShowHelper

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

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

        tvShowHelper = TvShowHelper.getInstance(BottomNavigationActivity.getContenxtApplication())

        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<TvShow>(EXTRA_STATE)
            if (list != null) {
                tvAdapter.listTvShows = list
            }
        }
    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = tvShowHelper.queryAll()
                MappingHelper.mapCursorToArrayListTvShow(cursor)
            }
            val notes = deferredNotes.await()
            if (notes.size > 0) {
                tvAdapter.listTvShows = notes
            } else {
                tvAdapter.listTvShows = ArrayList()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, tvAdapter.listTvShows)
    }

    private fun initRecycler() {
        tvAdapter = TvShowAdapter {
            val intent =
                Intent(context, DetailTvShowActivity::class.java)
            intent.putExtra(MainApp.TV_SHOW, it)
            startActivity(intent)
        }
        rv_main.adapter = tvAdapter
        rv_main.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}
