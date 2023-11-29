package com.example.matrixscale

import android.graphics.Matrix
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import com.example.matrixscale.databinding.ActivityMatrixBinding

class MatrixActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private lateinit var binding: ActivityMatrixBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatrixBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.seekOneOne.setOnSeekBarChangeListener(this)
        binding.seekOneTwo.setOnSeekBarChangeListener(this)
        binding.seekOneThree.setOnSeekBarChangeListener(this)
        binding.seekTwoOne.setOnSeekBarChangeListener(this)
        binding.seekTwoTwo.setOnSeekBarChangeListener(this)
        binding.seekTwoThree.setOnSeekBarChangeListener(this)
        binding.seekThreeOne.setOnSeekBarChangeListener(this)
        binding.seekThreeTwo.setOnSeekBarChangeListener(this)
        binding.seekThreeThree.setOnSeekBarChangeListener(this)

        binding.imagePicture.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            updateBaseMatrix()
        }
    }

    private fun updateBaseMatrix() {
        val drawable = binding.imagePicture.drawable ?: return

        val baseMatrix = Matrix()
        translateToCenter(drawable, baseMatrix)
    }

    private fun translateToCenter(
        drawable: Drawable,
        baseMatrix: Matrix
    ) {
        val viewWidth: Float = getImageViewWidth(binding.imagePicture).toFloat()
        val viewHeight: Float = getImageViewHeight(binding.imagePicture).toFloat()
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight
        baseMatrix.postTranslate(
            (viewWidth - drawableWidth) / 2f,
            (viewHeight - drawableHeight) / 2f
        )

        binding.imagePicture.imageMatrix = baseMatrix
    }

    private fun getImageViewWidth(imageView: ImageView): Int {
        return imageView.width - imageView.paddingLeft - imageView.paddingRight
    }

    private fun getImageViewHeight(imageView: ImageView): Int {
        return imageView.height - imageView.paddingTop - imageView.paddingBottom
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        val drawable = binding.imagePicture.drawable ?: return

        val oneOne = binding.seekOneOne.progress.toFloat()/100
        val oneTwo = binding.seekOneTwo.progress.toFloat()/100
        val oneThree = binding.seekOneThree.progress.toFloat()
        val twoOne = binding.seekTwoOne.progress.toFloat()/100
        val twoTwo = binding.seekTwoTwo.progress.toFloat()/100
        val twoThree = binding.seekTwoThree.progress.toFloat()
        val threeOne = binding.seekThreeOne.progress.toFloat()/10000
        val threeTwo = binding.seekThreeTwo.progress.toFloat()/10000
        val threeThree = binding.seekThreeThree.progress.toFloat()/100

        val matrix = Matrix().apply {
            setValues(
                floatArrayOf(
                    oneOne, oneTwo, oneThree,
                    twoOne, twoTwo, twoThree,
                    threeOne, threeTwo, threeThree
                )
            )
        }

        binding.textOneOne.text = oneOne.toString()
        binding.textOneTwo.text = oneTwo.toString()
        binding.textOneThree.text = oneThree.toString()
        binding.textTwoOne.text = twoOne.toString()
        binding.textTwoTwo.text = twoTwo.toString()
        binding.textTwoThree.text = twoThree.toString()
        binding.textThreeOne.text = threeOne.toString()
        binding.textThreeTwo.text = threeTwo.toString()
        binding.textThreeThree.text = threeThree.toString()

        translateToCenter(drawable, matrix)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}