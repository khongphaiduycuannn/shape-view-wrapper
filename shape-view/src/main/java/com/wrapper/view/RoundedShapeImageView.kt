package com.wrapper.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.withClip
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hjq.shape.R
import com.hjq.shape.view.ShapeImageView

class RoundedShapeImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ShapeImageView(context, attrs, defStyleAttr) {

    private val clipPath = Path()
    private val clipRect = RectF()
    private var needsUpdateClipPath = true


    init {
        setLayerType(LAYER_TYPE_HARDWARE, null)
        attrs?.let { parseAttributes(it) }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        needsUpdateClipPath = true
    }

    override fun onDraw(canvas: Canvas) {
        if (needsUpdateClipPath) {
            updateClipPath()
            needsUpdateClipPath = false
        }

        canvas.withClip(clipPath) {
            super.onDraw(this)
        }
    }


    private fun parseAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundedShapeImageView)

        val drawableResId = typedArray.getResourceId(
            R.styleable.RoundedShapeImageView_shape_imageDrawableSrc,
            0
        )
        val urlString = typedArray.getString(R.styleable.RoundedShapeImageView_shape_imageUrlSrc)

        val imageSrc = if (drawableResId != 0) drawableResId else urlString

        val enableCache = typedArray.getBoolean(
            R.styleable.RoundedShapeImageView_shape_cacheSrc,
            true
        )

        val placeholderDrawable = typedArray.getDrawable(
            R.styleable.RoundedShapeImageView_shape_srcPlaceholder
        )
        val errorDrawable = typedArray.getDrawable(
            R.styleable.RoundedShapeImageView_shape_srcError
        )

        typedArray.recycle()

        imageSrc?.let {
            loadImageInternal(it, enableCache, placeholderDrawable, errorDrawable)
        }
    }

    private fun updateClipPath() {
        val builder = shapeDrawableBuilder

        val shadowSize = builder.shadowSize.toFloat()
        val shadowOffsetX = builder.shadowOffsetX.toFloat()
        val shadowOffsetY = builder.shadowOffsetY.toFloat()
        val strokeSize = builder.strokeSize.toFloat()

        val left = shadowSize - shadowOffsetX + strokeSize
        val top = shadowSize - shadowOffsetY + strokeSize
        val right = width - shadowSize - shadowOffsetX - strokeSize
        val bottom = height - shadowSize - shadowOffsetY - strokeSize

        clipRect.set(left, top, right, bottom)

        val topLeftRadius = (builder.topLeftRadius - strokeSize).coerceAtLeast(0f)
        val topRightRadius = (builder.topRightRadius - strokeSize).coerceAtLeast(0f)
        val bottomRightRadius = (builder.bottomRightRadius - strokeSize).coerceAtLeast(0f)
        val bottomLeftRadius = (builder.bottomLeftRadius - strokeSize).coerceAtLeast(0f)

        val radii = floatArrayOf(
            topLeftRadius, topLeftRadius,
            topRightRadius, topRightRadius,
            bottomRightRadius, bottomRightRadius,
            bottomLeftRadius, bottomLeftRadius
        )

        clipPath.reset()
        clipPath.addRoundRect(clipRect, radii, Path.Direction.CW)
    }

    private fun loadImageInternal(
        source: Any,
        enableCache: Boolean,
        placeholder: Drawable?,
        error: Drawable?
    ) {
        val request = Glide.with(this).load(source)

        placeholder?.let { request.placeholder(it) }
        error?.let { request.error(it) }

        if (enableCache) {
            request.diskCacheStrategy(DiskCacheStrategy.ALL).into(this)
        } else {
            request.diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(this)
        }
    }


    fun loadImage(
        src: Any,
        enableCache: Boolean = true
    ) {
        loadImageInternal(src, enableCache, null, null)
    }

    fun loadImage(
        src: Any,
        enableCache: Boolean = true,
        @DrawableRes placeholderRes: Int = 0,
        @DrawableRes errorRes: Int = 0
    ) {
        val placeholder = if (placeholderRes != 0) AppCompatResources.getDrawable(
            context,
            placeholderRes
        ) else null

        val error = if (errorRes != 0) AppCompatResources.getDrawable(
            context,
            errorRes
        ) else null

        loadImageInternal(src, enableCache, placeholder, error)
    }

    fun loadImage(
        src: Any,
        enableCache: Boolean = true,
        placeholder: Drawable?,
        error: Drawable?
    ) {
        loadImageInternal(src, enableCache, placeholder, error)
    }
}