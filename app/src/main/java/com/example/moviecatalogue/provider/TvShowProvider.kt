//package com.example.moviecatalogue.provider
//
//import android.content.ContentProvider
//import android.content.ContentValues
//import android.content.Context
//import android.content.UriMatcher
//import android.database.Cursor
//import android.net.Uri
//import com.example.moviecatalogue.database.DatabaseContract.AUTHORITY
//import com.example.moviecatalogue.database.DatabaseContract.TvShowColumns.Companion.CONTENT_URI_TV_SHOW
//import com.example.moviecatalogue.database.DatabaseContract.TvShowColumns.Companion.TABLE_FAVORITE_TV_SHOW
//import com.example.moviecatalogue.database.TvShowHelper
//
//class TvShowProvider : ContentProvider() {
//
//    companion object {
//        private const val TV_SHOW = 1
//        private const val TV_SHOW_ID = 2
//        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
//        private lateinit var tvShowHelper: TvShowHelper
//
//        init {
//            // content://com.dicoding.picodiploma.mynotesapp/note
//            sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_TV_SHOW, TV_SHOW)
//            // content://com.dicoding.picodiploma.mynotesapp/note/id
//            sUriMatcher.addURI(
//                AUTHORITY,
//                "$TABLE_FAVORITE_TV_SHOW/#",
//                TV_SHOW_ID
//            )
//        }
//    }
//
//    override fun onCreate(): Boolean {
//        tvShowHelper = TvShowHelper.getInstance(context as Context)
//        tvShowHelper.open()
//        return true
//    }
//
//    override fun query(
//        uri: Uri, projection: Array<String>?, selection: String?,
//        selectionArgs: Array<String>?, sortOrder: String?
//    ): Cursor? {
//        val cursor: Cursor?
//        when (sUriMatcher.match(uri)) {
//            TV_SHOW -> cursor = tvShowHelper.queryAll()
//            TV_SHOW_ID -> cursor = tvShowHelper.queryById(uri.lastPathSegment.toString())
//            else -> cursor = null
//        }
//        return cursor
//    }
//
//
//    override fun getType(uri: Uri): String? {
//        return null
//    }
//
//    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
//        val added: Long = when (TV_SHOW) {
//            sUriMatcher.match(uri) -> tvShowHelper.insert(contentValues)
//            else -> 0
//        }
//        context?.contentResolver?.notifyChange(CONTENT_URI_TV_SHOW, null)
//        return Uri.parse("$CONTENT_URI_TV_SHOW/$added")
//    }
//
//
//    override fun update(
//        uri: Uri, contentValues: ContentValues?, selection: String?,
//        selectionArgs: Array<String>?
//    ): Int {
//        val updated: Int = when (TV_SHOW_ID) {
//            sUriMatcher.match(uri) -> tvShowHelper.update(
//                uri.lastPathSegment.toString(),
//                contentValues
//            )
//            else -> 0
//        }
//        context?.contentResolver?.notifyChange(CONTENT_URI_TV_SHOW, null)
//        return updated
//    }
//
//    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
//        val deleted: Int = when (TV_SHOW_ID) {
//            sUriMatcher.match(uri) -> tvShowHelper.deleteById(uri.lastPathSegment.toString())
//            else -> 0
//        }
//        context?.contentResolver?.notifyChange(CONTENT_URI_TV_SHOW, null)
//        return deleted
//    }
//}
