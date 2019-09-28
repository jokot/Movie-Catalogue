package com.example.moviecatalogue.api


import com.example.moviecatalogue.model.MovieDetails
import com.example.moviecatalogue.model.MovieResponse
import com.example.moviecatalogue.model.TvDetails
import com.example.moviecatalogue.model.TvResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiServices {

    @GET("/3/movie/{category}")
    fun getMovie(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("/3/movie/{movie_id}")
    fun getDetailsMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<MovieDetails>


    @GET("/3/tv/{category}")
    fun getTvShow(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<TvResponse>

    @GET("/3/tv/{tv_id}")
    fun getDetailsTvShow(
        @Path("tv_id") tv_id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<TvDetails>

    @GET("/3/search/movie")
    fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") name:String): Call<MovieResponse>

    @GET("/3/search/tv")
    fun searchTvShow(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") name:String): Call<TvResponse>
}