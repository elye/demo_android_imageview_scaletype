package com.example.matrixscale

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import com.example.matrixscale.MainActivity.Companion.IMAGE_TYPE_KEY
import kotlinx.android.synthetic.main.activity_entry.*


class EntryActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
                ImagesType.values())

        image_selector.adapter = dataAdapter

        button_show.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(IMAGE_TYPE_KEY, ImagesType.getEnum(
                    image_selector.selectedItem.toString()))
            }
            startActivity(intent)
        }
    }
}

enum class ImagesType(val descriptor: String, @DrawableRes val imageId : Int) {
    BIGGER("Bigger Image - Fall", R.drawable.fall),
    SMALLER("Smaller Image - Lion", R.drawable.lion),
    TALLER("Taller Image - Tree", R.drawable.tree),
    LONGER("Longer Image - Bridge", R.drawable.bridge);

    override fun toString(): String {
        return descriptor
    }

    companion object {
        fun getEnum(value: String): ImagesType {
            return values().first { it.descriptor == value }
        }
    }
}
