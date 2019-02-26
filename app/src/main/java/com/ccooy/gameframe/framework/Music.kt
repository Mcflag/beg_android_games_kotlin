package com.ccooy.gameframe.framework

interface Music {

    val isPlaying: Boolean

    val isStopped: Boolean

    var isLooping: Boolean

    fun play()

    fun stop()

    fun pause()

    fun setVolume(volume: Float)

    fun dispose()
}
