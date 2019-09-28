package com.example.moviecatalogue

import android.app.SearchManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.example.moviecatalogue.ext.toast

class SearchMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.search_movie)
        }
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
                        getString(R.string.search).toast(this@SearchMovieActivity)
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.toast(this@SearchMovieActivity)
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

}
