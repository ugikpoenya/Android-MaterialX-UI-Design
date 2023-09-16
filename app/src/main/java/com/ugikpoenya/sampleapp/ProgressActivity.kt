package com.ugikpoenya.sampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ugikpoenya.materialx.ui.design.utils.Tools
import com.ugikpoenya.sampleapp.databinding.ActivityProgressBinding

class ProgressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()

        Tools.displayImageOriginal(this, binding.imageView, "https://images.pexels.com/photos/3732667/pexels-photo-3732667.jpeg")
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_menu)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = "Progress Activity"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Tools.setSystemBarColor(this)
    }
}