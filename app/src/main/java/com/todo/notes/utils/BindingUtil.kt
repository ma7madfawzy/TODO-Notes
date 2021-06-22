package com.todo.notes.utils

import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.textfield.TextInputLayout
import com.todo.notes.R
import com.todo.notes.utils.Extensions.afterTextChanged
import com.todo.notes.utils.Extensions.modulus
import com.todo.notes.utils.Extensions.setRoundedBackground
import com.todo.notes.utils.Extensions.showAlert
import com.todo.notes.utils.Extensions.snackError


object BindingUtil {
    @BindingAdapter("error")
    @JvmStatic
    fun error(view: View, error: Int) {
        if (error == 0) return
        when (view) {
            is EditText -> view.error =
                view.getContext().getString(error)
            is TextInputLayout -> {
                error(view, error)
            }
            else -> view.snackError(error)
        }
    }

    @BindingAdapter("error")
    @JvmStatic
    fun error(view: TextInputLayout, error: Int) {
        if (error == 0) return
        view.isErrorEnabled = true
        view.error = view.context.getString(error)
        if (view.tag != null && view.tag is TextWatcher) return  //watcher already set
        if (view.editText != null) view.editText!!.afterTextChanged {
            view.error = null
            view.isErrorEnabled = false
            view.tag = this
        }
    }

    @BindingAdapter("firstChar")
    @JvmStatic
    fun firstChar(view: TextView, text: String?) {
        text?.let {
            view.text = text.toCharArray()[0].toUpperCase().toString()
        }
    }

    @BindingAdapter("snack_int")
    @JvmStatic
    fun snackError(view: View, @StringRes message: Int) {
        if (message != 0)
            view.snackError(message)
    }

    @BindingAdapter("random_color_background")
    @JvmStatic
    fun randomColorBackground(view: View, position: Int?) {
        //get position based color index
        val rainbow = view.context.resources.getIntArray(com.todo.notes.R.array.rainbow)
        //@Int.modulus is an extension function located in Extensions object in utils help with the modulus operation
        val index = position?.modulus(rainbow.size)
        view.setRoundedBackground(rainbow[index!!])
    }

    @BindingAdapter("snackString")
    @JvmStatic
    fun snackString(view: View, s: String?) {
        s?.let { view.snackError(s) }
    }

    @BindingAdapter("toast")
    @JvmStatic
    fun toast(view: View, s: String?) {
        s?.let { Toast.makeText(view.context, s, Toast.LENGTH_LONG).show() }
    }

    @BindingAdapter("showAlert")
    @JvmStatic
    fun showAlert(view: View, message: String?) {
        message?.let {
            view.context.showAlert(
                view.context.getString(R.string.smth_went_wrong),
                message, null
            )
        }
    }

    @BindingAdapter("verticalLayoutManager")
    @JvmStatic
    fun verticalLayoutManager(view: RecyclerView, boolean: Boolean) {
        boolean.let {
            view.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        }
    }


    @BindingAdapter("toolbar_title")
    @JvmStatic
    fun toolbar_title(view: Toolbar, title: String?) {
        view.title = title ?: ""
    }

    @BindingAdapter("url_src")
    @JvmStatic
    fun url_src(view: ImageView, url: String?) {
        ImageLoader.loadImage(view, url)
    }

    @BindingAdapter("hasFixedSize")
    @JvmStatic
    fun hasFixedSize(view: RecyclerView, value: Boolean?) {
        value?.let { view.setHasFixedSize(it) }
    }

    @BindingAdapter("round_corners")
    @JvmStatic
    fun roundCorners(imageView: ShapeableImageView, round_corners: Boolean) {
        if (!round_corners) return
        val radius = imageView.context.resources.getDimension(R.dimen.default_corner_radius)
        imageView.shapeAppearanceModel = imageView.shapeAppearanceModel
            .toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, radius)
            .build()

    }

    @BindingAdapter("visibility")
    @JvmStatic
    fun visibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("linksClickable")
    @JvmStatic
    fun linksClickable(view: TextView, text: String?) {
        text?.let {
            view.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    @BindingAdapter("pickDate_Time")
    @JvmStatic
    fun pickDate_Time(view: TextView, show: Boolean) {
        if (show) view
            .setOnClickListener { DatTimePickerHandler(view) }
    }

    @BindingAdapter("enableCollapsing")
    @JvmStatic
    fun enableCollapsing(collapsingToolbarLayout: CollapsingToolbarLayout, enabled: Boolean?) {
        enabled?.let {
            val lp = collapsingToolbarLayout.layoutParams as AppBarLayout.LayoutParams
            if (it) {
                lp.scrollFlags =
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
            } else {
                lp.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
            }
            collapsingToolbarLayout.layoutParams = lp
        }
    }
}