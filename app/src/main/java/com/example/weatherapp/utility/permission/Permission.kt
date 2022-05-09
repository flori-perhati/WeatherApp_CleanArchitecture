package com.example.weatherapp.utility.permission

import android.Manifest.permission.*
import java.lang.IllegalArgumentException

sealed class Permission(vararg val permissions: String) {

    companion object {
        fun from(permission: String) = when (permission) {
            ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION -> Location
            else -> throw IllegalArgumentException("Unknown permission: $permission")
        }
    }

    // Grouped permissions
    object Location : Permission(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
}

