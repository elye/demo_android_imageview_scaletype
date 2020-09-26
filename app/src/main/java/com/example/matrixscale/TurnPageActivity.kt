package com.example.matrixscale

import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_turn_page.seek_turn_page
import kotlinx.android.synthetic.main.activity_turn_page.image_picture
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.pow

class TurnPageActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_turn_page)
        seek_turn_page.setOnSeekBarChangeListener(this)

        image_picture.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            scalingPicture(100)
        }
    }

    private fun scalingPicture(scaleValue: Int) {

        val drawable = image_picture.drawable ?: return

        val viewWidth: Float = getImageViewWidth(image_picture).toFloat()
        val viewHeight: Float = getImageViewHeight(image_picture).toFloat()
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight


        val matrix = Matrix()
        matrix.setTranslate(viewWidth / 2f, (viewHeight - drawableHeight) / 2f - 40)
        matrix.preScale(scaleValue/100f, 1f)
        val skewValue = 0.3f - abs(scaleValue.toDouble().pow(2.0) /10000f) * 0.3f
        matrix.preSkew(0f, skewValue.toFloat())

        image_picture.imageMatrix = matrix
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        scalingPicture(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    private fun getImageViewWidth(imageView: ImageView): Int {
        return imageView.width - imageView.paddingLeft - imageView.paddingRight
    }

    private fun getImageViewHeight(imageView: ImageView): Int {
        return imageView.height - imageView.paddingTop - imageView.paddingBottom
    }

}