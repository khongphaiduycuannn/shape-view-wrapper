package com.wrapper.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import androidx.core.content.withStyledAttributes
import com.hjq.shape.R
import com.hjq.shape.view.ShapeButton
import com.wrapper.utils.enableScaleOnPress

class RippleShapeButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ShapeButton(context, attrs, defStyleAttr) {

    companion object {
        const val DEFAULT_OFFSET = 1.1f
        const val DEFAULT_RIPPLE_RADIUS = 0f
        const val DEFAULT_RIPPLE_COLOR = 0x20FFFFFF
    }


    private var rippleRadius: Float = DEFAULT_RIPPLE_RADIUS
    private var rippleColor: Int = DEFAULT_RIPPLE_COLOR
    private var enableScaleOnPress: Boolean = false


    init {
        isClickable = true
        setupAttrs(context, attrs)
    }


    private fun setupAttrs(context: Context, attrs: AttributeSet?) {
        parseAttrs(context, attrs)

        setupScaleOnPress()
        notifyRippleValueChanged()
    }


    private fun parseAttrs(context: Context, attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.RippleShapeButton) {
            val rippleColor = getColor(
                R.styleable.RippleShapeButton_shape_ripple_color,
                DEFAULT_RIPPLE_COLOR
            )
            this@RippleShapeButton.rippleColor = rippleColor

            val rippleRadius =
                if (hasValue(R.styleable.RippleShapeButton_shape_ripple_radius)) {
                    getDimension(R.styleable.RippleShapeButton_shape_ripple_radius, 0f)
                } else {
                    val topLeftRadius = shapeDrawableBuilder.topLeftRadius
                    val topRightRadius = shapeDrawableBuilder.topRightRadius
                    val bottomLeftRadius = shapeDrawableBuilder.bottomLeftRadius
                    val bottomRightRadius = shapeDrawableBuilder.bottomRightRadius
                    arrayOf(
                        topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius
                    ).maxOrNull() ?: 0f
                }
            this@RippleShapeButton.rippleRadius = rippleRadius

            val scaleOnPress = getBoolean(
                R.styleable.RippleShapeButton_scale_on_press,
                false
            )
            this@RippleShapeButton.enableScaleOnPress = scaleOnPress
        }
    }

    private fun notifyRippleValueChanged() {
        val rippleRadiusWithOffset = rippleRadius + DEFAULT_OFFSET
        val mask = createMask(rippleRadiusWithOffset)
        val rippleDrawable = RippleDrawable(
            ColorStateList.valueOf(rippleColor),
            null,
            mask
        )
        foreground = rippleDrawable
    }

    private fun setupScaleOnPress() {
        if (enableScaleOnPress) {
            enableScaleOnPress()
        }
    }

    private fun createMask(radius: Float): ShapeDrawable {
        val radii = FloatArray(8) { radius }
        val shape = RoundRectShape(radii, null, null)
        return ShapeDrawable(shape)
    }


    fun setRippleColor(color: Int) {
        rippleColor = color
        notifyRippleValueChanged()
    }

    fun setRippleRadius(radius: Float) {
        rippleRadius = radius
        notifyRippleValueChanged()
    }
}