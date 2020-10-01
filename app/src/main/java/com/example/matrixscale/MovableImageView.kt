package com.example.matrixscale

import android.content.Context
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.OverScroller
import androidx.appcompat.widget.AppCompatImageView

class MovableImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var lastX: Int = 0
    private var lastY: Int = 0

    private val scroller = OverScroller(context, AccelerateDecelerateInterpolator())

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.rawX.toInt()
                lastY = event.rawY.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                moveImage(event.rawX, event.rawY)
            }
            MotionEvent.ACTION_UP -> {
                scroller.startScroll(scrollX, scrollY, -scrollX, -scrollY)
                invalidate()
            }
        }
        return super.onTouchEvent(event)
    }

    private fun moveImage(xPos: Float, yPos: Float) {
        val bounds = RectF()
        imageMatrix.mapRect(bounds, RectF(drawable.bounds))

        var disX = xPos - lastX
        var disY = yPos - lastY

        val leftLimit = if (bounds.left < 0) bounds.left else 0f
        val topLimit = if (bounds.top < 0) bounds.top else 0f
        val rightLimit = if (bounds.right > width) bounds.right else width.toFloat()
        val bottomLimit = if (bounds.bottom > height) bounds.bottom else height.toFloat()

        if (scrollX - disX.toInt() < leftLimit) {
            disX = scrollX.toFloat() - leftLimit
        } else if (scrollX - disX.toInt() > (rightLimit - width)) {
            disX = scrollX.toFloat() - (rightLimit - width)
        }

        if (scrollY - disY.toInt() < topLimit) {
            disY = scrollY.toFloat() - topLimit
        } else if (scrollY - disY.toInt() > (bottomLimit - height)) {
            disY = scrollY.toFloat() - (bottomLimit - height)
        }

        scrollBy(-disX.toInt(), -disY.toInt())

        lastX = xPos.toInt()
        lastY = yPos.toInt()
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            invalidate()
        }
    }
}
