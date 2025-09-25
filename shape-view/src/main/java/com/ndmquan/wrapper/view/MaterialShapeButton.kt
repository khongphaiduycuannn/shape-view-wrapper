package com.ndmquan.wrapper.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.hjq.shape.R
import com.hjq.shape.builder.ShapeDrawableBuilder
import com.hjq.shape.builder.TextColorBuilder
import com.hjq.shape.view.ShapeButton

class MaterialShapeButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val shapeButton = ShapeButton(context, attrs, defStyleAttr)

    init {
        initCardView(attrs, defStyleAttr)
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


    private fun initCardView(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            cardElevation = 0f
            val typedArray: TypedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.MaterialShapeButton,
                defStyleAttr,
                0
            )
            radius = runCatching {
                typedArray.getDimension(R.styleable.ShapeButton_shape_radius, 0f)
            }.onFailure { ex ->
                ex.printStackTrace()
            }.also {
                typedArray.recycle()
            }.getOrDefault(0f)
        }

        post {
            isClickable = true
            isFocusable = true
        }
    }

    private fun initShapeButton() {
        shapeButton.apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            isClickable = false
            isFocusable = false
        }

        addView(shapeButton)
    }
}