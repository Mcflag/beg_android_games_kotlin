package com.ccooy.gameframe.framework

interface Audio {
    fun newMusic(filename: String): Music

    fun newSound(filename: String): Sound
}
