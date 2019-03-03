package com.ccooy.gameframe.framework.impl

import android.graphics.Bitmap
import android.graphics.Rect
import android.view.SurfaceView


class AndroidFastRenderView(var game: AndroidGame, var framebuffer: Bitmap) : SurfaceView(game), Runnable {
    var renderThread: Thread? = null

    @Volatile
    internal var running = false

    fun resume() {
        running = true
        renderThread = Thread(this)
        renderThread!!.start()
    }

    override fun run() {
        val dstRect = Rect()
        var startTime = System.nanoTime()
        while (running) {
            if (!holder.surface.isValid)
                continue

            val deltaTime = (System.nanoTime() - startTime) / 1000000000.0f
            startTime = System.nanoTime()

            game.getCurrentScreen().update(deltaTime)
            game.getCurrentScreen().present(deltaTime)

            val canvas = holder.lockCanvas()
            canvas.getClipBounds(dstRect)
            canvas.drawBitmap(framebuffer, null, dstRect, null)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun pause() {
        running = false
        while (true) {
            try {
                renderThread!!.join()
                return
            } catch (e: InterruptedException) {
                // retry
            }

        }
    }
}