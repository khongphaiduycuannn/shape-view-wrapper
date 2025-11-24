package com.wrapper.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.withStyledAttributes
import com.hjq.shape.R
import com.hjq.shape.layout.ShapeLinearLayout
import com.wrapper.utils.constants.ShapeConstants
import com.wrapper.utils.enableRippleOnClick
import com.wrapper.utils.enableScaleOnPress

class RippleShapeLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ShapeLinearLayout(context, attrs, defStyleAttr) {


    private var rippleRadius = floatArrayOf(
        ShapeConstants.DEFAULT_RIPPLE_RADIUS,
        ShapeConstants.DEFAULT_RIPPLE_RADIUS,
        ShapeConstants.DEFAULT_RIPPLE_RADIUS,
        ShapeConstants.DEFAULT_RIPPLE_RADIUS
    )

    private var rippleColor: Int = ShapeConstants.DEFAULT_RIPPLE_COLOR

    private var enableScaleOnPress: Boolean = false


    init {
        isClickable = true
        setupAttrs(context, attrs)
    }


    private fun setupAttrs(context: Context, attrs: AttributeSet?) {
        parseAttrs(context, attrs)

        applyScaleOnPress()
        applyRippleEffect()
    }


    private fun parseAttrs(context: Context, attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.RippleShapeLinearLayout) {
            val rippleColor = getColor(
                R.styleable.RippleShapeLinearLayout_shape_ripple_color,
                ShapeConstants.DEFAULT_RIPPLE_COLOR
            )
            this@RippleShapeLinearLayout.rippleColor = rippleColor


            val rippleRadius =
                if (hasValue(R.styleable.RippleShapeLinearLayout_shape_ripple_radius)) {
                    val radiusAttrValue = getDimension(
                        R.styleable.RippleShapeLinearLayout_shape_ripple_radius, 0f
                    )
                    floatArrayOf(
                        radiusAttrValue, radiusAttrValue, radiusAttrValue, radiusAttrValue
                    )
                } else {
                    val topLeftRadius = shapeDrawableBuilder.topLeftRadius
                    val topRightRadius = shapeDrawableBuilder.topRightRadius
                    val bottomLeftRadius = shapeDrawableBuilder.bottomLeftRadius
                    val bottomRightRadius = shapeDrawableBuilder.bottomRightRadius
                    floatArrayOf(topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius)
                }
            this@RippleShapeLinearLayout.rippleRadius = rippleRadius


            val scaleOnPress = getBoolean(
                R.styleable.RippleShapeLinearLayout_scale_on_press,
                false
            )
            this@RippleShapeLinearLayout.enableScaleOnPress = scaleOnPress
        }
    }


    private fun applyRippleEffect() {
        enableRippleOnClick(rippleRadius, rippleColor)
    }

    private fun applyScaleOnPress() {
        if (enableScaleOnPress) {
            enableScaleOnPress()
        }
    }


    fun setRippleColor(color: Int) {
        rippleColor = color
        applyRippleEffect()
    }

    fun setRippleRadius(radius: Float) {
        rippleRadius = floatArrayOf(radius, radius, radius, radius)
        applyRippleEffect()
    }
}