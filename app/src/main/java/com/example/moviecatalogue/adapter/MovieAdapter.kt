package com.example.moviecatalogue.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviecatalogue.MainApp
import com.example.moviecatalogue.R
import com.example.moviecatalogue.model.Movie
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*
import java.util.*

class MovieAdapter(
    private var listener: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MainViewHolder>() {

    var listMovies = ArrayList<Movie>()
        set(listMovies) {
            if (listMovies.size > 0) {
                this.listMovies.clear()
            }
            this.listMovies.addAll(listMovies)

            notifyDataSetChanged()
        }

    fun addItem(movie: Movie) {
        this.listMovies.add(movie)
        notifyItemInserted(this.listMovies.size - 1)
    }

    fun updateItem(position: Int, movie: Movie) {
        this.listMovies[position] = movie
        notifyItemChanged(position, movie)
    }

    fun removeItem(position: Int) {
        this.listMovies.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listMovies.size)
    }

    fun setData(items: MutableList<Movie>) {
        listMovies.clear()
        listMovies.addAll(items)
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
        return listMovies.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, potition: Int) {
        holder.bindItem(listMovies[potition], listener)
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(item: Movie, listener: (Movie) -> Unit) {
            itemView.text_tittle.text = item.title
            itemView.text_date.text = item.releaseDate
            itemView.text_description.text = item.overview
            itemView.text_rating.text = item.voteAverage.toString()
            when (item.voteAverage) {
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