package com.ccooy.gameframe.basictest

import android.content.Context
import android.view.WindowManager
import android.os.Bundle
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import java.io.IOException


class BitmapTest : AppCompatActivity() {
    internal inner class RenderView(context: Context) : View(context) {
        lateinit var bob565: Bitmap
        lateinit var bob4444: Bitmap
        var dst = Rect()

        init {

            try {
                val assetManager = context.getAssets()
                var inputStream = assetManager.open("bobrgb888.png")
                bob565 = BitmapFactory.decodeStream(inputStream)
                inputStream.close()
                Log.d(
                    "BitmapText",
                    "bobrgb888.png format: " + bob565.config
                )

                inputStream = assetManager.open("bobargb8888.png")
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_4444
                bob4444 = BitmapFactory
                    .decodeStream(inputStream, null, options)
                inputStream.close()
                Log.d(
                    "BitmapText",
                    "bobargb8888.png format: " + bob4444.config
                )

            } catch (e: IOException) {
                // silently ignored, bad coder monkey, baaad!
            } finally {
                // we should really close our input streams here.
            }
        }

        override fun onDraw(canvas: Canvas) {
            canvas.drawRGB(0, 0, 0)
            dst.set(50, 50, 350, 350)
            canvas.drawBitmap(bob565, null, dst, null)
            canvas.drawBitmap(bob4444, 100f, 100f, null)
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