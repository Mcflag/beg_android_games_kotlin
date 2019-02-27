package com.ccooy.gameframe.framework.impl

import android.media.MediaPlayer
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer.OnCompletionListener
import com.ccooy.gameframe.framework.Music
import java.io.IOException


class AndroidMusic(assetDescriptor: AssetFileDescriptor) : Music, OnCompletionListener {
    var mediaPlayer: MediaPlayer = MediaPlayer()
    var isPrepared = false

    override var isLooping: Boolean
        get() = mediaPlayer.isLooping
        set(isLooping) {
            mediaPlayer.isLooping = isLooping
        }

    override val isPlaying: Boolean
        get() = mediaPlayer.isPlaying

    override val isStopped: Boolean
        get() = !isPrepared

    init {
        try {
            mediaPlayer.setDataSource(
                assetDescriptor.fileDescriptor,
                assetDescriptor.startOffset,
                assetDescriptor.length
            )
            mediaPlayer.prepare()
            isPrepared = true
            mediaPlayer.setOnCompletionListener(this)
        } catch (e: Exception) {
            throw RuntimeException("Couldn't load music")
        }

    }

    override fun dispose() {
        if (mediaPlayer.isPlaying)
            mediaPlayer.stop()
        mediaPlayer.release()
    }

    override fun pause() {
        if (mediaPlayer.isPlaying)
            mediaPlayer.pause()
    }

    override fun play() {
        if (mediaPlayer.isPlaying)
            return
        try {
            synchronized(this) {
                if (!isPrepared)
                    mediaPlayer.prepare()
                mediaPlayer.start()
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun setVolume(volume: Float) {
        mediaPlayer.setVolume(volume, volume)
    }

    override fun stop() {
        mediaPlayer.stop()
        synchronized(this) {
            isPrepared = false
        }
    }

    override fun onCompletion(player: MediaPlayer) {
        synchronized(this) {
            isPrepared = false
        }
    }
}