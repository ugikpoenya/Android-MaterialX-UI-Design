package com.ugikpoenya.sampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.ugikpoenya.appmanager.AdsManager
import com.ugikpoenya.materialx.ui.design.utils.Tools
import com.ugikpoenya.sampleapp.databinding.ActivityTabBinding


class TabActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTabBinding
    var tabList = ArrayList<String>()

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
        tabList.add("Home")
        tabList.add("Static")
        tabList.add("Animation")
        tabList.add("Hot")
        tabList.add("Downloaded")


        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addList(tabList)
        binding.viewPager.adapter = adapter
        binding.viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(binding.tabLayout))
        binding.viewPager.offscreenPageLimit = tabList.size

        tabList.forEach {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(it))
        }

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                supportActionBar!!.title = tab?.text.toString()
                if (tab != null) {
                    binding.viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                Log.d("LOG", "onTabUnselected " + tab?.text.toString())
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
//                Log.d("LOG", "onTabReselected " + tab?.text.toString())
            }
        })
    }

    private class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        var tabList = ArrayList<String>()
        override fun getItem(position: Int): Fragment {
            val bundle = Bundle()
            bundle.putString("tab", tabList[position])
            val contentFragment = ContentFragment()
            contentFragment.arguments = bundle
            return contentFragment
        }

        fun addList(tabList: ArrayList<String>) {
            this.tabList = tabList
        }

        override fun getCount(): Int {
            return tabList.size
        }
    }
}