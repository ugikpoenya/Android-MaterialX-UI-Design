package com.ugikpoenya.sampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ugikpoenya.materialx.ui.design.utils.Tools
import com.ugikpoenya.sampleapp.databinding.ActivityDarkThemeBinding


class DarkThemeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDarkThemeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDarkThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_menu)
        setSupportActionBar( binding.toolbar)
        supportActionBar!!.title = "Toolbar"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Tools.setSystemBarColor(this, R.color.grey_900)
    }
}