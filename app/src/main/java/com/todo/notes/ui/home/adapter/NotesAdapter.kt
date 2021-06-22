package com.todo.notes.ui.home

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.todo.notes.R
import com.todo.notes.data.model.NoteDM
import com.todo.notes.databinding.NoteRowBinding
import com.todo.notes.ui.base.BaseLoaderAdapter
import com.todo.notes.ui.base.BaseViewHolder
import com.todo.notes.ui.home.details.DetailsActivity

open class NotesAdapter(
    val activity: Activity,
    val dataList: List<NoteDM>,
    paginationHandler: PaginationHandler
) : BaseLoaderAdapter<NoteRowBinding, NoteDM>(R.layout.note_row, paginationHandler) {

    override fun onHolderCreated(holder: BaseViewHolder<NoteRowBinding, NoteDM>?): RecyclerView.ViewHolder {
        holder?.binding?.root?.setOnClickListener { v ->
            DetailsActivity.start(
                activity,
                holder.binding?.dataModel
            )
        }
        return super.onHolderCreated(holder)
    }

}