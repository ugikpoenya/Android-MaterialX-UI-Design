package com.ugikpoenya.sampleapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.ugikpoenya.appmanager.AdsManager
import com.ugikpoenya.appmanager.ServerManager
import com.ugikpoenya.appmanager.holder.AdsViewHolder
import com.ugikpoenya.appmanager.model.PostModel
import com.ugikpoenya.materialx.ui.design.utils.Tools
import com.ugikpoenya.sampleapp.databinding.ActivityTabBinding
import com.ugikpoenya.sampleapp.holder.ListViewHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


class TabActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTabBinding
    val groupAdapter = GroupAdapter<GroupieViewHolder>()
    var post_model_array_list = ArrayList<PostModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AdsManager().initBanner(this, binding.lyBannerAds)
        initToolbar()
        initComponent()
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_menu)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = "List Basic"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Tools.setSystemBarColor(this)
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

        binding.swipeRefresh.setOnRefreshListener {
            getData()
        }


        var tab_1 = binding.tabLayout.newTab().setText("Tab 1")
        var tab_2 = binding.tabLayout.newTab().setText("Tab 2")
        var tab_3 = binding.tabLayout.newTab().setText("Tab 3")

        binding.tabLayout.addTab(tab_1)
        binding.tabLayout.addTab(tab_2)
        binding.tabLayout.addTab(tab_3)

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("LOG", "onTabSelected " + tab?.position + " /" + tab?.text.toString())

                groupAdapter.clear()
                getData()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                Log.d("LOG", "onTabUnselected " + tab?.text.toString())
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
//                Log.d("LOG", "onTabReselected " + tab?.text.toString())
            }
        })
    }

    var searchKeyword = ""
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val item = menu.findItem(R.id.action_search)
        val sv = item.actionView as SearchView
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchKeyword = newText
                showData()
                return true
            }
        })
        return true
    }

    private fun getData() {
        binding.swipeRefresh.isRefreshing = true
        ServerManager().getPosts(this) {
            if (it != null) {
                post_model_array_list = it
            }
            showData()
        }
    }

    private fun showData() {
        binding.swipeRefresh.isRefreshing = false
        groupAdapter.clear()
        var index = 0
        post_model_array_list
            .filter { ((it.post_title + " " + it.post_content).lowercase().contains(searchKeyword.lowercase())) }
            .forEach {
                groupAdapter.add(ListViewHolder(it, this))
                index++
                if ((index % 7) == 1) {
                    groupAdapter.add(AdsViewHolder(this, 0, "home"))
                }
            }
    }
}