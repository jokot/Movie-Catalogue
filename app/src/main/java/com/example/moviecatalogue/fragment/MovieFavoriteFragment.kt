package com.example.moviecatalogue.fragment


import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviecatalogue.BottomNavigationActivity
import com.example.moviecatalogue.DetailMovieActivity
import com.example.moviecatalogue.MainApp
import com.example.moviecatalogue.R
import com.example.moviecatalogue.adapter.MovieAdapter
import com.example.moviecatalogue.database.DatabaseContract.MovieColumns.Companion.CONTENT_URI_MOVIE
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecycler()

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadNotesAsync()
            }
        }
        BottomNavigationActivity.getContenxtApplication().contentResolver.registerContentObserver(
            CONTENT_URI_MOVIE,
            true,
            myObserver
        )


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
            val deferredNotes = async(Dispatchers.IO) {
                //                val uri = Uri.parse(CONTENT_URI)
                val cursor =
                    BottomNavigationActivity.getContenxtApplication().contentResolver?.query(
                        CONTENT_URI_MOVIE,
                        null,
                        null,
                        null,
                        null
                    ) as Cursor
//                val cursor = movieHelper.queryAll()
                MappingHelper.mapCursorToArrayListMovie(cursor)
            }

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

}
