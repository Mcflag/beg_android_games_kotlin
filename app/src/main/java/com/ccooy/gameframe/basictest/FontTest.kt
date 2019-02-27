package com.ccooy.gameframe.basictest

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager

class FontTest : AppCompatActivity() {

    inner class RenderView(context: Context) : View(context) {
        var paint: Paint
        var font: Typeface
        var bounds: Rect = Rect()

        init {
            paint = Paint()
            font = Typeface.createFromAsset(context.assets, "font.ttf")
        }

        override fun onDraw(canvas: Canvas) {
            canvas.drawRGB(0, 0, 0)
            paint.color = Color.YELLOW
            paint.typeface = font
            paint.textSize = 28f
            paint.textAlign = Paint.Align.CENTER
            canvas.drawText("这是个测试This is a test!", canvas.width / 2f, 100f, paint)

            val text = "这是另一个测试This is another test o_O"
            paint.color = Color.WHITE
            paint.textSize = 18f
            paint.textAlign = Paint.Align.LEFT
            paint.getTextBounds(text, 0, text.length, bounds)
            canvas.drawText(text, canvas.width.toFloat() - bounds.width(), 140f, paint)
            invalidate()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(RenderView(this))
    }

}