package com.ugikpoenya.sampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ugikpoenya.appmanager.ServerManager
import com.ugikpoenya.sampleapp.databinding.ActivityBottomNavigationBinding
import com.ugikpoenya.sampleapp.holder.ListViewHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


class BottomNavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBottomNavigationBinding
    val groupAdapter = GroupAdapter<GroupieViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponent();
    }

    private fun initComponent() {
        val listLayoutManager = LinearLayoutManager(this)
        listLayoutManager.orientation = RecyclerView.VERTICAL
        listLayoutManager.generateDefaultLayoutParams()
        val dividerItemDecoration = DividerItemDecoration(binding.recyclerView.context, listLayoutManager.orientation)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        binding.recyclerView.layoutManager = listLayoutManager
        binding.recyclerView.adapter = groupAdapter
        getData()

        binding.navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_recent -> {
                    binding.searchText.text = item.title
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_favorites -> {
                    binding.searchText.text = item.title
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_nearby -> {
                    binding.searchText.text = item.title
                    return@OnNavigationItemSelectedListener true
                }

                else -> false
            }
        })

        binding.swipeRefresh.setOnRefreshListener {
            getData()
        }
        binding.swipeRefresh.setProgressViewOffset(true, 100, 200)

        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY < oldScrollY) { // up
                animateNavigation(false)
                animateSearchBar(false)
            }
            if (scrollY > oldScrollY) { // down
                animateNavigation(true)
                animateSearchBar(true)
            }
        })
    }

    private fun getData() {
        binding.swipeRefresh.isRefreshing = true
        ServerManager().getPosts(this) {
            groupAdapter.clear()
            binding.swipeRefresh.isRefreshing = false
            it?.forEach { postModel ->
                groupAdapter.add(ListViewHolder(postModel, this))
            }
        }
    }

    var isNavigationHide = false
    private fun animateNavigation(hide: Boolean) {
        if (isNavigationHide && hide || !isNavigationHide && !hide) return
        isNavigationHide = hide
        val moveY = if (hide) 2 * binding.navigation.height else 0
        binding.navigation.animate().translationY(moveY.toFloat()).setStartDelay(100).setDuration(300).start()
    }

    var isSearchBarHide = false

    private fun animateSearchBar(hide: Boolean) {
        if (isSearchBarHide && hide || !isSearchBarHide && !hide) return
        isSearchBarHide = hide
        val moveY = if (hide) -(2 * binding.searchBar.height) else 0
        binding.searchBar.animate().translationY(moveY.toFloat()).setStartDelay(100).setDuration(300).start()
    }
}