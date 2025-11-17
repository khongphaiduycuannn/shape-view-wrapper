package com.wrapper.utils

import android.content.Context
import android.content.res.Resources
import androidx.annotation.IntRange

fun @receiver:IntRange(from = -60, to = 600) Int.sdp(context: Context): Int {
    if (this == 0) {
        return 0
    }

    val coercedValue = this.coerceIn(-60, 600)

    val resourceName = when {
        coercedValue < 0 -> "_minus${-coercedValue}sdp"
        else -> "_${coercedValue}sdp"
    }
    val resourceId = context.resources.getIdentifier(
        resourceName,
        "dimen",
        context.packageName
    )

    return if (resourceId != 0) {
        context.resources.getDimensionPixelSize(resourceId)
    } else {
        (coercedValue * context.resources.displayMetrics.density).toInt()
    }
}

fun Int.dp(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Float.dp(): Float = this * Resources.getSystem().displayMetrics.density