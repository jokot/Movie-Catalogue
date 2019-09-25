package com.example.moviecatalogue.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.moviecatalogue.model.Movie
import com.example.moviecatalogue.model.TvShow
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) :
    ManagedSQLiteOpenHelper(ctx, "FavoriteCatalogue.db", null, 1) {

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            Movie.TABLE_FAVORITE_MOVIE, true,
            Movie.BACKDROP_PATH to TEXT,
            Movie.MOVIE_ID to INTEGER + UNIQUE,
            Movie.OVERVIEW to TEXT,
            Movie.POSTER_PATH to TEXT,
            Movie.RELEAS_DATE to TEXT,
            Movie.TITLE to TEXT,
            Movie.VOTE_VARAGE to REAL
        )

        db.createTable(
            TvShow.TABLE_FAVORITE_TV_SHOW, true,
            TvShow.BACKDROP_PATH to TEXT,
            TvShow.FIRST_AIR_DATE to TEXT,
            TvShow.TV_SHOW_ID to INTEGER + UNIQUE,
            TvShow.NAME to TEXT,
            TvShow.ORIGINAL_LANGUAGE to TEXT,
            TvShow.ORIGINAL_NAME to TEXT,
            TvShow.OVERVIEW to TEXT,
            TvShow.POPULARITY to REAL,
            TvShow.POSTER_PATH to TEXT,
            TvShow.VOTE_AVERAGE to REAL,
            TvShow.VOTE_COUNT to REAL
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Movie.TABLE_FAVORITE_MOVIE, true)
        db.dropTable(TvShow.TABLE_FAVORITE_TV_SHOW, true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)