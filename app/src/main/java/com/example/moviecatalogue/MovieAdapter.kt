package com.example.moviecatalogue

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(
    private var mutableList: MutableList<Movie>,
    private var listener: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(group: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(group.context).inflate(R.layout.item_movie, group, false)
        )
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, potition: Int) {
        holder.bindItem(mutableList[potition], listener)
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(item: Movie, listener: (Movie) -> Unit) {
            itemView.text_tittle.text = item.title
            itemView.text_date.text = item.releaseDate
            itemView.text_description.text = item.overview
            itemView.text_rating.text = item.voteAverage.toString()
            when (item.voteAverage) {
                in 0..3 -> itemView.text_rating.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorRed))
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