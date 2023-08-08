package com.ugikpoenya.sampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ugikpoenya.appmanager.AdsManager
import com.ugikpoenya.sampleapp.databinding.ActivitySearchBinding


class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AdsManager().initBanner(this, binding.lyBannerAds)
        initComponent()
    }

    private fun initComponent() {
        binding.btBack.setOnClickListener {
            finish()
        }
    }
}