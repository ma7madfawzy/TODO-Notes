package com.todo.notes.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.snackbar.Snackbar
import com.todo.notes.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

object Extensions {
    /**
     * Extension function to simplify setting an afterTextChanged action to EditText components.
     */
    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    /**
     * Extension function to apply debounce on textChanges using Flow Coroutine
     */
    @ExperimentalCoroutinesApi
    @CheckResult
    fun EditText.textChanges(): Flow<CharSequence?> {
        return callbackFlow<CharSequence?> {
            val listener = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) = Unit
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    trySend(s)
                }
            }
            addTextChangedListener(listener)
            awaitClose { removeTextChangedListener(listener) }
        }.onStart { emit(text) }
    }
    fun Context.showAlert(
        title: String? = null,
        message: String?,
        positiveListener: DialogInterface.OnClickListener?) {
        message?.let {
            val builder = AlertDialog.Builder(this)
            builder.setMessage(message)
            builder.setTitle(title)
            builder.setCancelable(true)
            builder.setPositiveButton(getString(R.string.ok), positiveListener)
            builder.setNegativeButton(getString(R.string.cancel), null)
            val alert11 = builder.create()
            alert11.show()
        }
    }

    /**
     * Extension function to set the action required on each text change with debounce
     */
    @FlowPreview
    @ExperimentalCoroutinesApi
    fun EditText.configDebounce(
        onEach: (String) -> Any,
        duration: Long = 700,
        scope: CoroutineScope,
        ignoreFirst: Boolean = false
    ) {
        var ignore = ignoreFirst
        textChanges().debounce(duration)
            .onEach {
                if (ignore)
                    ignore = false
                else
                    onEach.invoke(it.toString())
            }
            .launchIn(scope)
    }

    infix fun Int.modulus(other: Int) = ((this % other) + other) % other
    fun View.setRoundedBackground(@ColorInt color: Int) {
        addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View?,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {

                val shape = GradientDrawable()
                shape.cornerRadius = measuredHeight / 2f
                shape.setColor(color)

                background = shape

                removeOnLayoutChangeListener(this)
            }
        })
    }

    /**
     * Extension function to simplify showing a snackBar using the View.
     */
    fun View.snackError(@StringRes res: Int) {
        snackError(this.context.getString(res))
    }

    fun View.snackError(errorString: String?) {
        errorString?.let { Snackbar.make(this, it, Snackbar.LENGTH_SHORT).show() }
    }

    fun Activity.startActivity(viewToAnimate: View, cls: Class<*>, extras: Bundle? = null) {
        val intent = Intent(this, cls)
        extras?.let { intent.putExtras(it) }
        val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this, viewToAnimate, this.getString(R.string.transition)
        )
        ActivityCompat.startActivity(this, intent, options.toBundle())
    }

    fun Activity.startActivity(cls: Class<*>, extras: Bundle? = null, finish: Boolean = false) {
        val intent = Intent(this, cls)
        extras?.let { intent.putExtras(it) }
        this.startActivity(intent)
        if (finish)
            this.finish()
    }
}