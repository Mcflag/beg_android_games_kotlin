package com.ccooy.gameframe.basictest

import android.os.Bundle
import android.content.Intent
import android.widget.ArrayAdapter
import android.app.ListActivity
import android.view.View
import android.widget.ListView

class MainActivity : ListActivity() {
    private var tests = arrayOf(
        "LifeCycleTest",
        "SingleTouchTest",
        "MultiTouchTest",
        "KeyTest",
        "AccelerometerTest",
        "AssetsTest",
        "ExternalStorageTest",
        "SoundPoolTest",
        "MediaPlayerTest",
        "FullScreenTest",
        "RenderViewTest",
        "ShapeTest",
        "BitmapTest",
        "FontTest",
        "SurfaceViewTest"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, tests
        )
    }

    override fun onListItemClick(
        list: ListView, view: View, position: Int,
        id: Long
    ) {
        super.onListItemClick(list, view, position, id)
        val testName = tests[position]
        try {
            val clazz = Class.forName("com.ccooy.gameframe.basictest.$testName")
            val intent = Intent(this, clazz)
            startActivity(intent)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

    }
}
