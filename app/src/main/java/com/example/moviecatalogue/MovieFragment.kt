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
//        if (main.getStringSharePref(MainApp.SAVE_INSTANCE_LIST_MOVIE,activity!!) == ""){
//            main.putStringSharePref(MainApp.SAVE_INSTANCE_LIST_MOVIE,MainApp.SAVE_INSTANCE_LIST_MOVIE,activity!!)
//            initRecycler(mutableList)
//            getMovie()
//        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        if(savedInstanceState != null){
//            val savedMutableList = savedInstanceState.getParcelableArrayList<Movie>(MainApp.SAVE_INSTANCE_LIST_MOVIE).toMutableList()
//            mutableList.clear()
//            mutableList.addAll(savedMutableList)
//
//            initRecycler(savedMutableList)
//            movieAdapter.notifyDataSetChanged()
//        }else{
//            main.putStringSharePref(MainApp.SAVE_INSTANCE_LIST_MOVIE,MainApp.SAVE_INSTANCE_LIST_MOVIE,activity!!)
            initRecycler(mutableList)
            getMovie()
//        }
    }

    private fun getMovie() {
        mutableList.clear()

        if (pb_main != null) {
            pb_main.visibility = View.VISIBLE
        }

        main.getMovie(activity!!,{
            if (pb_main != null) {
                pb_main.visibility = View.GONE
            }
            mutableList.addAll(it)
            movieAdapter.notifyDataSetChanged()
        }, {
            toast(activity!!, it)
        })
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putParcelableArrayList(MainApp.SAVE_INSTANCE_LIST_MOVIE,ArrayList(mutableList))
////        main.putStringSharePref(MainApp.SAVE_INSTANCE_LIST_MOVIE,MainApp.SAVE_INSTANCE_LIST_MOVIE,activity!!)
//    }

    private fun initRecycler(list:MutableList<Movie>) {
        movieAdapter = MovieAdapter(list) {
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra(MainApp.MOVIE, it)
            intent.putExtra(MainApp.M_OR_T, MainApp.MOVIE)
            startActivity(intent)
        }
        rv_main.adapter = movieAdapter
        rv_main.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }
}
