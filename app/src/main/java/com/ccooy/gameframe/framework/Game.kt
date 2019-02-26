package com.ccooy.gameframe.framework



interface Game {
    val input: Input

    val fileIO: FileIO

    val graphics: Graphics

    val audio: Audio

    val currentScreen: Screen

    val startScreen: Screen

    fun setScreen(screen: Screen)
}