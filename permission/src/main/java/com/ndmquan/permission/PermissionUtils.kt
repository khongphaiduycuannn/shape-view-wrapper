package com.ndmquan.permission

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.ndmquan.permission.callback.PermissionCallback
import com.ndmquan.permission.permissions.PermissionsGroup
import com.ndmquan.permission.utils.isPermissionGranted
import com.ndmquan.permission.utils.openSettingPermission
import com.ndmquan.permission.utils.shouldShowPermissionRationale
import java.util.UUID

class PermissionUtils(
    private val activity: AppCompatActivity
) {

    private var isWaitingForSettings = false
    private var currentCallback: PermissionCallback? = null
    private var currentPermissions: List<String> = emptyList()

    private val lifecycleCallback = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityResumed(resumedActivity: Activity) {
            if (resumedActivity == activity && isWaitingForSettings) {
                isWaitingForSettings = false
                val allGranted = isAllPermissionsGranted(currentPermissions)
                currentCallback?.onSettingResult(allGranted)
                activity.application.unregisterActivityLifecycleCallbacks(this)
            }
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
        override fun onActivityStarted(activity: Activity) {}
        override fun onActivityPaused(activity: Activity) {}
        override fun onActivityStopped(activity: Activity) {}
        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
        override fun onActivityDestroyed(activity: Activity) {}
    }


    fun requestPermission(
        permission: String,
        callback: PermissionCallback
    ) = requestPermissions(listOf(permission), callback)

    fun requestPermissions(
        permissions: PermissionsGroup,
        callback: PermissionCallback
    ) = requestPermissions(permissions.permissions, callback)

    fun requestPermissions(permissions: List<String>, callback: PermissionCallback) {
        currentCallback = callback

        if (isAllPermissionsGranted(permissions)) {
            callback.onAllGranted(permissions)
            return
        }

        val contract = ActivityResultContracts.RequestMultiplePermissions()
        activity.activityResultRegistry
            .register("Permissions_ID_${UUID.randomUUID()}", contract) { result ->
                val granted = result.filter { it.value }.keys.toList()
                val denied = result.filter { !it.value }.keys.toList()

                if (denied.isEmpty()) {
                    callback.onAllGranted(granted)
                    return@register
                }

                callback.onDenied(granted, denied)

                if (shouldShowPermissionRationale(denied)) {
                    return@register
                }

                val onlyOrNull = if (denied.size == 1) denied.first() else null
                callback.onShouldOpenSetting(activity) {
                    currentPermissions = permissions
                    isWaitingForSettings = true
                    activity.application.registerActivityLifecycleCallbacks(lifecycleCallback)
                    activity.openSettingPermission(onlyOrNull)
                }
            }.launch(permissions.toTypedArray())
    }


    private fun isAllPermissionsGranted(permissions: List<String>): Boolean {
        return permissions.all { activity.isPermissionGranted(it) }
    }

    private fun shouldShowPermissionRationale(permissions: List<String>): Boolean {
        return permissions.any { activity.shouldShowPermissionRationale(it) }
    }
}