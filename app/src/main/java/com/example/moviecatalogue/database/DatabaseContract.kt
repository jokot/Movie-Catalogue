package com.example.moviecatalogue.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.example.moviecatalogue"
    const val SCHEME = "content"


    internal class MovieColumns : BaseColumns {
        companion object {
            const val TABLE_FAVORITE_MOVIE: String = "movie"
            const val BACKDROP_PATH: String = "BACKDROP_PATH"
            const val _ID: String = "MOVIE_ID"
            const val OVERVIEW: String = "OVERVIEW"
            const val POSTER_PATH: String = "POSTER_PATH"
            const val RELEAS_DATE: String = "RELEAS_DATE"
            const val TITLE: String = "TITLE"
            const val VOTE_VARAGE: String = "VOTE_VARAGE"

            // untuk membuat URI content://com.example.moviecatalogue/movie
            val CONTENT_URI_MOVIE: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE_MOVIE)
                .build()
        }
    }

    internal class TvShowColumns : BaseColumns {
        companion object {
            const val TABLE_FAVORITE_TV_SHOW: String = "tvShow"
            const val BACKDROP_PATH: String = "BACKDROP_PATH"
            const val _ID: String = "TV_SHOW_ID"
            const val OVERVIEW: String = "OVERVIEW"
            const val POSTER_PATH: String = "POSTER_PATH"
            const val FIRST_AIR_DATE: String = "FIRST_AIR_DATE"
            const val NAME: String = "NAME"
            const val ORIGINAL_LANGUAGE: String = "ORIGINAL_LANGUAGE"
            const val ORIGINAL_NAME: String = "ORIGINAL_NAME"
            const val POPULARITY: String = "POPULARITY"
            const val VOTE_AVERAGE: String = "VOTE_AVERAGE"
            const val VOTE_COUNT: String = "VOTE_COUNT"
        }
    }
}