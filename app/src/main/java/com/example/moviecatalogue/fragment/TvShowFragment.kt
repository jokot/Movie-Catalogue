package com.example.moviecatalogue.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviecatalogue.DetailTvShowActivity
import com.example.moviecatalogue.MainApp
import com.example.moviecatalogue.MainViewModel
import com.example.moviecatalogue.R
import com.example.moviecatalogue.adapter.TvShowAdapter
import com.example.moviecatalogue.ext.toast
import com.example.moviecatalogue.model.TvShow
import kotlinx.android.synthetic.main.fragment_tv_show.*


class TvShowFragment : Fragment() {
    private lateinit var tvAdapter: TvShowAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(MainApp.TV_SHOW, true)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecycler()
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.getTvShow().observe(this, getTvShow)

        if (savedInstanceState?.getBoolean(MainApp.TV_SHOW) != true) {
            showLoading(true)
            mainViewModel.setTvShow(requireActivity()) {
                showLoading(false)
                toast(requireActivity(), it)
            }
        }
    }

    private val getTvShow = Observer<MutableList<TvShow>> {
        if (it != null) {
            tvAdapter.setData(it)
            showLoading(false)
        }
    }

    private fun initRecycler() {
        tvAdapter = TvShowAdapter {
            val intent =
                Intent(context, DetailTvShowActivity::class.java)
            intent.putExtra(MainApp.TV_SHOW, it)
            startActivity(intent)
        }
        rv_main.adapter = tvAdapter
        rv_main.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pb_main.visibility = View.VISIBLE
        } else {
            pb_main.visibility = View.GONE
        }
    }

}