package com.example.weatherapp.utility.extension

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible

/**
 * Shows the view.
 */
fun View.show() {
    this.let {
        if (!it.isVisible)
            it.isVisible = true
    }
}

/**
 * Hides the view.
 */
fun View.hide() {
    this.let {
        if (it.isVisible)
            it.isVisible = false
    }
}

/**
 * Makes the view visibility VISIBLE.
 */
fun View.visible() {
    this.let {
        if (it.visibility == View.INVISIBLE || it.visibility == View.GONE)
            it.visibility = View.VISIBLE
    }
}

/**
 * Makes the view visibility INVISIBLE.
 */
fun View.invisible() {
    this.let {
        if (it.visibility == View.VISIBLE || it.visibility == View.GONE)
            it.visibility = View.INVISIBLE
    }
}

/**
 * Makes the view visibility GONE.
 */
fun View.gone() {
    this.let {
        if (it.visibility == View.VISIBLE || it.visibility == View.GONE)
            it.visibility = View.GONE
    }
}

/**
 * Hides the keyboard.
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Shows the keyboard.
 */
fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}


fun View.modifyDrawable(borderWidth: Int? = null, borderColor: Int? = null, cornerRadius: Int? = null, bgColor: Int? = null) {
    val drawable = GradientDrawable()

    bgColor?.let {
        val color = this.context.getColor(it)
        drawable.setColor(color)
    }

    cornerRadius?.let {
        val radius = it.dpToPx().toFloat()
        drawable.cornerRadius = radius
    }

    borderWidth?.let { bWidth ->
        borderColor?.let { borderColor ->
            val color = this.context.getColor(borderColor)
            drawable.setStroke(bWidth, color)
        }
    }

    this.background = drawable
}