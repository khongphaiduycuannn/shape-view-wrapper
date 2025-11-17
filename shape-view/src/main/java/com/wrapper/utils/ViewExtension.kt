package com.wrapper.utils

import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.View
import com.wrapper.view.RippleShapeButton.Companion.DEFAULT_OFFSET

fun View.enableRippleOnClick(radius: Float = 0f, rippleColor: Int = 0x15FFFFFF) {
    isClickable = true

    val rippleRadiusWithOffset = radius + DEFAULT_OFFSET
    val mask = createRippleMask(rippleRadiusWithOffset)
    val rippleDrawable = RippleDrawable(
        ColorStateList.valueOf(rippleColor),
        null,
        mask
    )

    foreground = rippleDrawable
}

private fun createRippleMask(radius: Float): ShapeDrawable {
    val radii = FloatArray(8) { radius }
    val shape = RoundRectShape(radii, null, null)
    return ShapeDrawable(shape)
}