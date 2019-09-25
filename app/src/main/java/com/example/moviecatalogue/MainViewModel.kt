package com.example.moviecatalogue

import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val main = MainApp()
    val listMovie = MutableLiveData<MutableList<Movie>>()
    val listTvShow = MutableLiveData<MutableList<TvShow>>()

    fun setMovie(
        activity: Activity,
        onError: (String) -> Unit
    ) {
        val listMovie: MutableList<Movie> = mutableListOf()
        main.getMovie(activity, {
            listMovie.addAll(it)
            this@MainViewModel.listMovie.postValue(listMovie)
        }, {
            onError(it)
        })

    }


    fun setTvShow(
        activity: Activity,
        onError: (String) -> Unit
    ) {
        val listTvShow: MutableList<TvShow> = mutableListOf()

        main.getTvShow(activity, {
            listTvShow.addAll(it)
            this@MainViewModel.listTvShow.postValue(listTvShow)
        }, {
            onError(it)
        })
    }


    fun getMovie(): LiveData<MutableList<Movie>> {
        return listMovie
    }

    fun getTvShow(): LiveData<MutableList<TvShow>> {
        return listTvShow
    }
}