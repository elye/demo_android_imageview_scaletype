package com.example.matrixscale

import android.graphics.Matrix
import android.graphics.RectF
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.image_picture
import kotlinx.android.synthetic.main.activity_matrix.*

class MatrixActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matrix)
        seek_one_one.setOnSeekBarChangeListener(this)
        seek_one_two.setOnSeekBarChangeListener(this)
        seek_one_three.setOnSeekBarChangeListener(this)
        seek_two_one.setOnSeekBarChangeListener(this)
        seek_two_two.setOnSeekBarChangeListener(this)
        seek_two_three.setOnSeekBarChangeListener(this)
        seek_three_one.setOnSeekBarChangeListener(this)
        seek_three_two.setOnSeekBarChangeListener(this)
        seek_three_three.setOnSeekBarChangeListener(this)

        image_picture.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            updateBaseMatrix()
        }
    }

    private fun updateBaseMatrix() {
        val drawable = image_picture.drawable ?: return

        val baseMatrix = Matrix()
        translateToCenter(drawable, baseMatrix)
    }

    private fun translateToCenter(
        drawable: Drawable,
        baseMatrix: Matrix
    ) {
        val viewWidth: Float = getImageViewWidth(image_picture).toFloat()
        val viewHeight: Float = getImageViewHeight(image_picture).toFloat()
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight
        baseMatrix.postTranslate(
            (viewWidth - drawableWidth) / 2f,
            (viewHeight - drawableHeight) / 2f
        )

        image_picture.imageMatrix = baseMatrix
    }

    private fun getImageViewWidth(imageView: ImageView): Int {
        return imageView.width - imageView.paddingLeft - imageView.paddingRight
    }

    private fun getImageViewHeight(imageView: ImageView): Int {
        return imageView.height - imageView.paddingTop - imageView.paddingBottom
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        val drawable = image_picture.drawable ?: return

        val matrix = Matrix().apply {
            setValues(
                floatArrayOf(
                    seek_one_one.progress.toFloat()/100,
                    seek_one_two.progress.toFloat()/100,
                    seek_one_three.progress.toFloat(),
                    seek_two_one.progress.toFloat()/100,
                    seek_two_two.progress.toFloat()/100,
                    seek_two_three.progress.toFloat(),
                    seek_three_one.progress.toFloat()/10000,
                    seek_three_two.progress.toFloat()/10000,
                    seek_three_three.progress.toFloat()/100
                )
            )
        }

        text_one_one.text = (seek_one_one.progress.toFloat()/100).toString()
        text_one_two.text = (seek_one_two.progress.toFloat()/100).toString()
        text_one_three.text = (seek_one_three.progress.toFloat()).toString()
        text_two_one.text = (seek_two_one.progress.toFloat()/100).toString()
        text_two_two.text = (seek_two_two.progress.toFloat()/100).toString()
        text_two_three.text = (seek_two_three.progress.toFloat()).toString()
        text_three_one.text = (seek_three_one.progress.toFloat()/10000).toString()
        text_three_two.text = (seek_three_two.progress.toFloat()/10000).toString()
        text_three_three.text = (seek_three_three.progress.toFloat()/100).toString()

        translateToCenter(drawable, matrix)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}