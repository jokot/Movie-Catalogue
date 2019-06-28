package com.example.moviecatalogue


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_movie.*



class MovieFragment : Fragment() {

    private val main = MainApp()
    private var mutableList = mutableListOf<Movie>()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycler()
        getMovie()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getMovie() {
        mutableList.clear()
        main.getMovie({
            if (pb_main != null) {
                pb_main.visibility = View.GONE
            }
            mutableList.addAll(it)
            movieAdapter.notifyDataSetChanged()
        }, {
            toast(activity!!, it)
        })
    }


    private fun initRecycler() {
        movieAdapter = MovieAdapter(mutableList) {
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra(MainApp.MOVIE, it)
            intent.putExtra(MainApp.M_OR_T, MainApp.MOVIE)
            startActivity(intent)
        }
        rv_main.adapter = movieAdapter
        rv_main.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }
}
