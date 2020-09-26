package com.example.matrixscale

import android.content.Intent
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView.ScaleType
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import com.example.matrixscale.MainActivity.Companion.ADJUST_KEY
import com.example.matrixscale.MainActivity.Companion.IMAGE_TYPE_KEY
import com.example.matrixscale.MainActivity.Companion.MATRIX_BASED_KEY
import com.example.matrixscale.MainActivity.Companion.MATRIX_BASED_SCALE_KEY
import com.example.matrixscale.MainActivity.Companion.SCALE_TYPE_KEY
import com.example.matrixscale.MainActivity.Companion.SIZE_TYPE_KEY
import kotlinx.android.synthetic.main.activity_entry.*


class EntryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        image_selector.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, ImagesTypeEnum.values()
        )

        scale_selector.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, ScaleTypeEnum.values()
        )

        size_selector.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, SizeTypeEnum.values()
        )

        matrix_based_scale_selector.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, MatrixBasedScaleTypeEnum.values()
        )

        button_show.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(
                    IMAGE_TYPE_KEY,
                    ImagesTypeEnum.getEnum(image_selector.selectedItem.toString())
                )
                putExtra(
                    SCALE_TYPE_KEY,
                    ScaleTypeEnum.getEnum(scale_selector.selectedItem.toString())
                )
                putExtra(
                    SIZE_TYPE_KEY,
                    SizeTypeEnum.getEnum(size_selector.selectedItem.toString())
                )
                putExtra(ADJUST_KEY, adjustviewbounds_selector.isChecked)
                putExtra(MATRIX_BASED_KEY, matrixbased_selector.isChecked)
                putExtra(
                    MATRIX_BASED_SCALE_KEY,
                    MatrixBasedScaleTypeEnum.getEnum(
                        matrix_based_scale_selector.selectedItem.toString())
                )
            }
            startActivity(intent)
        }

        button_matrix_explore.setOnClickListener {
            startActivity(Intent(this, MatrixActivity::class.java))
        }

        button_matrix_page.setOnClickListener {
            startActivity(Intent(this, TurnPageActivity::class.java))
        }
    }
}

enum class ImagesTypeEnum(val descriptor: String, @DrawableRes val imageId: Int) {
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

enum class MatrixBasedScaleTypeEnum(val descriptor: String) {
    CENTER("Center"),
    CENTER_INSIDE("Center Inside"),
    CENTER_CROP("Center Crop"),
    START_CROP("Start Crop"),
    END_CROP("End Crop"),
    FIT_CENTER("Fit Center"),
    FIT_START("Fit Start"),
    FIT_END("Fit End"),
    FIT_XY("Fit XY");

    override fun toString(): String {
        return descriptor
    }

    companion object {
        fun getEnum(value: String): MatrixBasedScaleTypeEnum {
            return values().first { it.descriptor == value }
        }
    }
}

enum class SizeTypeEnum(
    val descriptor: String,
    val layoutParams: Pair<Int, Int>
) {
    MATCH_PARENT("All Match Parent",
        Pair(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)),
    WRAP_CONTENT("All Wrap Content",
        Pair(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)),
    MATCH_PARENT_WRAP_CONTENT("Height Match - Width Wrap",
        Pair(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)),
    WRAP_CONTENT_MATCH_PARENT("Height Wrap - Width Match",
        Pair(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));

    override fun toString(): String {
        return descriptor
    }

    companion object {
        fun getEnum(value: String): SizeTypeEnum {
            return values().first { it.descriptor == value }
        }
    }
}
