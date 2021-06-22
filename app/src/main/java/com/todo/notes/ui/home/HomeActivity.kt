package com.todo.notes.ui.home

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.todo.notes.R
import com.todo.notes.data.model.NoteDM
import com.todo.notes.databinding.ActivityHomeBinding
import com.todo.notes.ui.base.BaseActivity
import com.todo.notes.ui.home.adapter.NotesAdapter
import com.todo.notes.ui.home.add_note.AddNoteActivity
import com.todo.notes.utils.Extensions.configDebounce
import com.todo.notes.utils.Extensions.showAlert
import com.todo.notes.utils.SwipeToDeleteCallback
import com.todo.notes.utils.ThemeDialogHandler
import javax.inject.Inject


class HomeActivity : BaseActivity<HomeActivityViewModel, ActivityHomeBinding>() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    private lateinit var notesAdapter: NotesAdapter

    @Inject
    lateinit var themeHandlerDialog: ThemeDialogHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeHasNotes()
        observeNotesData()
    }

    override fun setupViews() {
        setSupportActionBar(binding.toolbar)
        configRecycler()
        configSearch()
        initThemeHandler()
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadData()
    }

    private fun observeNotesData() {
        viewModel.notesData.observe(this) { result ->
            if (result.isEmpty()) {
                startAddNoteActivity()
                finish()
            } else
                updateAdapter(result)
        }
    }

    private fun observeHasNotes() {
        viewModel.hasNotes.observe(this) { result ->
            if (!result) {
                startAddNoteActivity()
                finish()
            }
        }
    }

    private fun updateAdapter(result: List<NoteDM>) {
        notesAdapter.setDataList(result)
        binding.recycler.scheduleLayoutAnimation()
    }

    private fun startAddNoteActivity() {
        AddNoteActivity.start(this)
    }

    private fun configSearch() {
        binding.searchEt.configDebounce({
            notesAdapter.clearData()
            viewModel.onQueryChanged(it.toString())
        }, 700, lifecycleScope, true)
    }

    private fun configRecycler() {
        notesAdapter = NotesAdapter(this@HomeActivity)
        binding.recycler.adapter = notesAdapter
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onSwipeToDelete(viewHolder)
            }
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.recycler)
    }

    private fun onSwipeToDelete(viewHolder: RecyclerView.ViewHolder) {
        val msg = getString(R.string.sure_delete_note)
            .replace("*", notesAdapter.get(viewHolder.adapterPosition).title!!)
        showAlert(getString(R.string.delete), msg,
            DialogInterface.OnClickListener { dialog, which ->
                viewModel.deleteNote(notesAdapter.get(viewHolder.adapterPosition).id)
                notesAdapter.remove(viewHolder.adapterPosition)
            })
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
        startAddNoteActivity()
    }

}