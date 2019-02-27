package com.ccooy.gameframe.framework.impl

import com.ccooy.gameframe.framework.Sound
import com.ccooy.gameframe.framework.Music
import android.media.SoundPool
import android.media.AudioManager
import android.app.Activity
import android.content.res.AssetManager
import com.ccooy.gameframe.framework.Audio
import android.media.AudioAttributes
import java.io.IOException


class AndroidAudio(activity: Activity) : Audio {
    var assets: AssetManager
    var soundPool: SoundPool

    init {
        activity.volumeControlStream = AudioManager.STREAM_MUSIC
        this.assets = activity.assets
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        this.soundPool = SoundPool.Builder()
            .setMaxStreams(20)
            .setAudioAttributes(audioAttributes)
            .build()
    }

    override fun newMusic(filename: String): Music {
        try {
            val assetDescriptor = assets.openFd(filename)
            return AndroidMusic(assetDescriptor)
        } catch (e: IOException) {
            throw RuntimeException("Couldn't load music '$filename'")
        }

    }

    override fun newSound(filename: String): Sound {
        try {
            val assetDescriptor = assets.openFd(filename)
            val soundId = soundPool.load(assetDescriptor, 0)
            return AndroidSound(soundPool, soundId)
        } catch (e: IOException) {
            throw RuntimeException("Couldn't load sound '$filename'")
        }

    }
}