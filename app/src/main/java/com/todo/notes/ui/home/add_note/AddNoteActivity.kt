package com.example.contacts.ui.home.add_contact

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.databinding.Observable
import com.todo.notes.R
import com.todo.notes.databinding.ActivityAddNoteBinding
import com.todo.notes.ui.base.BaseActivity

class AddNoteActivity : BaseActivity<AddNoteViewModel, ActivityAddNoteBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configViewModel()
    }

    override fun setupViews() {
        initToolbar()
        configActionDone()
    }

    private fun configViewModel() {
        observeResult()
    }

    private fun observeResult() {
//        viewModel.saveNoteResult
//            .addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
//                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
//                    if (viewModel.saveNoteResult.get())
//                        onNoteCreated()
//                }
//            })
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarLayout.title = title
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun configActionDone() {
        binding.phoneEt.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE ->
                    viewModel.onSaveNote()
            }
            false
        }
    }

    private fun onNoteCreated() {
        //setting result code with RESULT_OK ells the starter there was a change in data so refresh is required
        setResult(RESULT_OK)
        finish()
        Toast.makeText(
            applicationContext, getString(R.string.note_added_successfully)
                .replace("*", viewModel.model.title.toString()), Toast.LENGTH_LONG
        ).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_create_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                viewModel.onSaveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun getViewBinding() = ActivityAddNoteBinding.inflate(layoutInflater)

    override fun getLayoutRes() = R.layout.activity_add_note

    override fun getViewModelClass() = AddNoteViewModel::class.java

}