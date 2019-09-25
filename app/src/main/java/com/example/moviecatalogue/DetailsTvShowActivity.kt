package com.example.moviecatalogue

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_tv_show.*

class DetailsTvShowActivity : AppCompatActivity() {

    private val main = MainApp()

    private lateinit var tvShow: TvShow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_tv_show)
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

//        if (intent.getStringExtra(MainApp.M_OR_T).toString() != "null") {
        tvShow = intent.getParcelableExtra(MainApp.TV_SHOW)
        getTvShowDetails(tvShow.id)

        text_tittle.text = tvShow.name
        val date = tvShow.firstAirDate
        val year = date?.substring(0, 4)
        text_year.text = year
        text_overview_detail.text = tvShow.overview
        text_rating.text = tvShow.voteAverage.toString()
        when (tvShow.voteAverage) {
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
        Picasso.get().load("${MainApp.IMAGE_BASE_URL}${tvShow.posterPath}")
            .placeholder(R.drawable.ic_backgroud_placeholder).into(image_detail)


    }

    private fun getTvShowDetails(tvId: Int) {
        main.getDetailTvShow(tvId, this,{

            //            genre
            val listGenre = it.genres
            var genre = ""
            for (i in listGenre) {
                genre += i.name + ", "
            }
            text_genre_list.text = genre.dropLast(2)


//            spoken language
            text_languages.text = it.languages.toString()
                .replace("[", "")
                .replace("]", "")
                .replace("\"","")


//            production companies
            val listProductionCompanies = it.productionCompanies
            var productionCompanies = ""
            for (i in listProductionCompanies) {
                productionCompanies += i.name + ", "
            }
            text_production_companies.text = productionCompanies.dropLast(2)

            if(pb_detail !=null){
                pb_detail.visibility = View.GONE
            }
        },{
            toast(this,it)
        })
    }
}
