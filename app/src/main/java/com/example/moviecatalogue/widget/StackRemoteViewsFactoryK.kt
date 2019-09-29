package com.example.moviecatalogue.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviecatalogue.MainApp
import com.example.moviecatalogue.R
import com.example.moviecatalogue.database.database
import com.example.moviecatalogue.model.Movie
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class StackRemoteViewsFactoryK(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems: MutableList<Bitmap> = mutableListOf()
    private val listMovie: MutableList<Movie> = mutableListOf()
    private var listMovieOff: MutableList<String?> = mutableListOf()
    private var listPath: MutableList<String?> = mutableListOf()
    private lateinit var bitmap: Bitmap

    private fun getFavorite(onSucsess: () -> Unit,onEmpty: () -> Unit) {
        mContext.database.use {
            val result = select(Movie.TABLE_FAVORITE_MOVIE)
            val favorite = result.parseList(classParser<Movie>())
            if(favorite.isNotEmpty()){
                listMovie.clear()
                listMovie.addAll(favorite)
                onSucsess()
            }else{
                onEmpty()
            }

        }
    }

    override fun onCreate() {
        getFavorite( {
            listMovieOff.clear()
            for (i in 0 until listMovie.size) {
                if (i < 5) {
                    listMovieOff.add(listMovie[i].posterPath)
                }
            }
        },{
            mWidgetItems.clear()
            mWidgetItems.add(
                BitmapFactory.decodeResource(
                    mContext.resources,
                    R.drawable.darth_vader
                )
            )
        })
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun onDataSetChanged() {
        listPath.clear()
        if (listMovieOff.size > 0) {
            listPath = listMovieOff
        }
    }


    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)

        try {
            if (listPath.isNotEmpty()) {
                bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load("${MainApp.IMAGE_BASE_URL}${listPath[position]}")
                    .apply(RequestOptions().fitCenter())
                    .submit()
                    .get()
                rv.setImageViewBitmap(R.id.imageView, bitmap)
            } else {
                rv.setImageViewBitmap(R.id.imageView, mWidgetItems[0])
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        val extras = Bundle()
        extras.putInt(ImagesBannerWidget.EXTRA_ITEM, position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getCount(): Int {
        return if (listPath.isEmpty()) {
            mWidgetItems.size
        } else {
            listPath.size
        }
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {

    }

}