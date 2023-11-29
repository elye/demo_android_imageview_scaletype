package com.example.matrixscale

import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import com.example.matrixscale.databinding.ActivityTurnPageBinding
import kotlin.math.abs
import kotlin.math.pow

class TurnPageActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private lateinit var binding: ActivityTurnPageBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTurnPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.seekTurnPage.setOnSeekBarChangeListener(this)

        binding.imagePicture.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            scalingPicture(100)
        }
    }

    private fun scalingPicture(scaleValue: Int) {

        val drawable = binding.imagePicture.drawable ?: return

        val viewWidth: Float = getImageViewWidth(binding.imagePicture).toFloat()
        val viewHeight: Float = getImageViewHeight(binding.imagePicture).toFloat()
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight


        val matrix = Matrix()
        matrix.setTranslate(viewWidth / 2f, (viewHeight - drawableHeight) / 2f - 40)
        matrix.preScale(scaleValue/100f, 1f)
        val skewValue = 0.3f - abs(scaleValue.toDouble().pow(2.0) /10000f) * 0.3f
        matrix.preSkew(0f, skewValue.toFloat())

        binding.imagePicture.imageMatrix = matrix
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