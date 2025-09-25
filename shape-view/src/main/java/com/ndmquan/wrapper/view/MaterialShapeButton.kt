package com.ndmquan.wrapper.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.hjq.shape.R
import com.hjq.shape.builder.ShapeDrawableBuilder
import com.hjq.shape.builder.TextColorBuilder
import com.hjq.shape.view.ShapeButton

class MaterialShapeButton @JvmOverloads constructor(
    private val context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    companion object {
        const val FIXED_OFFSET = 1
    }


    private val innerShapeButton = ShapeButton(context, attrs, defStyleAttr)
    private val rippleEffectContainer = FrameLayout(context)


    init {
        setupCardViewProperties()
        setupButtonLayout()
    }


    val shapeDrawableBuilder: ShapeDrawableBuilder
        get() = innerShapeButton.shapeDrawableBuilder

    val textColorBuilder: TextColorBuilder
        get() = innerShapeButton.textColorBuilder

    fun setTextColor(color: Int) {
        innerShapeButton.setTextColor(color)
    }

    fun setText(text: CharSequence, type: TextView.BufferType? = null) {
        innerShapeButton.setText(text, type)
    }


    @SuppressLint("CustomViewStyleable")
    private fun setupCardViewProperties() {
        cardElevation = 0f
        radius = extractDimensionValue(R.styleable.ShapeButton_shape_radius)
    }

    private fun setupButtonLayout() {
        setupRippleContainer()
        setupInnerButton()
        addChildViews()
    }

    private fun setupRippleContainer() {
        rippleEffectContainer.apply {
            val shadowOffset = calculateShadowOffset()
            layoutParams = createContainerLayoutParams(shadowOffset)
            background = createRippleDrawable()
            isClickable = true
            isFocusable = true
        }
    }

    private fun setupInnerButton() {
        innerShapeButton.apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            isClickable = false
            isFocusable = false
        }
    }

    private fun addChildViews() {
        addView(innerShapeButton)
        addView(rippleEffectContainer)
    }

    private fun calculateShadowOffset(): Int {
        return extractDimensionValue(R.styleable.ShapeButton_shape_shadowSize).toInt() + FIXED_OFFSET
    }

    private fun createContainerLayoutParams(shadowOffset: Int): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        ).apply {
            setMargins(shadowOffset, shadowOffset, shadowOffset, shadowOffset)
        }
    }

    private fun createRippleDrawable(): RippleDrawable {
        return RippleDrawable(
            rippleColor,
            null,
            createRippleMask()
        )
    }

    private fun createRippleMask(): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = radius + 2 * FIXED_OFFSET
            setColor(Color.WHITE)
        }
    }

    @SuppressLint("CustomViewStyleable")
    private fun extractDimensionValue(attributeId: Int): Float {
        val attributeSet = attrs ?: return 0f

        val typedArray: TypedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.ShapeButton,
            defStyleAttr,
            0
        )

        return runCatching {
            typedArray.getDimension(attributeId, 0f)
        }.onFailure { exception ->
            exception.printStackTrace()
        }.also {
            typedArray.recycle()
        }.getOrDefault(0f)
    }
}