package com.example.moviecatalogue

import android.annotation.SuppressLint
import android.app.Application
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
        const val LANGUAGE = "en-US"
        const val PAGE = 1
        const val LOG_D = "LOG_D"
        const val MOVIE = "tvShow"
        const val M_OR_T = "mort"
        const val TV_SHOW = "tv_show"
        const val FRAGMENT_MOVIE_TAG = "fragment movie tag"
        const val FRAGMENT_TV_TAG = "fragment tv tag"
        const val CHANGE_LANGUAGE_CODE = 123
    }

    private val
            gson: Gson = GsonBuilder().setLenient().create()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    private val services: ApiServices = retrofit.create(ApiServices::class.java)

    fun getMovie(
        onResponse: (List<Movie>) -> Unit,
        onError: (String) -> Unit,
        category: String = CATEGORY,
        language: String = LANGUAGE,
        page: Int = PAGE
    ) {
        val call = services.getMovie(
            category,
            API_KEY,
            language
            , page
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
        onResponse: (MovieDetails) -> Unit,
        onError: (String) -> Unit,
        language: String = LANGUAGE
    ) {
        val call = services.getDetailsMovie(movieId, API_KEY, language)

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
        onResponse: (List<TvShow>) -> Unit,
        onError: (String) -> Unit,
        category: String = CATEGORY,
        language: String = LANGUAGE,
        page: Int = PAGE
    ) {
        val call = services.getTvShow(
            category,
            API_KEY,
            language,
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
        onResponse: (TvDetails) -> Unit,
        onError: (String) -> Unit,
        language: String = LANGUAGE
    ) {
        val call = services.getDetailsTvShow(tvId, API_KEY, language)
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
}