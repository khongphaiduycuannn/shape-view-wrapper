package com.wrapper.utils

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.text.TextUtils
import android.widget.TextView
import androidx.core.graphics.toColorInt

const val TAG_TEXT_COLOR_ANIMATOR = -732
const val DEFAULT_DURATION = 200L

fun TextView.animateTextColor(
    toColor: String,
    duration: Long = DEFAULT_DURATION
) {
    val colorInt = try {
        toColor.toColorInt()
    } catch (_: Exception) {
        Color.BLACK
    }
    animateTextColor(currentTextColor, colorInt, duration)
}

fun TextView.animateTextColor(
    fromColor: String,
    toColor: String,
    duration: Long = DEFAULT_DURATION
) {
    val fromColorInt = try {
        fromColor.toColorInt()
    } catch (_: Exception) {
        Color.BLACK
    }
    val toColorInt = try {
        toColor.toColorInt()
    } catch (_: Exception) {
        Color.BLACK
    }
    animateTextColor(fromColorInt, toColorInt, duration)
}

fun TextView.animateTextColor(
    toColor: Int,
    duration: Long = DEFAULT_DURATION
) {
    animateTextColor(currentTextColor, toColor, duration)
}

fun TextView.animateTextColor(
    fromColor: Int,
    toColor: Int,
    duration: Long = DEFAULT_DURATION
) {
    (getTag(TAG_TEXT_COLOR_ANIMATOR) as? ValueAnimator)?.cancel()

    val animator = ValueAnimator.ofObject(ArgbEvaluator(), fromColor, toColor).apply {
        this.duration = duration
        addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Int
            setTextColor(animatedValue)
        }
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationCancel(p0: Animator) {
                setTag(TAG_TEXT_COLOR_ANIMATOR, null)
            }

            override fun onAnimationEnd(p0: Animator) {
                setTag(TAG_TEXT_COLOR_ANIMATOR, null)
            }

            override fun onAnimationRepeat(p0: Animator) {}
            override fun onAnimationStart(p0: Animator) {}
        })
    }

    setTag(TAG_TEXT_COLOR_ANIMATOR, animator)
    animator.start()
}

fun TextView.enableRunningText() {
    ellipsize = TextUtils.TruncateAt.MARQUEE
    marqueeRepeatLimit = -1
    isSingleLine = true
    isSelected = true
}