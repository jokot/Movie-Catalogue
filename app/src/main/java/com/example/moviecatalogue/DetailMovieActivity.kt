package com.example.moviecatalogue

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {

    private val main = MainApp()

    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        supportActionBar?.run{
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        setUpLayout()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpLayout() {
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
        main.getDetailsMovie(movieId,this, {
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

            if(pb_detail !=null){
                pb_detail.visibility = View.GONE
            }
        }, {
            toast(this, it)
        })
    }
}
