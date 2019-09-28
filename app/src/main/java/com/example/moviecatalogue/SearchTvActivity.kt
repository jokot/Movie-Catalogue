package com.example.moviecatalogue

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.moviecatalogue.adapter.TvShowAdapter
import com.example.moviecatalogue.ext.logD
import com.example.moviecatalogue.model.TvShow
import kotlinx.android.synthetic.main.activity_search_tv.*

class SearchTvActivity : AppCompatActivity() {

    private val main = MainApp()
    private var mutableList = mutableListOf<TvShow>()
    private lateinit var tvShowAdapter: TvShowAdapter
    private var name: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_tv)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.search_tv_show)
        }
        initRecycler(mutableList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchManager: SearchManager? =
            getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (searchManager != null) {
            val searchView: SearchView = menu?.findItem(R.id.search)?.actionView as SearchView
            searchView.apply {
                isIconified = false
                setSearchableInfo(searchManager.getSearchableInfo(componentName))
                queryHint = resources.getString(R.string.query_hint)
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        name = query
                        searchTvShow()
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        name = newText
                        searchTvShow()
                        return true
                    }
                })
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchTvShow() {
        showLoading(true)
        main.searchTvShow(name, this, {
            tvShowAdapter.setData(it)
            showLoading(false)
        }, {
            logD(it)
            showLoading(false)
        })
    }

    private fun initRecycler(list: MutableList<TvShow>) {
        tvShowAdapter = TvShowAdapter(list) {
            val intent = Intent(this, DetailTvShowActivity::class.java)
            intent.putExtra(MainApp.TV_SHOW, it)
            startActivity(intent)
        }
        tvShowAdapter.notifyDataSetChanged()
        rv_main.adapter = tvShowAdapter
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
