package com.ccooy.gameframe.framework

import com.ccooy.gameframe.framework.Graphics.PixmapFormat

interface Pixmap {
    val width: Int

    val height: Int

    val format: PixmapFormat

    fun dispose()
}