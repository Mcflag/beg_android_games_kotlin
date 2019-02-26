package com.ccooy.gameframe.basictest

import android.content.Context
import android.view.SurfaceView
import android.view.WindowManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window


class SurfaceViewTest : AppCompatActivity() {
    private lateinit var renderView: FastRenderView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        renderView = FastRenderView(this)
        setContentView(renderView)
    }

    override fun onResume() {
        super.onResume()
        renderView.resume()
    }

    override fun onPause() {
        super.onPause()
        renderView.pause()
    }

    inner class FastRenderView(context: Context) : SurfaceView(context), Runnable {
        private var renderThread: Thread? = null

        @Volatile
        var running = false

        fun resume() {
            running = true
            renderThread = Thread(this)
            renderThread!!.start()
        }

        override fun run() {
            while (running) {
                if (!holder.surface.isValid)
                    continue

                val canvas = holder.lockCanvas()
                canvas.drawRGB(255, 0, 0)
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
}