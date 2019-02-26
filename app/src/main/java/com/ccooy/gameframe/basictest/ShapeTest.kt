package com.ccooy.gameframe.basictest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.view.WindowManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window


class ShapeTest : AppCompatActivity() {
    internal inner class RenderView(context: Context) : View(context) {
        var paint: Paint

        init {
            paint = Paint()
        }

        override fun onDraw(canvas: Canvas) {
            canvas.drawRGB(255, 255, 255)
            paint.color = Color.RED
            canvas.drawLine(0f, 0f, canvas.width - 1f, canvas.height - 1f, paint)

            paint.style = Style.STROKE
            paint.color = -0xff0100
            canvas.drawCircle(canvas.width / 2f, canvas.height / 2f, 40f, paint)

            paint.style = Style.FILL
            paint.color = 0x770000ff
            canvas.drawRect(100f, 100f, 200f, 200f, paint)
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