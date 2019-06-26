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

//    https://api.themoviedb.org/3/movie/301528?api_key=b40f9f903ef4b4f89c53cfb00d7aeea5&language=en-US
//    https://api.themoviedb.org/3/movie/upcoming?api_key=b40f9f903ef4b4f89c53cfb00d7aeea5&language=en-US&page=1
}