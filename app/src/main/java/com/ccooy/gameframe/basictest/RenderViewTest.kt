package com.ccooy.gameframe.basictest

import android.content.Context
import android.graphics.Canvas
import android.view.WindowManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import java.util.*


class RenderViewTest : AppCompatActivity() {
    internal inner class RenderView(context: Context) : View(context) {
        private var rand = Random()

        override fun onDraw(canvas: Canvas) {
            canvas.drawRGB(
                rand.nextInt(256), rand.nextInt(256),
                rand.nextInt(256)
            )
            invalidate()
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(RenderView(this))
    }
}