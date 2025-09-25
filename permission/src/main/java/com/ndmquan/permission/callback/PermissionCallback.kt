package com.ndmquan.permission.callback

import android.content.Context
import androidx.appcompat.app.AlertDialog

abstract class PermissionCallback {
    abstract fun onAllGranted(permission: List<String>)

    abstract fun onDenied(listGranted: List<String>, listDenied: List<String>)

    open fun onShouldOpenSetting(context: Context, openSettingAction: () -> Unit) {
        openPermissionRequireDialog(context, openSettingAction)
    }

    open fun onSettingResult(result: Boolean) {}


    private fun openPermissionRequireDialog(context: Context, onAction: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle("Notification")
            .setMessage("Your app need permission to work properly")
            .setPositiveButton("Go to setting") { _, _ -> onAction() }
            .setNegativeButton("Cancel", null)
            .show()
    }
}