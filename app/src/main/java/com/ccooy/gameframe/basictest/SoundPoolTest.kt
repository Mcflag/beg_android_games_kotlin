package com.ccooy.gameframe.basictest

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.media.AudioManager
import android.media.SoundPool
import android.widget.TextView
import android.os.Bundle
import android.view.View.OnTouchListener
import android.support.v7.app.AppCompatActivity
import android.view.View
import java.io.IOException

@SuppressLint("ClickableViewAccessibility")
class SoundPoolTest : AppCompatActivity(), OnTouchListener {
    lateinit var soundPool: SoundPool
    private var explosionId = -1

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        textView.setOnTouchListener(this)
        setContentView(textView)
        volumeControlStream = AudioManager.STREAM_MUSIC
        soundPool = SoundPool(20, AudioManager.STREAM_MUSIC, 0)

        try {
            val assetManager = this.assets
            val descriptor = assetManager.openFd("explosion-02.ogg")
            explosionId = soundPool.load(descriptor, 1)
        } catch (e: IOException) {
            textView.text = "Couldn't load sound effect from asset, " + e.message
        }

    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (explosionId != -1) {
                soundPool.play(explosionId, 1f, 1f, 0, 0, 1f)
            }
        }
        return true
    }
}