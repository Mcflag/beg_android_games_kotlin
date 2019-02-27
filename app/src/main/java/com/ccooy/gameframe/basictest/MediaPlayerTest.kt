package com.ccooy.gameframe.basictest

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import java.lang.Exception

class MediaPlayerTest : AppCompatActivity() {
    var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var textView = TextView(this)
        setContentView(textView)

        volumeControlStream = AudioManager.STREAM_MUSIC
        mediaPlayer = MediaPlayer()
        try {
            var descriptor = assets.openFd("music.ogg")
            mediaPlayer?.let {
                it.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
                it.prepare()
                it.isLooping = true
            }
        } catch (e: Exception) {
            textView.text = "Couldn't load music file, " + e.message
            mediaPlayer = null
        }
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer?.start()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
        if (isFinishing) {
            mediaPlayer?.let {
                it.stop()
                it.release()
            }
        }
    }

}