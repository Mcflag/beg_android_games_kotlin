package com.ccooy.gameframe.framework



interface Game {
    val input: Input

    val fileIO: FileIO

    val graphics: Graphics

    val audio: Audio

    fun getCurrentScreen(): Screen

    fun getStartScreen(): Screen

    fun setCurrentScreen(value: Screen)
}