package com.example.weatherapp.utility.extension

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.example.weatherapp.R

/**
 * Displays a short toast using CharSequence as e message.
 */
fun Context.shortToast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

/**
 * Displays a short toast using @StringRes as e message.
 */
fun Context.shortToast(@StringRes resId: Int) =
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()

/**
 * Displays a long toast using CharSequence as e message.
 */
fun Context.longToast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

/**
 * Displays a long toast using @StringRes as e message.
 */
fun Context.longToast(@StringRes resId: Int) =
    Toast.makeText(this, resId, Toast.LENGTH_LONG).show()

/**
 * Provides a progressDialog.
 */
fun Context.progressDialog(): Dialog {
    val dialog = Dialog(this)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.custom_progress_dialog)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    return dialog
}

/**
 * Check for internet connection.
 */
fun Context.isConnectedToInternet() : Boolean {
    val connectivityManager: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

    if (network == null)
        return false

    if (networkCapabilities == null)
        return false

    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
        return true

    return false
}

