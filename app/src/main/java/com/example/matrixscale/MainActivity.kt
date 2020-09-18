package com.example.matrixscale

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val IMAGE_TYPE_KEY = "IMAGETYPE"
        const val SCALE_TYPE_KEY = "SCALETYPE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image_picture.apply {
            setImageResource((intent.getSerializableExtra(IMAGE_TYPE_KEY) as ImagesTypeEnum).imageId)
            scaleType = (intent.getSerializableExtra(SCALE_TYPE_KEY) as ScaleTypeEnum).scaleType
        }
    }
}
