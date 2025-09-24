package com.ndmquan.wrapper.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.hjq.shape.builder.ShapeDrawableBuilder
import com.hjq.shape.builder.TextColorBuilder
import com.hjq.shape.view.ShapeButton

class MaterialShapeButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val shapeButton = ShapeButton(context, attrs, defStyleAttr)

    init {
        cardElevation = 0f

        shapeButton.apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            isClickable = false
            isFocusable = false
            background = null
        }

        addView(shapeButton)

        post {
            background = shapeButton.shapeDrawableBuilder.drawable
        }
    }

    val shapeDrawableBuilder: ShapeDrawableBuilder get() = shapeButton.shapeDrawableBuilder

    val textColorBuilder: TextColorBuilder get() = shapeButton.textColorBuilder

    fun setTextColor(color: Int) {
        shapeButton.setTextColor(color)
    }

    fun setText(text: CharSequence, type: TextView.BufferType? = null) {
        shapeButton.setText(text, type)
    }
}