package com.todo.notes.ui.home.details

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.todo.notes.R
import com.todo.notes.data.model.NoteDM
import com.todo.notes.databinding.ActivityNoteDetailsBinding
import com.todo.notes.ui.base.BaseActivity
import com.todo.notes.utils.Extensions.startActivity


class DetailsActivity : BaseActivity<DetailsViewModel, ActivityNoteDetailsBinding>() {

    companion object {
        fun start(activity: Activity, view: View, noteDm: NoteDM?) {
            val extras = Bundle()
            extras.putParcelable("dataModel", noteDm)
            activity.startActivity(view, DetailsActivity::class.java, extras)
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

    override fun getLayoutRes() = R.layout.activity_note_details
    override fun getViewModelClass() = DetailsViewModel::class.java
    override fun getViewBinding() = ActivityNoteDetailsBinding.inflate(layoutInflater)

}

