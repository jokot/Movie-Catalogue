package com.example.moviecatalogue

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("Registered")
class MainApp : Application() {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/"
        const val API_KEY = "b40f9f903ef4b4f89c53cfb00d7aeea5"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185"
        const val CATEGORY = "popular"
        const val PAGE = 1
        const val LOG_D = "LOG_D"
        const val MOVIE = "movie"
        const val M_OR_T = "mort"
        const val TV_SHOW = "tv_show"
        const val FRAGMENT_MOVIE_TAG = "fragment movie tag"
        const val FRAGMENT_TV_TAG = "fragment tv tag"
        const val CHANGE_LANGUAGE_CODE = 123
        const val SAVE_INSTANCE_LIST_MOVIE = "save instance list movie"
        const val SAVE_INSTANCE_LIST_TV = "save instance list list"
        const val SAVE_INSTANCE_FRAGMENT = "save instance fragment"
        const val SHARE_PREF = "share pref"

        val gson: Gson = GsonBuilder().setLenient().create()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val services: ApiServices = retrofit.create(ApiServices::class.java)
    }


    fun getMovie(
        activity: Activity,
        onResponse: (MutableList<Movie>) -> Unit,
        onError: (String) -> Unit,
        category: String = CATEGORY,
        page: Int = PAGE
    ) {
        val call = services.getMovie(
            category,
            API_KEY,
            activity.getString(R.string.language),
            page
        )

        call.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                logD(t.message.toString())
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) response.body()?.let {
                    onResponse(it.movie)
                } else {
                    onError(response.errorBody()!!.string())
                }
            }

        })
    }

    fun getDetailsMovie(
        movieId: Int,
        activity: Activity,
        onResponse: (MovieDetails) -> Unit,
        onError: (String) -> Unit
    ) {
        val call = services.getDetailsMovie(movieId, API_KEY, activity.getString(R.string.language))

        call.enqueue(object : Callback<MovieDetails> {
            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                logD(t.message.toString())
            }

            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                if (response.isSuccessful) response.body()?.let {
                    onResponse(it)
                } else {
                    onError(response.errorBody()!!.string())
                }
            }

        })
    }

    fun getTvShow(
        activity: Activity,
        onResponse: (List<TvShow>) -> Unit,
        onError: (String) -> Unit,
        category: String = CATEGORY,
        page: Int = PAGE
    ) {
        val call = services.getTvShow(
            category,
            API_KEY,
            activity.getString(R.string.language),
            page
        )
        call.enqueue(object : Callback<TvResponse> {
            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                logD(t.message.toString())
            }

            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                if (response.isSuccessful) response.body()?.let {
                    onResponse(it.tvShow)
                } else {
                    onError(response.errorBody()!!.string())
                }
            }
        })
    }

    fun getDetailTvShow(
        tvId: Int,
        activity: Activity,
        onResponse: (TvDetails) -> Unit,
        onError: (String) -> Unit
    ) {
        val call = services.getDetailsTvShow(tvId, API_KEY, activity.getString(R.string.language))
        call.enqueue(object : Callback<TvDetails> {
            override fun onFailure(call: Call<TvDetails>, t: Throwable) {
                logD(t.message.toString())
            }

            override fun onResponse(call: Call<TvDetails>, response: Response<TvDetails>) {
                if (response.isSuccessful) response.body()?.let {
                    onResponse(it)
                } else onError(response.errorBody()!!.string())
            }

        })
    }

    fun putStringSharePref(key: String, value: String, activity: Activity) {
        val editor = activity.getSharedPreferences(SHARE_PREF, Context.MODE_PRIVATE).edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringSharePref(key: String, activity: Activity): String {
        val sharePref = activity.getSharedPreferences(SHARE_PREF, Context.MODE_PRIVATE)
        return sharePref.getString(key, "")!!
    }
}