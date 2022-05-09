package com.example.weatherapp.utility.permission

import android.app.ActivityManager
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import java.lang.ref.WeakReference

class PermissionManager<T: Fragment> constructor(private val weakReference: WeakReference<T>) {

    private val requiredPermissions = mutableListOf<Permission>()
    private var rationale: String? = null
    private var callback: (Boolean) -> Unit = {}
    private var detailedCallback: (Map<Permission, Boolean>) -> Unit = {}

    private val permissionCheck = weakReference.get()
        ?.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { grantResults ->
            sendResultAndCleanUp(grantResults)
        }

    // region Initialization of PermissionManager variables
    /**
     * Set the description value.
     */
    fun rationale(description: String): PermissionManager<T> {
        this.rationale = description
        return this
    }

    /**
     * Add the permissions required.
     */
    fun request(vararg permissions: Permission): PermissionManager<T> {
        requiredPermissions.addAll(permissions)
        return this
    }

    /**
     * Checks if the permission or permissions of the same group are approved.
     */
    fun checkPermission(callback: (Boolean) -> Unit) {
        this.callback = callback
        handlePermissionRequest()
    }

    /**
     * Checks if the different permissions asked for are approved.
     */
    fun checkDetailedPermission(callback: (Map<Permission, Boolean>) -> Unit) {
        this.detailedCallback = callback
        handlePermissionRequest()
    }
    // endregion

    // region Permission request
    /**
     * Handles the permissions request.
     */
    private fun handlePermissionRequest() {
        weakReference.get()?.let { baseFragment ->
            when {
                areAllPermissionsGranted(baseFragment.requireContext()) -> sendPositiveResult()
                shouldShowPermissionRationale(baseFragment) -> displayRationale(baseFragment)
                else -> requestPermissions()
            }
        }
    }

    /**
     * Checks if the permissions are all granted.
     */
    private fun areAllPermissionsGranted(context: Context): Boolean {
        return requiredPermissions.all { permission ->
            permission.isGranted(context)
        }
    }

    /**
     * Checks if a rationale should be displayed when asking for permissions.
     */
    private fun shouldShowPermissionRationale(baseFragment: Fragment): Boolean {
        return requiredPermissions.any { permission ->
            permission.requiresRationale(baseFragment)
        }
    }

    /**
     * All permissions are granted.
     */
    private fun sendPositiveResult() {
        sendResultAndCleanUp(getPermissionList().associate { it to true })
    }

    /**
     * Sets the result to callbacks and clean the PermissionManager variables.
     */
    private fun sendResultAndCleanUp(grantResults: Map<String, Boolean>) {
        callback(grantResults.all { it.value })
        detailedCallback(grantResults.mapKeys { Permission.from(it.key) })
        cleanUp()
    }

    /**
     * Display rationale for the permissions asked for if needed.
     */
    private fun displayRationale(fragment: Fragment) {
        AlertDialog.Builder(fragment.requireContext())
            .setTitle(fragment.getString(R.string.dialog_permission_title))
            .setMessage(
                rationale ?: fragment.getString(R.string.dialog_permission_default_message)
            )
            .setCancelable(false)
            .setPositiveButton(fragment.getString(R.string.dialog_permission_button_positive)) { _, _ ->
                requestPermissions()
            }
            .show()
    }

    /**
     * Request the permissions asked by user.
     */
    private fun requestPermissions() {
        permissionCheck?.launch(getPermissionList())
    }
    // endregion

    // region Private methods of PermissionManager class.
    /**
     * Clear all the permissions that user has approved.
     */
    private fun clearApplicationUserData() {
        weakReference.get()?.requireContext()?.let { context ->
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            manager.clearApplicationUserData()
        }
    }

    /**
     * Clear PermissionManager data for another use.
     */
    private fun cleanUp() {
        requiredPermissions.clear()
        rationale = null
        callback = {}
        detailedCallback = {}
    }

    /**
     * Transforms requiredPermissions into typedArray.
     */
    private fun getPermissionList(): Array<String> {
        return requiredPermissions.flatMap { permission ->
            permission.permissions.toList()
        }.toTypedArray()
    }

    /**
     * Checks if the permission is granted.
     */
    private fun hasPermission(context: Context, permission: String): Boolean =
        ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    // endregion

    // region Permission class extension
    /**
     * Check if the permissions required are granted.
     */
    private fun Permission.isGranted(context: Context): Boolean =
        permissions.all { hasPermission(context, it) }

    /**
     * Asks the user to accept the required permissions.
     */
    private fun Permission.requiresRationale(fragment: Fragment): Boolean =
        permissions.any { fragment.shouldShowRequestPermissionRationale(it) }
    // endregion
}