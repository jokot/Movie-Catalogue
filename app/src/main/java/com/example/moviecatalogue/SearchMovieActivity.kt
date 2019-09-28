package com.example.moviecatalogue

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.moviecatalogue.adapter.MovieAdapter
import com.example.moviecatalogue.ext.logD
import com.example.moviecatalogue.ext.toast
import com.example.moviecatalogue.model.Movie
import kotlinx.android.synthetic.main.activity_search_movie.*

class SearchMovieActivity : AppCompatActivity() {

    private val main = MainApp()
    private var mutableList = mutableListOf<Movie>()
    private lateinit var movieAdapter: MovieAdapter
    private var name : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.search_movie)
        }

        initRecycler(mutableList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)
        val searchManager : SearchManager? = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if(searchManager != null){
            val searchView : SearchView = menu?.findItem(R.id.search)?.actionView as SearchView
            searchView.apply {
                isIconified = false
                setSearchableInfo(searchManager.getSearchableInfo(componentName))
                queryHint = resources.getString(R.string.query_hint)
                setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        name = query
                        searchMovie()
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        name = newText
                        searchMovie()
                        return true
                    }
                })
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home ->{
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchMovie(){
        showLoading(true)
        main.searchMovie(name,this,{
            movieAdapter.setData(it)
            showLoading(false)
        },{
            logD(it)
            showLoading(false)
        })
    }

    private fun initRecycler(list: MutableList<Movie>) {
        movieAdapter = MovieAdapter(list) {
            val intent = Intent(this, DetailMovieActivity::class.java)
            intent.putExtra(MainApp.MOVIE, it)
            startActivity(intent)
        }
        movieAdapter.notifyDataSetChanged()
        rv_main.adapter = movieAdapter
        rv_main.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }


    private fun showLoading(state: Boolean) {
        if (state) {
            pb_main.visibility = View.VISIBLE
        } else {
            pb_main.visibility = View.GONE
        }
    }

}
