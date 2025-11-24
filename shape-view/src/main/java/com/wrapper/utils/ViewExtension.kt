package com.wrapper.utils

import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.View
import com.wrapper.utils.constants.ShapeConstants

fun View.enableRippleOnClick(
    topLeft: Float = ShapeConstants.DEFAULT_RIPPLE_RADIUS,
    topRight: Float = ShapeConstants.DEFAULT_RIPPLE_RADIUS,
    bottomRight: Float = ShapeConstants.DEFAULT_RIPPLE_RADIUS,
    bottomLeft: Float = ShapeConstants.DEFAULT_RIPPLE_RADIUS,
    rippleColor: Int = ShapeConstants.DEFAULT_RIPPLE_COLOR
) {
    isClickable = true

    val topLeftWithOffset = topLeft + ShapeConstants.DEFAULT_FIXED_OFFSET
    val topRightWithOffset = topRight + ShapeConstants.DEFAULT_FIXED_OFFSET
    val bottomRightWithOffset = bottomRight + ShapeConstants.DEFAULT_FIXED_OFFSET
    val bottomLeftWithOffset = bottomLeft + ShapeConstants.DEFAULT_FIXED_OFFSET
    val mask = createRippleMask(
        topLeftWithOffset,
        topRightWithOffset,
        bottomRightWithOffset,
        bottomLeftWithOffset
    )

    val rippleDrawable = RippleDrawable(
        ColorStateList.valueOf(rippleColor),
        null,
        mask
    )

    foreground = rippleDrawable
}

fun View.enableRippleOnClick(
    radius: Float = ShapeConstants.DEFAULT_RIPPLE_RADIUS,
    rippleColor: Int = ShapeConstants.DEFAULT_RIPPLE_COLOR
) {
    enableRippleOnClick(
        topLeft = radius,
        topRight = radius,
        bottomRight = radius,
        bottomLeft = radius,
        rippleColor = rippleColor
    )
}

fun View.enableRippleOnClick(
    radius: FloatArray = FloatArray(4) { ShapeConstants.DEFAULT_RIPPLE_RADIUS },
    rippleColor: Int = ShapeConstants.DEFAULT_RIPPLE_COLOR
) {
    enableRippleOnClick(
        topLeft = radius[0],
        topRight = radius[1],
        bottomLeft = radius[2],
        bottomRight = radius[3],
        rippleColor = rippleColor
    )
}

private fun createRippleMask(
    topLeft: Float,
    topRight: Float,
    bottomRight: Float,
    bottomLeft: Float
): ShapeDrawable {
    val radii = floatArrayOf(
        topLeft, topLeft,
        topRight, topRight,
        bottomRight, bottomRight,
        bottomLeft, bottomLeft
    )
    val shape = RoundRectShape(radii, null, null)
    return ShapeDrawable(shape)
}