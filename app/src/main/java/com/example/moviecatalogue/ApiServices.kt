package com.example.moviecatalogue


import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Call


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
        @Query("api_key" ) apiKey: String,
        @Query("language") language: String
    ):Call<MovieDetails>


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
        @Query("api_key" ) apiKey: String,
        @Query("language") language: String
    ):Call<TvDetails>
}