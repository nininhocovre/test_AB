package com.ab.exerciseabinbev.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ab.exerciseabinbev.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerViewAdapter: OrderRecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.list).apply {
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = LinearLayoutManager(context)
        }
        return view;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        recyclerViewAdapter = OrderRecyclerViewAdapter(viewModel)
        recyclerView.adapter = recyclerViewAdapter
        viewModel.lastOrder.observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                recyclerViewAdapter.setOrder(it)
            }
        })
    }

}