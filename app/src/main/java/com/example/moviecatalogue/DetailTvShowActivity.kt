package com.example.moviecatalogue

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.moviecatalogue.ext.toast
import com.example.moviecatalogue.model.TvShow
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_tv_show.*

class DetailTvShowActivity : AppCompatActivity() {

    private val main = MainApp()

    private lateinit var tvShow: TvShow
    private var isFavorite = false
    private var menuItem : Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_tv_show)
        supportActionBar?.run{
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        setUpLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu,menu)
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
            R.id.favorite ->{
                if(isFavorite) removeFavorite() else addFavorite()

                isFavorite = !isFavorite

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addFavorite(){
        "Add Favorite".toast(this)
    }

    private fun removeFavorite(){
        "Remove Favorite".toast(this)
    }

    private fun setUpLayout() {
        supportActionBar?.setTitle(R.string.tv_show)

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
            toast(this, it)
        })
    }

    private fun setFavorite(){
        if(isFavorite){
            menuItem?.getItem(0)?.setIcon(R.drawable.ic_favorite_white_24dp)
        }else{
            menuItem?.getItem(0)?.setIcon(R.drawable.ic_favorite_border_white_24dp)
        }
    }
}
