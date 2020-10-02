package com.example.matrixscale

import android.content.Context
import android.content.res.Resources
import android.graphics.Matrix
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.OverScroller
import androidx.appcompat.widget.AppCompatImageView

class MovableImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var lastX: Int = 0
    private var lastY: Int = 0

    private val bounds = RectF()
    private var leftLimit = 0f
    private var topLimit = 0f
    private var rightLimit = 0f
    private var bottomLimit = 0f

    var scrollerType = RadioScollerEnum.NOTHING

    private val scroller = OverScroller(context, AccelerateDecelerateInterpolator())
    private var velocityTracker: VelocityTracker? = null

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        setTheBoundary()
    }

    override fun setImageMatrix(matrix: Matrix?) {
        super.setImageMatrix(matrix)
        setTheBoundary()
    }

    private fun setTheBoundary() {
        imageMatrix.mapRect(bounds, RectF(drawable.bounds))
        leftLimit = if (bounds.left < 0) bounds.left else 0f
        topLimit = if (bounds.top < 0) bounds.top else 0f
        rightLimit = (if (bounds.right > width) bounds.right else width.toFloat()) - width
        bottomLimit = (if (bounds.bottom > height) bounds.bottom else height.toFloat()) - height
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.rawX.toInt()
                lastY = event.rawY.toInt()

                velocityTracker = VelocityTracker.obtain()
                velocityTracker?.addMovement(event)
            }
            MotionEvent.ACTION_MOVE -> {
                moveImage(event.rawX, event.rawY)

                velocityTracker?.addMovement(event)
            }
            MotionEvent.ACTION_CANCEL -> {
                velocityTracker?.recycle()
                velocityTracker = null
            }
            MotionEvent.ACTION_UP -> {
                when (scrollerType) {
                    RadioScollerEnum.SPRING_BACK -> {
                        scroller.springBack(scrollX, scrollY, 0,0 ,0, 0)
                        invalidate()
                    }
                    RadioScollerEnum.SCROLL_TO -> {
                        scroller.startScroll(scrollX, scrollY, -scrollX, -scrollY, 1000)
                        invalidate()
                    }
                    RadioScollerEnum.FLING,
                    RadioScollerEnum.FLING_OVER -> {
                        val overValue = if (scrollerType == RadioScollerEnum.FLING_OVER)
                            resources.dpToPx(64) else 0

                        velocityTracker?.let {
                            // Compute velocity within the last 1000ms
                            it.addMovement(event)
                            it.computeCurrentVelocity(1000)

                            scroller.fling(
                                scrollX, scrollY,
                                -it.xVelocity.toInt(), -it.yVelocity.toInt(),
                                leftLimit.toInt(), rightLimit.toInt(),
                                topLimit.toInt(), bottomLimit.toInt(),
                                overValue, overValue
                            )
                            invalidate()
                        }
                    }
                }

                velocityTracker?.recycle()
                velocityTracker = null
            }
        }
        return super.onTouchEvent(event)
    }

    private fun moveImage(xPos: Float, yPos: Float) {
        var disX = xPos - lastX
        var disY = yPos - lastY

        if (scrollX - disX.toInt() < leftLimit) {
            disX = scrollX.toFloat() - leftLimit
        } else if (scrollX - disX.toInt() > rightLimit) {
            disX = scrollX.toFloat() - rightLimit
        }

        if (scrollY - disY.toInt() < topLimit) {
            disY = scrollY.toFloat() - topLimit
        } else if (scrollY - disY.toInt() > bottomLimit) {
            disY = scrollY.toFloat() - bottomLimit
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

    inline fun <reified T> Resources.dpToPx(value: Int): T {
        val result = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(), displayMetrics)

        return when (T::class) {
            Float::class -> result as T
            Int::class -> result.toInt() as T
            else -> throw IllegalStateException("Type not supported")
        }
    }
}
