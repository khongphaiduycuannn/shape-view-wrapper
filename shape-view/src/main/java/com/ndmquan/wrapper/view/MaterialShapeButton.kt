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


    private val shapeButton = ShapeButton(context, attrs, defStyleAttr)
    private val rippleContainer = FrameLayout(context)


    init {
        initCardView()
        initShapeButton()
    }


    val shapeDrawableBuilder: ShapeDrawableBuilder get() = shapeButton.shapeDrawableBuilder

    val textColorBuilder: TextColorBuilder get() = shapeButton.textColorBuilder


    fun setTextColor(color: Int) {
        shapeButton.setTextColor(color)
    }

    fun setText(text: CharSequence, type: TextView.BufferType? = null) {
        shapeButton.setText(text, type)
    }


    @SuppressLint("CustomViewStyleable")
    private fun initCardView() {
        cardElevation = 0f
        radius = getDimenValue(R.styleable.ShapeButton_shape_radius)
    }

    private fun initShapeButton() {
        rippleContainer.apply {
            val shadowSize =
                getDimenValue(R.styleable.ShapeButton_shape_shadowSize).toInt() + FIXED_OFFSET
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            ).apply {
                setMargins(shadowSize, shadowSize, shadowSize, shadowSize)
            }

            background = RippleDrawable(
                rippleColor,
                null,
                GradientDrawable().apply {
                    shape = GradientDrawable.RECTANGLE
                    cornerRadius = radius + 2 * FIXED_OFFSET
                    setColor(Color.WHITE)
                }
            )

            isClickable = true
            isFocusable = true
        }

        shapeButton.apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            isClickable = false
            isFocusable = false
        }

        addView(shapeButton)
        addView(rippleContainer)
    }


    @SuppressLint("CustomViewStyleable")
    private fun getDimenValue(id: Int): Float {
        val attrs = attrs ?: return 0f

        val typedArray: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.ShapeButton,
            defStyleAttr,
            0
        )
        return runCatching {
            typedArray.getDimension(id, 0f)
        }.onFailure { ex ->
            ex.printStackTrace()
        }.also {
            typedArray.recycle()
        }.getOrDefault(0f)
    }
}