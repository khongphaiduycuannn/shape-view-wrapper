package com.ndmquan.permission.permissions

import android.Manifest
import android.os.Build

class ReadStoragePermissions(
    val images: Boolean = true,
    val videos: Boolean = true,
    val audios: Boolean = true
) : PermissionsGroup {

    override val permissions = mutableListOf<String>().apply {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.READ_EXTERNAL_STORAGE)
            return@apply
        }

        if (images) add(Manifest.permission.READ_MEDIA_IMAGES)
        if (videos) add(Manifest.permission.READ_MEDIA_VIDEO)
        if (audios) add(Manifest.permission.READ_MEDIA_AUDIO)
    }
}