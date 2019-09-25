package com.example.moviecatalogue.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviecatalogue.DetailMovieActivity
import com.example.moviecatalogue.MainApp
import com.example.moviecatalogue.MainViewModel
import com.example.moviecatalogue.R
import com.example.moviecatalogue.adapter.MovieAdapter
import com.example.moviecatalogue.database.database
import com.example.moviecatalogue.model.Movie
import kotlinx.android.synthetic.main.fragment_movie_favorite.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

/**
 * A simple [Fragment] subclass.
 */
class MovieFavoriteFragment : Fragment() {

    private var mutableList: MutableList<Movie> = mutableListOf()
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecycler()
        getFavorite()
    }

    private fun initRecycler() {
        movieAdapter = MovieAdapter(mutableList) {
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra(MainApp.MOVIE, it)
//            intent.putExtra(
//                MainApp.M_OR_T,
//                MainApp.MOVIE
//            )
            startActivity(intent)
        }
        movieAdapter.notifyDataSetChanged()
        rv_main.adapter = movieAdapter
        rv_main.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

    private fun getFavorite() {
        context?.database?.use {
            val result = select(Movie.TABLE_FAVORITE_MOVIE)
            val favorite = result.parseList(classParser<Movie>())
            mutableList.addAll(favorite)
            movieAdapter.notifyDataSetChanged()
        }
    }
}
