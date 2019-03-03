package com.ccooy.gameframe.framework.impl

import android.graphics.Bitmap
import com.ccooy.gameframe.framework.Graphics.PixmapFormat
import com.ccooy.gameframe.framework.Pixmap

class AndroidPixmap(var bitmap: Bitmap, override var format: PixmapFormat) : Pixmap {

    override val width: Int
        get() = bitmap.width

    override val height: Int
        get() = bitmap.height

    override fun dispose() {
        bitmap.recycle()
    }
}
