package com.example.moviecatalogue.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviecatalogue.MainApp
import com.example.moviecatalogue.R
import com.example.moviecatalogue.model.TvShow
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*

class TvShowAdapter(
    private var listener: (TvShow) -> Unit
) : RecyclerView.Adapter<TvShowAdapter.MainViewHolder>() {

    var listTvShows = ArrayList<TvShow>()
        set(listTvShows) {
            if (listTvShows.size > 0) {
                this.listTvShows.clear()
            }
            this.listTvShows.addAll(listTvShows)

            notifyDataSetChanged()
        }

    fun addItem(tvShow: TvShow) {
        this.listTvShows.add(tvShow)
        notifyItemInserted(this.listTvShows.size - 1)
    }

    fun updateItem(position: Int, tvShow: TvShow) {
        this.listTvShows[position] = tvShow
        notifyItemChanged(position, tvShow)
    }

    fun removeItem(position: Int) {
        this.listTvShows.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listTvShows.size)
    }

    fun setData(items: MutableList<TvShow>) {
        listTvShows.clear()
        listTvShows.addAll(items)
//        mutableList.clear()
//        mutableList.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(group: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(group.context).inflate(R.layout.item_movie, group, false)
        )
    }

    override fun getItemCount(): Int {
        return listTvShows.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, potition: Int) {
        holder.bindItem(listTvShows[potition], listener)
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(item: TvShow, listener: (TvShow) -> Unit) {
            itemView.text_tittle.text = item.name
            itemView.text_date.text = item.firstAirDate
            itemView.text_description.text = item.overview
            itemView.text_rating.text = item.voteAverage.toString()
            when (item.voteAverage.toInt()) {
                in 0..3 -> itemView.text_rating.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.colorRed
                    )
                )
                in 4..6 -> itemView.text_rating.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.colorYellow
                    )
                )
                in 7..10 -> itemView.text_rating.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.colorGreen
                    )
                )
            }

            Picasso.get().load("${MainApp.IMAGE_BASE_URL}${item.posterPath}")
                .error(R.color.colorBlack)
                .placeholder(R.drawable.ic_backgroud_placeholder)
                .into(itemView.image_movie, object : Callback {
                    override fun onSuccess() {
                        itemView.pb_item_main.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        itemView.pb_item_main.visibility = View.GONE
                    }
                })

            itemView.setOnClickListener {
                listener(item)
            }
        }
    }
}