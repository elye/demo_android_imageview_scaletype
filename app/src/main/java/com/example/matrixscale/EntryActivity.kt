package com.example.matrixscale

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView.ScaleType
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import com.example.matrixscale.MainActivity.Companion.IMAGE_TYPE_KEY
import com.example.matrixscale.MainActivity.Companion.SCALE_TYPE_KEY
import kotlinx.android.synthetic.main.activity_entry.*


class EntryActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        image_selector.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, ImagesTypeEnum.values())

        scale_selector.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, ScaleTypeEnum.values())

        button_show.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(IMAGE_TYPE_KEY, ImagesTypeEnum.getEnum(image_selector.selectedItem.toString()))
                putExtra(SCALE_TYPE_KEY, ScaleTypeEnum.getEnum(scale_selector.selectedItem.toString()))
            }
            startActivity(intent)
        }
    }
}

enum class ImagesTypeEnum(val descriptor: String, @DrawableRes val imageId : Int) {
    BIGGER("Bigger Image - Fall", R.drawable.fall),
    SMALLER("Smaller Image - Lion", R.drawable.lion),
    TALLER("Taller Image - Tree", R.drawable.tree),
    LONGER("Longer Image - Bridge", R.drawable.bridge);

    override fun toString(): String {
        return descriptor
    }

    companion object {
        fun getEnum(value: String): ImagesTypeEnum {
            return values().first { it.descriptor == value }
        }
    }
}

enum class ScaleTypeEnum(val descriptor: String, val scaleType: ScaleType) {
    MATRIX("Matrix", ScaleType.MATRIX),
    CENTER("Center", ScaleType.CENTER),
    CENTER_INSIDE("Center Inside", ScaleType.CENTER_INSIDE),
    CENTER_CROP("Center Crop", ScaleType.CENTER_CROP),
    FIT_CENTER("Fit Center", ScaleType.FIT_CENTER),
    FIT_START("Fit Start", ScaleType.FIT_START),
    FIT_END("Fit End", ScaleType.FIT_END),
    FIT_XY("Fit XY", ScaleType.FIT_XY);

    override fun toString(): String {
        return descriptor
    }

    companion object {
        fun getEnum(value: String): ScaleTypeEnum {
            return values().first { it.descriptor == value }
        }
    }
}