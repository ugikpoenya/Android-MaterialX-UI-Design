package com.ugikpoenya.sampleapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.GONE
import android.widget.TextView.OnEditorActionListener
import android.widget.TextView.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ugikpoenya.appmanager.SearchManager
import com.ugikpoenya.appmanager.ServerManager
import com.ugikpoenya.appmanager.holder.AdsViewHolder
import com.ugikpoenya.materialx.ui.design.holder.SuggestionViewHolder
import com.ugikpoenya.materialx.ui.design.utils.Tools
import com.ugikpoenya.materialx.ui.design.utils.ViewAnimation
import com.ugikpoenya.sampleapp.databinding.ActivitySearchBinding
import com.ugikpoenya.sampleapp.holder.ListViewHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val suggestionAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        initComponent()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        Tools.setSystemBarColor(this, com.ugikpoenya.materialx.ui.design.R.color.grey_5);
        Tools.setSystemBarLight(this)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initComponent() {
        val recyclerSuggestionLayout = LinearLayoutManager(this)
        recyclerSuggestionLayout.orientation = RecyclerView.VERTICAL
        recyclerSuggestionLayout.generateDefaultLayoutParams()
        binding.recyclerSuggestion.addItemDecoration(DividerItemDecoration(this, recyclerSuggestionLayout.orientation))
        binding.recyclerSuggestion.layoutManager = recyclerSuggestionLayout
        binding.recyclerSuggestion.adapter = suggestionAdapter
        showSuggestionSearch();

        val listLayoutManager = LinearLayoutManager(this)
        listLayoutManager.orientation = RecyclerView.VERTICAL
        listLayoutManager.generateDefaultLayoutParams()
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, listLayoutManager.orientation))
        binding.recyclerView.layoutManager = listLayoutManager
        binding.recyclerView.adapter = groupAdapter

        suggestionAdapter.setOnItemClickListener { item, _ ->
            when (item) {
                is SuggestionViewHolder -> {
                    binding.etSearch.setText(item.title)
                    hideKeyboard()
                    getData()
                }
            }
        }

        binding.etSearch.setOnEditorActionListener(OnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                getData()
                return@OnEditorActionListener true
            }
            false
        })


        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                showSuggestionSearch()
            }
        })

        binding.btClear.setOnClickListener { binding.etSearch.setText("") }

        binding.etSearch.setOnTouchListener { view, motionEvent ->
            showSuggestionSearch()
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            false
        }
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun showSuggestionSearch() {
        suggestionAdapter.clear()
        var index = 0
        SearchManager().get(this)
            .filter { (it.lowercase().contains(binding.etSearch.text.toString().lowercase().trim())) }
            .forEach {
                if (index++ < 5) {
                    suggestionAdapter.add(SuggestionViewHolder(it, ::onDeleteItem))
                }
            }

        if (binding.lytSuggestion.visibility == GONE) {
            ViewAnimation.expand(binding.lytSuggestion)
        }
    }

    private fun onDeleteItem(title: String) {
        SearchManager().delete(this, title)
        showSuggestionSearch()
    }


    private fun getData() {
        binding.progressBar.visibility = VISIBLE
        binding.lytNoResult.visibility = GONE
        ViewAnimation.collapse(binding.lytSuggestion)
        groupAdapter.clear()

        val searchKeyword = binding.etSearch.text.toString().trim()
        SearchManager().add(this, searchKeyword)
        ServerManager().getPosts(this) { posts ->
            if (posts != null) {
                binding.progressBar.visibility = GONE


                var index = 0
                posts
                    .filter { ((it.post_title + " " + it.post_content).lowercase().contains(searchKeyword.lowercase())) }
                    .forEach {
                        groupAdapter.add(ListViewHolder(it, this))
                        index++
                        if ((index % 7) == 1) {
                            groupAdapter.add(AdsViewHolder(this, 0, "home"))
                        }
                    }

                if (groupAdapter.itemCount == 0) binding.lytNoResult.visibility = VISIBLE
            }
        }
    }
}