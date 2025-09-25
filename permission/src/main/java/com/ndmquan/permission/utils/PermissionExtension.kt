package com.ndmquan.permission.utils

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

fun AppCompatActivity.isPermissionGranted(permission: String): Boolean {
    return when (permission) {
        Manifest.permission.SYSTEM_ALERT_WINDOW -> Settings.canDrawOverlays(this)
        else -> ActivityCompat.checkSelfPermission(this, permission) == PERMISSION_GRANTED
    }
}


fun AppCompatActivity.shouldShowPermissionRationale(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
}


fun AppCompatActivity.openSettingPermission(permission: String? = null) {
    val settingIntent = createSettingIntent(permission)

    try {
        if (settingIntent.resolveActivity(packageManager) != null) {
            startActivity(settingIntent)
        } else {
            openAppDetailsSettings()
        }
    } catch (e: Exception) {
        openAppDetailsSettings()
    }
}

private fun AppCompatActivity.createSettingIntent(permission: String?): Intent {
    return when (permission) {
        Manifest.permission.SYSTEM_ALERT_WINDOW -> {
            Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
                data = Uri.fromParts("package", packageName, null)
            }
        }

        Manifest.permission.POST_NOTIFICATIONS -> {
            Intent().apply {
                when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                        action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                        putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                    }

                    else -> {
                        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        data = Uri.fromParts("package", packageName, null)
                    }
                }
            }
        }

        Manifest.permission.WRITE_SETTINGS -> {
            Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
                data = Uri.fromParts("package", packageName, null)
            }
        }

        else -> {
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", packageName, null)
            }
        }
    }
}

private fun AppCompatActivity.openAppDetailsSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", packageName, null)
    }
    startActivity(intent)
}