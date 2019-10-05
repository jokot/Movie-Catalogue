package com.example.moviecatalogue.helper

import android.database.Cursor
import com.example.moviecatalogue.database.DatabaseContract
import com.example.moviecatalogue.model.Movie
import com.example.moviecatalogue.model.TvShow

object MappingHelper {

    fun mapCursorToArrayListMovie(moviesCursor: Cursor): ArrayList<Movie> {
        val moviesList = ArrayList<Movie>()
        while (moviesCursor.moveToNext()) {
            val id =
                moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID))
            val backdropPath =
                moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.BACKDROP_PATH))
            val overview =
                moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.OVERVIEW))
            val posterPath =
                moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTER_PATH))
            val releaseDate =
                moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RELEAS_DATE))
            val tittle =
                moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE))
            val voteAverage =
                moviesCursor.getFloat(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.VOTE_VARAGE))
            moviesList.add(
                Movie(
                    backdropPath,
                    id,
                    overview,
                    posterPath,
                    releaseDate,
                    tittle,
                    voteAverage
                )
            )
        }

        return moviesList
    }

    fun mapCursorToObjectMovie(moviesCursor: Cursor): Movie {
        moviesCursor.moveToNext()
        val id =
            moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID))
        val backdropPath =
            moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.BACKDROP_PATH))
        val overview =
            moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.OVERVIEW))
        val posterPath =
            moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTER_PATH))
        val releaseDate =
            moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RELEAS_DATE))
        val tittle =
            moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE))
        val voteAverage =
            moviesCursor.getFloat(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.VOTE_VARAGE))
        return Movie(backdropPath, id, overview, posterPath, releaseDate, tittle, voteAverage)
    }

    fun mapCursorToArrayListTvShow(tvShowCursor: Cursor): ArrayList<TvShow> {
        val tvShowList = ArrayList<TvShow>()
        while (tvShowCursor.moveToNext()) {
            val id =
                tvShowCursor.getInt(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.Companion._ID))
            val backdropPath =
                tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.BACKDROP_PATH))
            val overview =
                tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.OVERVIEW))
            val posterPath =
                tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.POSTER_PATH))
            val firstAirDate =
                tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.FIRST_AIR_DATE))
            val name =
                tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.NAME))
            val originalLang =
                tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.ORIGINAL_LANGUAGE))
            val originalName =
                tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.ORIGINAL_NAME))
            val popularity =
                tvShowCursor.getDouble(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.POPULARITY))
            val voteAverage =
                tvShowCursor.getDouble(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.VOTE_AVERAGE))
            val voteCount =
                tvShowCursor.getDouble(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.VOTE_COUNT))
            tvShowList.add(
                TvShow(
                    backdropPath,
                    firstAirDate,
                    id,
                    name,
                    originalLang,
                    originalName,
                    overview,
                    popularity,
                    posterPath,
                    voteAverage,
                    voteCount
                )
            )
        }

        return tvShowList
    }

    fun mapCursorToObjectTvShow(tvShowCursor: Cursor): TvShow {

        tvShowCursor.moveToFirst()
        val id =
            tvShowCursor.getInt(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.Companion._ID))
        val backdropPath =
            tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.BACKDROP_PATH))
        val overview =
            tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.OVERVIEW))
        val posterPath =
            tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.POSTER_PATH))
        val firstAirDate =
            tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.FIRST_AIR_DATE))
        val name =
            tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.NAME))
        val originalLang =
            tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.ORIGINAL_LANGUAGE))
        val originalName =
            tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.ORIGINAL_NAME))
        val popularity =
            tvShowCursor.getDouble(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.POPULARITY))
        val voteAverage =
            tvShowCursor.getDouble(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.VOTE_AVERAGE))
        val voteCount =
            tvShowCursor.getDouble(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.VOTE_COUNT))

        return TvShow(
            backdropPath,
            firstAirDate,
            id,
            name,
            originalLang,
            originalName,
            overview,
            popularity,
            posterPath,
            voteAverage,
            voteCount
        )
    }
}