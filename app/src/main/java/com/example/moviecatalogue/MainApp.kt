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
        const val CATEGORY = "upcoming"
        const val LANGUAGE = "en-US"
        const val PAGE = 1
        const val LOG_D = "LOG_D"
        const val MOVIE = "movie"
    }

    private val gson: Gson = GsonBuilder().setLenient().create()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    private val services: ApiServices = retrofit.create(ApiServices::class.java)

    fun getMovie(
        category: String = CATEGORY,
        language: String = LANGUAGE,
        page: Int = PAGE,
        onResponse: (List<Movie>) -> Unit
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
                } else response.errorBody()!!.string().let {
                    toast(it)
                }
            }

        })
    }
}