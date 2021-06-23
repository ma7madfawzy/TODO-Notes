package com.todo.notes.ui.home.details

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityCompat
import com.todo.notes.R
import com.todo.notes.data.model.NoteDM
import com.todo.notes.databinding.ActivityNoteDetailsBinding
import com.todo.notes.ui.base.BaseActivity
import com.todo.notes.ui.home.HomeActivity
import com.todo.notes.ui.home.add_note.AddNoteActivity
import com.todo.notes.utils.Extensions.showAlert
import com.todo.notes.utils.Extensions.startActivity


class DetailsActivity : BaseActivity<DetailsViewModel, ActivityNoteDetailsBinding>() {

    companion object {
        fun start(activity: Activity, v: View, dataModel: NoteDM?) {
            val bundle = Bundle()
            bundle.putParcelable(DetailsViewModel.DATA_MODEL, dataModel)
            activity.startActivity(v, DetailsActivity::class.java, bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configViewModel()
    }


    override fun setupViews() {
        initToolbar()
    }

    private fun configViewModel() {
        viewModel.readExtras(intent.extras)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        ActivityCompat.finishAfterTransition(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                val msg = getString(R.string.sure_delete_note)
                    .replace("*", viewModel.model.dataModel?.title!!)
                showAlert(getString(R.string.delete), msg,
                    DialogInterface.OnClickListener { dialog, which ->
                        viewModel.deleteNote()
                        HomeActivity.start(this)
                    })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun getLayoutRes() = R.layout.activity_note_details
    override fun getViewModelClass() = DetailsViewModel::class.java
    override fun getViewBinding() = ActivityNoteDetailsBinding.inflate(layoutInflater)
    fun onEditNoteClicked(view: View) {
        AddNoteActivity.start(this, viewModel.model.dataModel)
    }

}

