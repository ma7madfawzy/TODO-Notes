package com.todo.notes.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contacts.ui.home.add_contact.AddNoteActivity
import com.todo.notes.R
import com.todo.notes.databinding.ActivityHomeBinding
import com.todo.notes.ui.base.BaseActivity
import com.todo.notes.utils.Extensions.configDebounce
import com.todo.notes.utils.Extensions.startActivity
import com.todo.notes.utils.ThemeDialogHandler
import javax.inject.Inject


class HomeActivity : BaseActivity<HomeActivityViewModel, ActivityHomeBinding>() {


    private lateinit var notesAdapter: NotesAdapter

    @Inject
    lateinit var themeHandlerDialog: ThemeDialogHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeData()
        if (binding.searchEt.text.isNullOrEmpty())
            viewModel.loadData()
    }

    override fun setupViews() {
        setSupportActionBar(binding.toolbar)
        configRecycler()
        configSearch()
        initThemeHandler()
    }


    private fun observeData() {
        viewModel.newsResult.observe(this) { result ->
            val oldSize = notesAdapter.itemCount
            notesAdapter.updateDataList(result ?: emptyList())
            if (oldSize == notesAdapter.itemCount)//if adapter was empty
                binding.recycler.scheduleLayoutAnimation()
        }
    }

    private fun configSearch() {
        binding.searchEt.configDebounce({
            notesAdapter.clearData()
            viewModel.onQueryChanged(it.toString())
        }, 700, lifecycleScope)
    }

    private fun configRecycler() {
        binding.recycler.apply {
            layoutManager =
                LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false)
            notesAdapter = NotesAdapter(this@HomeActivity, ArrayList(), viewModel)
            adapter = notesAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_theme_changer -> {
                themeHandlerDialog.showThemesPickerDialog(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initThemeHandler() {
        themeHandlerDialog.delegate = delegate
    }

    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)

    override fun getLayoutRes() = R.layout.activity_home

    override fun getViewModelClass() = HomeActivityViewModel::class.java

    fun onAddNoteClicked(view: View) {
        startActivity(AddNoteActivity::class.java)
    }

}