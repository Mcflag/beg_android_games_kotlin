package com.ccooy.gameframe.framework.impl

import android.media.SoundPool
import com.ccooy.gameframe.framework.Sound


class AndroidSound(var soundPool: SoundPool, var soundId: Int) : Sound {

    override fun play(volume: Float) {
        soundPool.play(soundId, volume, volume, 0, 0, 1f)
    }

    override fun dispose() {
        soundPool.unload(soundId)
    }
}
