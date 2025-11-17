package com.wrapper.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import androidx.core.content.withStyledAttributes
import com.hjq.shape.R
import com.hjq.shape.layout.ShapeLinearLayout

class RippleShapeLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ShapeLinearLayout(context, attrs, defStyleAttr) {

    companion object {
        const val DEFAULT_OFFSET = 1.1f
        const val DEFAULT_RIPPLE_RADIUS = 0f
        const val DEFAULT_RIPPLE_COLOR = 0x20FFFFFF
    }


    private var rippleRadius: Float = DEFAULT_RIPPLE_RADIUS
    private var rippleColor: Int = DEFAULT_RIPPLE_COLOR


    init {
        isClickable = true
        setupRipple(context, attrs)
    }


    private fun setupRipple(context: Context, attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.RippleShapeLinearLayout) {
            val rippleColor = getColor(
                R.styleable.RippleShapeLinearLayout_shape_ripple_color,
                DEFAULT_RIPPLE_COLOR
            )
            this@RippleShapeLinearLayout.rippleColor = rippleColor

            val rippleRadius =
                if (hasValue(R.styleable.RippleShapeLinearLayout_shape_ripple_radius)) {
                    getDimension(R.styleable.RippleShapeLinearLayout_shape_ripple_radius, 0f)
                } else {
                    val topLeftRadius = shapeDrawableBuilder.topLeftRadius
                    val topRightRadius = shapeDrawableBuilder.topRightRadius
                    val bottomLeftRadius = shapeDrawableBuilder.bottomLeftRadius
                    val bottomRightRadius = shapeDrawableBuilder.bottomRightRadius
                    arrayOf(
                        topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius
                    ).maxOrNull() ?: 0f
                }
            this@RippleShapeLinearLayout.rippleRadius = rippleRadius
        }

        notifyRippleValueChanged()
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