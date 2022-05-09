package com.example.weatherapp.utility.extension

import android.content.res.Resources
import kotlin.math.roundToInt

/**
 * Convert dp to px based on android device density.
 */
fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).roundToInt()
}

/**
 * Convert px to dp based on android device density.
 */
fun Int.pxToDp(): Float {
    return (this / Resources.getSystem().displayMetrics.density)
}