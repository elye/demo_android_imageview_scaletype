package com.example.matrixscale

import android.graphics.Matrix
import android.graphics.RectF
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val IMAGE_TYPE_KEY = "IMAGE_TYPE_KEY"
        const val SCALE_TYPE_KEY = "SCALE_TYPE_KEY"
        const val SIZE_TYPE_KEY = "SIZE_TYPE_KEY"
        const val ADJUST_KEY = "ADJUST_KEY"
        const val MATRIX_BASED_KEY = "MATRIX_BASED_KEY"
        const val MATRIX_BASED_SCALE_KEY = "MATRIX_BASED_SCALE_KEY"
        const val SCROLLER_TYPE = "SCROLLER_TYPE_KEY"
    }

    private val baseMatrix = Matrix()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image_picture.apply {
            layoutParams.height = (intent.getSerializableExtra(SIZE_TYPE_KEY) as SizeTypeEnum).layoutParams.first
            layoutParams.width = (intent.getSerializableExtra(SIZE_TYPE_KEY) as SizeTypeEnum).layoutParams.second
            setImageResource((intent.getSerializableExtra(IMAGE_TYPE_KEY) as ImagesTypeEnum).imageId)
            adjustViewBounds = intent.getBooleanExtra(ADJUST_KEY, false)
            (this as MovableImageView).scrollerType = intent.getSerializableExtra(SCROLLER_TYPE) as RadioScollerEnum

            scaleType = if (intent.getBooleanExtra(MATRIX_BASED_KEY, false)) {
                val matrixBasedScaleTypeEnum =
                    (intent.getSerializableExtra(MATRIX_BASED_SCALE_KEY) as MatrixBasedScaleTypeEnum)
                addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                    updateBaseMatrix(matrixBasedScaleTypeEnum)
                }
                ImageView.ScaleType.MATRIX
            } else {
                (intent.getSerializableExtra(SCALE_TYPE_KEY) as ScaleTypeEnum).scaleType
            }

        }
    }

    private fun updateBaseMatrix(scaleType: MatrixBasedScaleTypeEnum) {
        val drawable = image_picture.drawable ?: return

        val viewWidth: Float = getImageViewWidth(image_picture).toFloat()
        val viewHeight: Float = getImageViewHeight(image_picture).toFloat()
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight
        baseMatrix.reset()
        val widthScale = viewWidth / drawableWidth
        val heightScale = viewHeight / drawableHeight
        when (scaleType) {
            MatrixBasedScaleTypeEnum.CENTER -> {
                baseMatrix.postTranslate(
                    (viewWidth - drawableWidth) / 2f,
                    (viewHeight - drawableHeight) / 2f
                )
            }
            MatrixBasedScaleTypeEnum.CENTER_CROP -> {
                val scale = widthScale.coerceAtLeast(heightScale)
                baseMatrix.postScale(scale, scale)
                baseMatrix.postTranslate(
                    (viewWidth - drawableWidth * scale) / 2f,
                    (viewHeight - drawableHeight * scale) / 2f
                )
            }
            MatrixBasedScaleTypeEnum.START_CROP -> {
                val scale = widthScale.coerceAtLeast(heightScale)
                baseMatrix.postScale(scale, scale)
            }
            MatrixBasedScaleTypeEnum.END_CROP -> {
                val scale = widthScale.coerceAtLeast(heightScale)
                baseMatrix.postScale(scale, scale)
                baseMatrix.postTranslate(
                    (viewWidth - drawableWidth * scale),
                    (viewHeight - drawableHeight * scale)
                )
            }
            MatrixBasedScaleTypeEnum.CENTER_INSIDE -> {
                val scale =
                    1.0f.coerceAtMost(widthScale.coerceAtMost(heightScale))
                baseMatrix.postScale(scale, scale)
                baseMatrix.postTranslate(
                    (viewWidth - drawableWidth * scale) / 2f,
                    (viewHeight - drawableHeight * scale) / 2f
                )
            }
            else -> {
                val tempSrc = RectF(0f, 0f, drawableWidth.toFloat(), drawableHeight.toFloat())
                val tempDst = RectF(0f, 0f, viewWidth, viewHeight)
                when (scaleType) {
                    MatrixBasedScaleTypeEnum.FIT_CENTER -> baseMatrix.setRectToRect(
                        tempSrc,
                        tempDst,
                        Matrix.ScaleToFit.CENTER
                    )
                    MatrixBasedScaleTypeEnum.FIT_START -> baseMatrix.setRectToRect(
                        tempSrc,
                        tempDst,
                        Matrix.ScaleToFit.START
                    )
                    MatrixBasedScaleTypeEnum.FIT_END -> baseMatrix.setRectToRect(
                        tempSrc,
                        tempDst,
                        Matrix.ScaleToFit.END
                    )
                    MatrixBasedScaleTypeEnum.FIT_XY -> baseMatrix.setRectToRect(
                        tempSrc,
                        tempDst,
                        Matrix.ScaleToFit.FILL
                    )
                    else -> {
                    }
                }
            }
        }

        image_picture.imageMatrix = baseMatrix
    }

    private fun getImageViewWidth(imageView: ImageView): Int {
        return imageView.width - imageView.paddingLeft - imageView.paddingRight
    }

    private fun getImageViewHeight(imageView: ImageView): Int {
        return imageView.height - imageView.paddingTop - imageView.paddingBottom
    }

}
