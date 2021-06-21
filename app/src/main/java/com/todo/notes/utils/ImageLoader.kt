package com.todo.notes.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.todo.notes.R

object ImageLoader {
    fun loadImage(view: ImageView, url: String?) {
        Picasso.get().load(url).placeholder(R.drawable.ic_place_holder)
            .into(view)
    }

}