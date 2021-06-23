package com.todo.notes.ui.home.add_note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import com.todo.notes.R
import com.todo.notes.data.model.NoteDM
import com.todo.notes.databinding.ActivityAddNoteBinding
import com.todo.notes.ui.base.BaseActivity
import com.todo.notes.ui.home.HomeActivity
import com.todo.notes.ui.home.details.DetailsViewModel
import com.todo.notes.utils.AlarmManagerUtil


class AddNoteActivity : BaseActivity<AddNoteViewModel, ActivityAddNoteBinding>() {
    companion object {

        fun start(activity: Context, dataModel: NoteDM? = null) {
            val intent = Intent(activity, AddNoteActivity::class.java)
            dataModel?.let {//case Update
                val bundle = Bundle()
                bundle.putParcelable(DetailsViewModel.DATA_MODEL, dataModel)
                intent.putExtras(bundle)
            }
            activity.startActivity(intent)
        }
    }

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
        viewModel.readExtras(intent.extras)
    }

    private fun observeResult() {
        viewModel.saveNoteResult.observe(this, { onNoteCreated(viewModel.model.hasReminder()) })
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun configActionDone() {
        binding.descEt.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE ->
                    viewModel.onSaveNote()
            }
            false
        }
    }

    private fun onNoteCreated(hasReminder: Boolean) {
        //setting result code with RESULT_OK ells the starter there was a change in data so refresh is required
        handleReminder(hasReminder)
        HomeActivity.start(this)
    }

    private fun handleReminder(hasReminder: Boolean) {
        hasReminder.let {
            AlarmManagerUtil.scheduleEvent(
                applicationContext,
                viewModel.model.getNoteDM()
            )
        }
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