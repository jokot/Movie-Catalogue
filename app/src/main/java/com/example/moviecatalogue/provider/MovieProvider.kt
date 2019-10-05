package com.example.moviecatalogue.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.moviecatalogue.database.DatabaseContract.AUTHORITY
import com.example.moviecatalogue.database.DatabaseContract.MovieColumns.Companion.CONTENT_URI_MOVIE
import com.example.moviecatalogue.database.DatabaseContract.MovieColumns.Companion.TABLE_FAVORITE_MOVIE
import com.example.moviecatalogue.database.MovieHelper

class MovieProvider : ContentProvider() {

    companion object {
        private const val MOVIE = 1
        private const val MOVIE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var movieHelper: MovieHelper

        init {
            // content://com.dicoding.picodiploma.mynotesapp/note
            sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_MOVIE, MOVIE)
            // content://com.dicoding.picodiploma.mynotesapp/note/id
            sUriMatcher.addURI(
                AUTHORITY,
                "$TABLE_FAVORITE_MOVIE/#",
                MOVIE_ID
            )
        }
    }

    override fun onCreate(): Boolean {
        movieHelper = MovieHelper.getInstance(context as Context)
        movieHelper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            MOVIE -> cursor = movieHelper.queryAll()
            MOVIE_ID -> cursor = movieHelper.queryById(uri.lastPathSegment.toString())
            else -> cursor = null
        }
        return cursor
    }


    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        val added: Long = when (MOVIE) {
            sUriMatcher.match(uri) -> movieHelper.insert(contentValues)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI_MOVIE, null)
        return Uri.parse("$CONTENT_URI_MOVIE/$added")
    }


    override fun update(
        uri: Uri, contentValues: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val updated: Int = when (MOVIE_ID) {
            sUriMatcher.match(uri) -> movieHelper.update(
                uri.lastPathSegment.toString(),
                contentValues
            )
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI_MOVIE, null)
        return updated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (MOVIE_ID) {
            sUriMatcher.match(uri) -> movieHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI_MOVIE, null)
        return deleted
    }
}
