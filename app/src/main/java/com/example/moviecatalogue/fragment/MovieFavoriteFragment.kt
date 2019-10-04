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
import com.example.moviecatalogue.R
import com.example.moviecatalogue.adapter.MovieAdapter
import com.example.moviecatalogue.database.MovieHelper
import com.example.moviecatalogue.helper.MappingHelper
import com.example.moviecatalogue.model.Movie
import kotlinx.android.synthetic.main.fragment_movie_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class MovieFavoriteFragment : Fragment() {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieHelper: MovieHelper

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()

        movieHelper = MovieHelper.getInstance(requireContext())

        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Movie>(EXTRA_STATE)
            if (list != null) {
                movieAdapter.listMovies = list
            }
        }
    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            showLoading(true)
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = movieHelper.queryAll()
                MappingHelper.mapCursorToArrayListMovie(cursor)
            }
            showLoading(false)
            val notes = deferredNotes.await()
            if (notes.size > 0) {
                movieAdapter.listMovies = notes
            } else {
                movieAdapter.listMovies = ArrayList()
            }
        }
    }

    private fun initRecycler() {
        movieAdapter = MovieAdapter {
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra(MainApp.MOVIE, it)
            startActivity(intent)
        }
        movieAdapter.notifyDataSetChanged()
        rv_main.adapter = movieAdapter
        rv_main.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, movieAdapter.listMovies)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pb_main.visibility = View.VISIBLE
        } else {
            pb_main.visibility = View.GONE
        }
    }
}
