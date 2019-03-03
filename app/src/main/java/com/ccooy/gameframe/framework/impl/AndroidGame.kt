package com.ccooy.gameframe.framework.impl

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import com.ccooy.gameframe.framework.*


abstract class AndroidGame : AppCompatActivity(), Game {
    lateinit var renderView: AndroidFastRenderView
    override lateinit var graphics: Graphics
    override lateinit var audio: Audio
    override lateinit var input: Input
    override lateinit var fileIO: FileIO
    lateinit var screen: Screen

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val frameBufferWidth = if (isLandscape) 420 else 380
        val frameBufferHeight = if (isLandscape) 380 else 420
        val frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565)

        val displaymetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displaymetrics)

        val scaleX = frameBufferWidth.toFloat() / displaymetrics.widthPixels
        val scaleY = frameBufferHeight.toFloat() / displaymetrics.heightPixels

        renderView = AndroidFastRenderView(this, frameBuffer)
        graphics = AndroidGraphics(assets, frameBuffer)
        fileIO = AndroidFileIO(this)
        audio = AndroidAudio(this)
        input = AndroidInput(this, renderView, scaleX, scaleY)
        screen = getStartScreen()
        setContentView(renderView)

    }

    override fun onResume() {
        super.onResume()
        screen.resume()
        renderView.resume()
    }

    override fun onPause() {
        super.onPause()
        renderView.pause()
        screen.pause()

        if (isFinishing)
            screen.dispose()
    }

    override fun getCurrentScreen(): Screen {
        return screen
    }

    override fun setCurrentScreen(value: Screen) {
        if (value == null)
            throw IllegalArgumentException("Screen must not be null")

        this.screen.pause()
        this.screen.dispose()
        value.resume()
        value.update(0.toFloat())
        this.screen = value
    }

}