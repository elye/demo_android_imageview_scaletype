package com.example.matrixscale

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val IMAGE_TYPE_KEY = "IMAGETYPE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image_picture.setImageResource((
                intent.getSerializableExtra(IMAGE_TYPE_KEY) as ImagesType).imageId)


    }
}