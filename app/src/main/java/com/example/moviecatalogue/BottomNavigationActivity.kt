package com.example.moviecatalogue

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.moviecatalogue.database.MovieHelper
import com.example.moviecatalogue.database.TvShowHelper
import com.example.moviecatalogue.fragment.FavoriteFragment
import com.example.moviecatalogue.fragment.MovieFragment
import com.example.moviecatalogue.fragment.TvShowFragment


class BottomNavigationActivity : AppCompatActivity() {
    private lateinit var movieHelper: MovieHelper
    private lateinit var tvShowHelper: TvShowHelper

    companion object {
        lateinit var contextOfApplication: Context

        fun getContenxtApplication(): Context {
            return contextOfApplication
        }
    }


    private var menuItem: Menu? = null

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_movie -> {
                    setUpActionBar(true)
                    changeFragment(MovieFragment(), MainApp.FRAGMENT_MOVIE_TAG)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_tv_show -> {
                    setUpActionBar(true)
                    changeFragment(TvShowFragment(), MainApp.FRAGMENT_TV_TAG)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_favorite -> {
                    setUpActionBar(false)
                    changeFragment(FavoriteFragment(), MainApp.FRAGMENT_FAVORITE_TAG)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_change_settings -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivityForResult(mIntent, MainApp.CHANGE_LANGUAGE_CODE)
                return true
            }
            R.id.notification_settings -> {
                startActivity(Intent(this, NotificationSettingsActivity::class.java))
                return true
            }
            R.id.search -> {
                return when (fragmentState()) {
                    getString(R.string.movie) -> {
                        startActivity(Intent(this, SearchMovieActivity::class.java))
                        true
                    }
                    getString(R.string.tv_show) -> {
                        startActivity(Intent(this, SearchTvActivity::class.java))
                        true
                    }
                    else -> {
                        true
                    }
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val fragmentMovie = supportFragmentManager.findFragmentByTag(MainApp.FRAGMENT_MOVIE_TAG)
        val fragmentTvShow = supportFragmentManager.findFragmentByTag(MainApp.FRAGMENT_TV_TAG)
        val fragmentFavorite =
            supportFragmentManager.findFragmentByTag(MainApp.FRAGMENT_FAVORITE_TAG)
        when {
            supportFragmentManager.findFragmentByTag(MainApp.FRAGMENT_MOVIE_TAG) != null ->
                fragmentMovie?.let {
                    supportFragmentManager.putFragment(
                        outState, MainApp.FRAGMENT_MOVIE_TAG,
                        it
                    )
                }
            supportFragmentManager.findFragmentByTag(MainApp.FRAGMENT_TV_TAG) != null -> fragmentTvShow?.let {
                supportFragmentManager.putFragment(
                    outState, MainApp.FRAGMENT_TV_TAG,
                    it
                )
            }
            else -> fragmentFavorite?.let {
                supportFragmentManager.putFragment(
                    outState, MainApp.FRAGMENT_FAVORITE_TAG,
                    it
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        contextOfApplication = applicationContext

//        movieHelper = MovieHelper.getInstance(applicationContext)
//        movieHelper.open()
        tvShowHelper = TvShowHelper.getInstance(applicationContext)
        tvShowHelper.open()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        if (savedInstanceState != null) {
            when {
                supportFragmentManager.getFragment(
                    savedInstanceState,
                    MainApp.FRAGMENT_MOVIE_TAG
                ) != null -> {
                    val mContent = supportFragmentManager.getFragment(
                        savedInstanceState,
                        MainApp.FRAGMENT_MOVIE_TAG
                    )
                    mContent?.let { changeFragment(it, MainApp.FRAGMENT_MOVIE_TAG) }
                }
                supportFragmentManager.getFragment(
                    savedInstanceState,
                    MainApp.FRAGMENT_TV_TAG
                ) != null -> {
                    val mContent =
                        supportFragmentManager.getFragment(
                            savedInstanceState,
                            MainApp.FRAGMENT_TV_TAG
                        )
                    mContent?.let { changeFragment(it, MainApp.FRAGMENT_TV_TAG) }
                }
                else -> {
                    val mContent = supportFragmentManager.getFragment(
                        savedInstanceState,
                        MainApp.FRAGMENT_FAVORITE_TAG
                    )
                    mContent?.let { changeFragment(it, MainApp.FRAGMENT_FAVORITE_TAG) }
                }
            }

        } else {
            initFragment()
        }

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }

    private fun fragmentState(): String {
        return when {
            supportFragmentManager.findFragmentByTag(MainApp.FRAGMENT_MOVIE_TAG) != null -> getString(
                R.string.movie
            )
            supportFragmentManager.findFragmentByTag(MainApp.FRAGMENT_TV_TAG) != null -> getString(R.string.tv_show)
            else -> getString(R.string.favorite)
        }
    }

    private fun setUpActionBar(state: Boolean) {
        if (state) {
            menuItem?.getItem(0)?.setIcon(R.drawable.ic_search_white_24dp)
            supportActionBar?.elevation = 10f
        } else {
            menuItem?.getItem(0)?.setIcon(R.color.colorPrimaryDark)
            supportActionBar?.elevation = 0f
        }
    }

    private fun initFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frame_fragment, MovieFragment(), MainApp.FRAGMENT_MOVIE_TAG)
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainApp.CHANGE_LANGUAGE_CODE) {
            finish()
            startActivity(intent)
        }
    }

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_fragment, fragment, tag)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        movieHelper.close()
        tvShowHelper.close()
    }
}
