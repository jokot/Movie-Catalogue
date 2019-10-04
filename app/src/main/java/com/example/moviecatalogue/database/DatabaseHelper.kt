package com.example.moviecatalogue.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import com.example.moviecatalogue.database.DatabaseContract.MovieColumns.Companion.BACKDROP_PATH
import com.example.moviecatalogue.database.DatabaseContract.MovieColumns.Companion.OVERVIEW
import com.example.moviecatalogue.database.DatabaseContract.MovieColumns.Companion.POSTER_PATH
import com.example.moviecatalogue.database.DatabaseContract.MovieColumns.Companion.RELEAS_DATE
import com.example.moviecatalogue.database.DatabaseContract.MovieColumns.Companion.TABLE_FAVORITE_MOVIE
import com.example.moviecatalogue.database.DatabaseContract.MovieColumns.Companion.TITLE
import com.example.moviecatalogue.database.DatabaseContract.MovieColumns.Companion.VOTE_VARAGE
import com.example.moviecatalogue.database.DatabaseContract.TvShowColumns.Companion.POPULARITY
import com.example.moviecatalogue.database.DatabaseContract.TvShowColumns.Companion.TABLE_FAVORITE_TV_SHOW
import com.example.moviecatalogue.database.DatabaseContract.TvShowColumns.Companion.VOTE_AVERAGE
import com.example.moviecatalogue.database.DatabaseContract.TvShowColumns.Companion.VOTE_COUNT


internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "FavoriteCatalogue.db"

        private const val DATABASE_VERSION = 1


        private val SQL_CREATE_TABLE_MOVIE = "CREATE TABLE $TABLE_FAVORITE_MOVIE" +
                " (${_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.MovieColumns._ID} INTEGER," +
                " $BACKDROP_PATH TEXT NOT NULL," +
                " $OVERVIEW TEXT NOT NULL," +
                " $POSTER_PATH TEXT NOT NULL," +
                " $RELEAS_DATE TEXT NOT NULL," +
                " $TITLE TEXT NOT NULL," +
                " $VOTE_VARAGE REAL)"

        private val SQL_CREATE_TABLE_TV_SHOW = "CREATE TABLE $TABLE_FAVORITE_TV_SHOW" +
                " (${_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " (${DatabaseContract.TvShowColumns._ID} INTEGER," +
                " ${DatabaseContract.TvShowColumns.BACKDROP_PATH} TEXT NOT NULL," +
                " ${DatabaseContract.TvShowColumns.OVERVIEW} TEXT NOT NULL," +
                " ${DatabaseContract.TvShowColumns.POSTER_PATH} TEXT NOT NULL," +
                " ${DatabaseContract.TvShowColumns.FIRST_AIR_DATE} TEXT NOT NULL," +
                " ${DatabaseContract.TvShowColumns.NAME} TEXT NOT NULL," +
                " ${DatabaseContract.TvShowColumns.ORIGINAL_LANGUAGE} TEXT NOT NULL," +
                " ${DatabaseContract.TvShowColumns.ORIGINAL_NAME} TEXT NOT NULL," +
                " $POPULARITY REAL," +
                " $VOTE_AVERAGE REAL," +
                " $VOTE_COUNT REAL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE)
        db.execSQL(SQL_CREATE_TABLE_TV_SHOW)
    }

    /*
    Method onUpgrade akan di panggil ketika terjadi perbedaan versi
    Gunakan method onUpgrade untuk melakukan proses migrasi data
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        /*
        Drop table tidak dianjurkan ketika proses migrasi terjadi dikarenakan data user akan hilang,
        */
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FAVORITE_MOVIE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FAVORITE_TV_SHOW")
        onCreate(db)
    }
}