package com.todo.notes.ui.home.adapter

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.todo.notes.data.model.NoteDM
import com.todo.notes.databinding.NoteRowBinding
import com.todo.notes.ui.home.details.DetailsActivity

class ViewHolder(val activity: Activity, private val itemBinding: NoteRowBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    init {
        configClickListeners()
    }

    private fun configClickListeners() {
        itemBinding.root.setOnClickListener { v ->
            DetailsActivity.start(
                activity,
                v,
                itemBinding.dataModel
            )
        }
    }


    fun bind(dataModel: NoteDM, position: Int) {
        itemBinding.dataModel = dataModel
        itemBinding.position = position
    }
}