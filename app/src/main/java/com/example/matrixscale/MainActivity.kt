package com.example.matrixscale

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.withSign

class MainActivity : AppCompatActivity() {
    companion object {
        const val IMAGE_TYPE_KEY = "IMAGETYPE"
        const val SCALE_TYPE_KEY = "SCALETYPE"
        const val SIZE_TYPE_KEY = "SIZETYPE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image_picture.apply {
            layoutParams.height = (intent.getSerializableExtra(SIZE_TYPE_KEY) as SizeTypeEnum).layoutParams.first
            layoutParams.width = (intent.getSerializableExtra(SIZE_TYPE_KEY) as SizeTypeEnum).layoutParams.second
            setImageResource((intent.getSerializableExtra(IMAGE_TYPE_KEY) as ImagesTypeEnum).imageId)
            scaleType = (intent.getSerializableExtra(SCALE_TYPE_KEY) as ScaleTypeEnum).scaleType
        }
    }
}
