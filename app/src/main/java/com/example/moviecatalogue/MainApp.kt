package com.example.moviecatalogue

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import com.example.moviecatalogue.api.ApiServices
import com.example.moviecatalogue.ext.logD
import com.example.moviecatalogue.model.*
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
        const val BASE_URL = BuildConfig.BASE_URL
        const val API_KEY = BuildConfig.API_KEY
        const val IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL
        const val CATEGORY = "popular"
        const val PAGE = 1
        const val LOG_D = "LOG_D"
        const val MOVIE = "movie"
        const val TV_SHOW = "tv_show"
        const val FRAGMENT_MOVIE_TAG = "fragment movie tag"
        const val FRAGMENT_TV_TAG = "fragment tv tag"
        const val FRAGMENT_FAVORITE_TAG = "fragment favorite tag"
        const val CHANGE_LANGUAGE_CODE = 123
        const val SHARE_PREF = "share pref"
        const val LANGUAGE = "en-US"
        const val REALEASE_REMINDER = "REALEASE_REMINDER"
        const val DAILY_REMINDER = "DAILY_REMINDER"

        private val gson: Gson = GsonBuilder().setLenient().create()
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val services: ApiServices = retrofit.create(ApiServices::class.java)
    }

    fun getMovieWidget(
        onResponse: (MutableList<Movie>) -> Unit,
        onError: (String) -> Unit,
        category: String = CATEGORY,
        page: Int = PAGE
    ) {
        val call = services.getMovie(
            category,
            API_KEY,
            LANGUAGE,
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

    fun searchMovie(
        name: String?,
        activity: Activity,
        onResponse: (MutableList<Movie>) -> Unit,
        onError: (String) -> Unit
    ) {
        val call = services.searchMovie(API_KEY, activity.getString(R.string.language), name)
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

    fun getReleaseToday(
        date:String,
        onResponse: (MutableList<Movie>) -> Unit,
        onError: (String) -> Unit
    ) {
        val call = services.releasToday(API_KEY,date,date)
        call.enqueue(object : Callback<MovieResponse>{
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

    fun searchTvShow(
        name: String?,
        activity: Activity,
        onResponse: (MutableList<TvShow>) -> Unit,
        onError: (String) -> Unit
    ) {
        val call = services.searchTvShow(API_KEY, activity.getString(R.string.language), name)
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



    fun putStringSharePref(key: String, value: String, context: Context) {
        val editor = context.getSharedPreferences(SHARE_PREF, Context.MODE_PRIVATE).edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringSharePref(key: String, context: Context): String {
        val sharePref = context.getSharedPreferences(SHARE_PREF, Context.MODE_PRIVATE)
        return sharePref.getString(key, "")!!
    }

    fun removeStringSharePref(key: String, context: Context) {
        val editor = context.getSharedPreferences(SHARE_PREF, Context.MODE_PRIVATE).edit()
        editor.remove(key)
        editor.apply()
    }
}