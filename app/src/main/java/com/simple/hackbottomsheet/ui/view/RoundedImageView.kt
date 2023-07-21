package com.simple.hackbottomsheet.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.annotation.Px
import androidx.appcompat.widget.AppCompatImageView
import com.simple.hackbottomsheet.R

class RoundedImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val radii: FloatArray


    private val path = Path()

    private val mainRectF = RectF()


    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundedImageView, defStyleAttr, 0)

        val radius = typedArray.getDimension(R.styleable.RoundedImageView_radius, 0f)

        val topLeftRadius = typedArray.getDimension(R.styleable.RoundedImageView_topLeftRadius, radius)
        val topRightRadius = typedArray.getDimension(R.styleable.RoundedImageView_topRightRadius, radius)
        val bottomLeftRadius = typedArray.getDimension(R.styleable.RoundedImageView_bottomLeftRadius, radius)
        val bottomRightRadius = typedArray.getDimension(R.styleable.RoundedImageView_bottomRightRadius, radius)

        radii = floatArrayOf(topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius)

        typedArray.recycle()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        mainRectF.set(0f, 0f, width.toFloat(), height.toFloat())
    }

    override fun onDraw(canvas: Canvas) {

        path.reset()

        path.addRoundRect(mainRectF, radii, Path.Direction.CW)

        canvas.clipPath(path)

        super.onDraw(canvas)
    }

    fun setRadius(@Px radius: Int) {
        bytefill(radii, radius)
    }

    fun setRadius(corner: Corner?, @Px radius: Int) {
        when (corner) {
            Corner.TOP_LEFT -> {
                radii[0] = radius.toFloat()
                radii[1] = radius.toFloat()
            }

            Corner.TOP_RIGHT -> {
                radii[2] = radius.toFloat()
                radii[3] = radius.toFloat()
            }

            Corner.BOTTOM_RIGHT -> {
                radii[4] = radius.toFloat()
                radii[5] = radius.toFloat()
            }

            Corner.BOTTOM_LEFT -> {
                radii[6] = radius.toFloat()
                radii[7] = radius.toFloat()
            }

            else -> {}
        }
    }

    fun setRadius(list: List<Int>) {

        radii[0] = list[0].toFloat()
        radii[1] = list[1].toFloat()
        radii[2] = list[2].toFloat()
        radii[3] = list[3].toFloat()
        radii[4] = list[4].toFloat()
        radii[5] = list[5].toFloat()
        radii[6] = list[6].toFloat()
        radii[7] = list[7].toFloat()

        postInvalidate()
    }

    private fun bytefill(array: FloatArray, value: Int) {

        val len = array.size
        array[0] = value.toFloat()
        var i = 1
        while (i < len) {
            System.arraycopy(array, 0, array, i, Math.min(len - i, i))
            i += i
        }
    }

    enum class Corner {
        TOP_LEFT, TOP_RIGHT, BOTTOM_RIGHT, BOTTOM_LEFT
    }
}