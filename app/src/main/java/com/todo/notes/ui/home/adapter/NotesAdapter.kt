package com.todo.notes.ui.home.adapter

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.todo.notes.R
import com.todo.notes.data.model.NoteDM
import com.todo.notes.databinding.NoteRowBinding
import com.todo.notes.ui.base.BaseAdapter
import com.todo.notes.ui.base.BaseViewHolder
import com.todo.notes.ui.home.details.DetailsActivity

open class NotesAdapter(private val activity: Activity) :
    BaseAdapter<NoteRowBinding, NoteDM>(R.layout.note_row) {

    override fun onHolderCreated(holder: BaseViewHolder<NoteRowBinding, NoteDM>?): RecyclerView.ViewHolder {
        holder?.binding?.root?.setOnClickListener { v ->
            DetailsActivity.start(activity, v, holder.binding?.dataModel)
        }
        return super.onHolderCreated(holder)
    }

}