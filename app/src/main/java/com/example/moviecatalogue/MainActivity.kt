package com.example.moviecatalogue

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val main = MainApp()
    private var mutableList = mutableListOf<Movie>()
    private lateinit var mainAdapter : MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecycler()
        getMovie()
    }

    private fun getMovie(){
        mutableList.clear()
        main.getMovie {
            pb_main.visibility = View.GONE
            mutableList.addAll(it)
            mainAdapter.notifyDataSetChanged()
        }
    }

    private fun initRecycler(){
        mainAdapter = MainAdapter(mutableList) {
            val intent = Intent(this,DetailMovieActivity::class.java)
            intent.putExtra(MainApp.MOVIE,it)
            startActivity(intent)
        }
        rv_main.adapter = mainAdapter
        rv_main.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

    }
}
