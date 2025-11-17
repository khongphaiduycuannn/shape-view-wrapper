package com.wrapper.utils

import android.annotation.SuppressLint
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import com.intuit.sdp.R


private const val TAG_CLICK_COUNT = -1


const val DEFAULT_SCALE_DURATION = 100L
const val DEFAULT_SCALE_FACTOR = 0.95f

const val DEFAULT_DEBOUNCE_TIME = 300L
const val DOUBLE_CLICK_INTERVAL = 200L
private var globalLastClickTime = 0L


fun View.enableScaleOnPress(
    scaleFactor: Float = DEFAULT_SCALE_FACTOR,
    duration: Long = DEFAULT_SCALE_DURATION
) {
    isClickable = true
    isFocusable = true

    @SuppressLint("ClickableViewAccessibility")
    setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                v.animate()
                    .scaleX(scaleFactor)
                    .scaleY(scaleFactor)
                    .setDuration(duration)
                    .start()
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                v.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(duration)
                    .start()
            }
        }
        false
    }
}

fun View.disableScaleOnPress() {
    setOnTouchListener(null)
    animate()
        .scaleX(1.0f)
        .scaleY(1.0f)
        .setDuration(0)
        .start()
}


fun View.setOnSingleClickListener(
    debounceMs: Long = DEFAULT_DEBOUNCE_TIME,
    onClick: (View) -> Unit
) {
    setOnClickListener {
        val currentTime = SystemClock.uptimeMillis()
        val timeDiff = currentTime - globalLastClickTime

        if (timeDiff < debounceMs) {
            return@setOnClickListener
        }

        onClick(this)
        globalLastClickTime = SystemClock.uptimeMillis()
    }
}

fun View.setOnDoubleClickListener(
    intervalMs: Long = DOUBLE_CLICK_INTERVAL,
    debounceMs: Long = DEFAULT_DEBOUNCE_TIME,
    onDoubleClick: (View) -> Unit
) {
    setTag(TAG_CLICK_COUNT, 0)

    setOnClickListener {
        val currentTime = SystemClock.uptimeMillis()
        val clickCount = getTag(TAG_CLICK_COUNT) as? Int ?: 0
        val timeDiff = currentTime - globalLastClickTime

        if (timeDiff < debounceMs) {
            if (clickCount == 1 && timeDiff <= intervalMs) {
                onDoubleClick(this)
                setTag(TAG_CLICK_COUNT, 0)
                globalLastClickTime = currentTime
            }
            return@setOnClickListener
        }

        setTag(TAG_CLICK_COUNT, 1)
        globalLastClickTime = currentTime
    }
}