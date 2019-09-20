package com.example.moviecatalogue


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.*
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
//        if(savedInstanceState == null){
            initRecycler(mutableList)
            getTvShow()
//        }
        super.onViewCreated(view, savedInstanceState)
    }


//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putParcelableArrayList(MainApp.SAVE_INSTANCE_LIST_TV,ArrayList(mutableList))
//
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        if(savedInstanceState != null){
//            val savedMutableList = savedInstanceState.getParcelableArrayList<TvShow>(MainApp.SAVE_INSTANCE_LIST_TV).toMutableList()
//            mutableList.clear()
//            mutableList.addAll(savedMutableList)
//
//            initRecycler(savedMutableList)
//            tvAdapter.notifyDataSetChanged()
//        }
    }

    private fun getTvShow() {
        mutableList.clear()

        if (pb_main != null) {
            pb_main.visibility = View.VISIBLE
        }
        main.getTvShow(activity!!,{
            if (pb_main != null) {
                pb_main.visibility = View.GONE
            }
            mutableList.addAll(it)
            tvAdapter.notifyDataSetChanged()
        }, {
            toast(activity!!, it)
        })
    }

    private fun initRecycler(list:MutableList<TvShow>) {
        tvAdapter = TvShowAdapter(list) {
            val intent = Intent(context, DetailsTvShowActivity::class.java)
            intent.putExtra(MainApp.TV_SHOW, it)
            startActivity(intent)
        }
        rv_main.adapter = tvAdapter
        rv_main.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}