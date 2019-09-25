package com.example.moviecatalogue

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.moviecatalogue.database.database
import com.example.moviecatalogue.ext.toast
import com.example.moviecatalogue.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.sql.SQLClientInfoException

class DetailMovieActivity : AppCompatActivity() {

    private val main = MainApp()

    private lateinit var movie: Movie
    private var isFavorite = false
    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        setUpLayout()
        favoriteState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        menuItem = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.favorite -> {
                if (isFavorite) removeFavorite() else addFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(Movie.TABLE_FAVORITE_MOVIE)
                .whereArgs(
                    "(${Movie.MOVIE_ID} = {id})",
                    "id" to movie.id
                )
            val favorite = result.parseList(classParser<Movie>())
            if (favorite.isNotEmpty()) isFavorite = true

            setFavorite()
        }
    }

    private fun addFavorite() {

        try {
            database.use {
                insert(
                    Movie.TABLE_FAVORITE_MOVIE,
                    Movie.BACKDROP_PATH to movie.backdropPath,
                    Movie.MOVIE_ID to movie.id,
                    Movie.OVERVIEW to movie.overview,
                    Movie.POSTER_PATH to movie.posterPath,
                    Movie.RELEAS_DATE to movie.releaseDate,
                    Movie.TITLE to movie.title,
                    Movie.VOTE_VARAGE to movie.voteAverage
                )
            }
            "Added Favorite".toast(this)
        } catch (e: SQLClientInfoException) {
            e.localizedMessage.toast(this)
        }
    }

    private fun removeFavorite() {
        try {
            database.use {
                delete(Movie.TABLE_FAVORITE_MOVIE, "(MOVIE_ID = {id})", "id" to movie.id)
            }
            "Remove Favorite".toast(this)
        } catch (e: SQLiteConstraintException) {
            e.localizedMessage.toast(this)
        }

    }

    private fun setUpLayout() {
        supportActionBar?.setTitle(R.string.movie)
        movie = intent.getParcelableExtra(MainApp.MOVIE)
        getMovieDetails(movie.id)
        text_tittle.text = movie.title
        val date = movie.releaseDate
        val year = date?.substring(0, 4)
        text_year.text = year
        text_overview_detail.text = movie.overview
        text_rating.text = movie.voteAverage.toString()
        when (movie.voteAverage) {
            in 0..3 -> text_rating.setTextColor(ContextCompat.getColor(this, R.color.colorRed))
            in 4..6 -> text_rating.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorYellow
                )
            )
            in 7..10 -> text_rating.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorGreen
                )
            )
        }
        Picasso.get().load("${MainApp.IMAGE_BASE_URL}${movie.posterPath}")
            .placeholder(R.drawable.ic_backgroud_placeholder).into(image_detail)


    }

    @SuppressLint("SetTextI18n")
    private fun getMovieDetails(movieId: Int) {
        main.getDetailsMovie(movieId, this, {
            text_budget.text = "$" + "${it.budget}"

//            genre
            val listGenre = it.genres
            var genre = ""
            for (i in listGenre) {
                genre += i.name + ", "
            }
            text_genre_list.text = genre.dropLast(2)


//            spoken language
            val listSpokenLanguage = it.spokenLanguages
            var spokenLanguage = ""
            for (i in listSpokenLanguage) {
                spokenLanguage += i.name + ", "
            }
            text_languages.text = spokenLanguage.dropLast(2)


//            production companies
            val listProductionCompanies = it.productionCompanies
            var productionCompanies = ""
            for (i in listProductionCompanies) {
                productionCompanies += i.name + ", "
            }
            text_production_companies.text = productionCompanies.dropLast(2)


//            production countries
            val listProductionCountries = it.productionCountries
            var productionCountries = ""
            for (i in listProductionCountries) {
                productionCountries += i.name + ", "
            }
            text_production_countries.text = productionCountries.dropLast(2)

            if (pb_detail != null) {
                pb_detail.visibility = View.GONE
            }
        }, {
            toast(this, it)
        })
    }

    private fun setFavorite() {
        if (isFavorite) {
            menuItem?.getItem(0)?.setIcon(R.drawable.ic_favorite_white_24dp)
        } else {
            menuItem?.getItem(0)?.setIcon(R.drawable.ic_favorite_border_white_24dp)
        }
    }
}
