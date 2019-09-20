package com.example.moviecatalogue

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

class BottomNavigationActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_movie -> {
                changeFragment(MovieFragment(), MainApp.FRAGMENT_MOVIE_TAG)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_tv_show -> {
                changeFragment(TvShowFragment(), MainApp.FRAGMENT_TV_TAG)
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
        if (item?.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivityForResult(mIntent, MainApp.CHANGE_LANGUAGE_CODE)

        }
        return super.onOptionsItemSelected(item)
    }

//    override fun onSaveInstanceState(outState: Bundle?) {
//        super.onSaveInstanceState(outState)
//        if(supportFragmentManager.findFragmentByTag(MainApp.FRAGMENT_MOVIE_TAG) !=null){
//            supportFragmentManager.putFragment(
//                outState!!,MainApp.SAVE_INSTANCE_FRAGMENT,
//                supportFragmentManager.findFragmentByTag(MainApp.FRAGMENT_MOVIE_TAG)!!)
//        }else{
//            supportFragmentManager.putFragment(
//                outState!!,MainApp.SAVE_INSTANCE_FRAGMENT,
//                supportFragmentManager.findFragmentByTag(MainApp.FRAGMENT_MOVIE_TAG)!!)
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//        if(savedInstanceState != null){
//            if(supportFragmentManager.getFragment(savedInstanceState, MainApp.FRAGMENT_MOVIE_TAG) != null){
//                val mContent = supportFragmentManager.getFragment(savedInstanceState, MainApp.FRAGMENT_MOVIE_TAG)
//                changeFragment(mContent!!,MainApp.FRAGMENT_MOVIE_TAG)
//            }else{
//                val mContent = supportFragmentManager.getFragment(savedInstanceState, MainApp.FRAGMENT_TV_TAG)
//                changeFragment(mContent!!,MainApp.FRAGMENT_TV_TAG)
//            }
//        }else{
            initFragment()
//        }

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

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
}
