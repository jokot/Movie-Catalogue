package com.example.moviecatalogue.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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
import com.example.moviecatalogue.ext.toast
import com.example.moviecatalogue.model.Movie
import kotlinx.android.synthetic.main.fragment_movie.*


class MovieFragment : Fragment() {
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainApp.CHANGE_LANGUAGE_CODE) {
            activity?.finish()
            startActivity(activity?.intent)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecycler()

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.getMovie().observe(this, getMovie)

        if (savedInstanceState?.getBoolean(MainApp.MOVIE) != true) {
            showLoading(true)
            mainViewModel.setMovie(requireActivity()) {
                showLoading(false)
                toast(requireActivity(), it)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(MainApp.MOVIE, true)
    }

    private val getMovie = Observer<MutableList<Movie>> {
        if (it != null) {
            movieAdapter.setData(it)
            showLoading(false)
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

    private fun showLoading(state: Boolean) {
        if (state) {
            pb_main.visibility = View.VISIBLE
        } else {
            pb_main.visibility = View.GONE
        }
    }
}
