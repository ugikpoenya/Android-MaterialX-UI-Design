package com.ugikpoenya.sampleapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ugikpoenya.appmanager.ServerManager
import com.ugikpoenya.appmanager.holder.AdsViewHolder
import com.ugikpoenya.sampleapp.databinding.FragmentContentBinding
import com.ugikpoenya.sampleapp.holder.ListViewHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


class ContentFragment : Fragment() {
    private var _binding: FragmentContentBinding? = null
    private val binding get() = _binding!!

    val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentBinding.inflate(inflater, container, false)
        val view = binding.root
        val tab = requireArguments().getString("tab")
        Log.d("LOG", "TAB $tab")

        val listLayoutManager = LinearLayoutManager(context)
        listLayoutManager.orientation = RecyclerView.VERTICAL
        listLayoutManager.generateDefaultLayoutParams()
        val dividerItemDecoration = DividerItemDecoration(context, listLayoutManager.orientation)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        binding.recyclerView.layoutManager = listLayoutManager
        binding.recyclerView.adapter = groupAdapter
        getData()

        binding.swipeRefresh.setOnRefreshListener {
            getData()
        }
        return view
    }

    private fun getData() {
        binding.swipeRefresh.isRefreshing = true
        context?.let { it ->
            ServerManager().getPosts(it) {
                binding.swipeRefresh.isRefreshing = false
                var index = 0
                it?.forEach { post ->
                    groupAdapter.add(ListViewHolder(post, context as Activity))
                    index++
                    if ((index % 7) == 1) {
                        groupAdapter.add(AdsViewHolder(context as Activity, 0, "home"))
                    }
                }
            }
        }
    }
}