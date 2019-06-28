package com.example.moviecatalogue


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_tv_show.*


class TvShowFragment : Fragment() {

    private val main = MainApp()
    private var mutableList = mutableListOf<TvShow>()
    private lateinit var tvAdapter: TvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycler()
        getTvShow()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getTvShow() {
        mutableList.clear()
        main.getTvShow({
            if (pb_main != null) {
                pb_main.visibility = View.GONE
            }
            mutableList.addAll(it)
            tvAdapter.notifyDataSetChanged()
        }, {
            toast(activity!!, it)
        })
    }

    private fun initRecycler() {
        tvAdapter = TvShowAdapter(mutableList) {
            val intent = Intent(context, DetailsTvShowActivity::class.java)
            intent.putExtra(MainApp.TV_SHOW, it)
            startActivity(intent)
        }
        rv_main.adapter = tvAdapter
        rv_main.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }
}
