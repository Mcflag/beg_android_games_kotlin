package com.ccooy.gameframe.framework.impl

import com.ccooy.gameframe.framework.Pixmap
import com.ccooy.gameframe.framework.Graphics.PixmapFormat
import android.content.res.AssetManager
import android.graphics.*
import com.ccooy.gameframe.framework.Graphics
import java.io.IOException
import java.io.InputStream
import android.graphics.Bitmap.Config
import android.graphics.BitmapFactory.Options
import android.graphics.Paint.Style;


class AndroidGraphics(var assets: AssetManager, var frameBuffer: Bitmap) : Graphics {
    var canvas: Canvas = Canvas(frameBuffer)
    var paint: Paint = Paint()
    var srcRect = Rect()
    var dstRect = Rect()
    override val width: Int
        get() = frameBuffer.width

    override val height: Int
        get() = frameBuffer.height

    override fun newPixmap(fileName: String, format: PixmapFormat): Pixmap {
        var format = format
        var config: Config = if (format === PixmapFormat.RGB565)
            Config.RGB_565
        else if (format === PixmapFormat.ARGB4444)
            Config.ARGB_4444
        else
            Config.ARGB_8888

        val options = Options()
        options.inPreferredConfig = config

        var mIn: InputStream? = null
        var bitmap: Bitmap? = null
        try {
            mIn = assets.open(fileName)
            bitmap = BitmapFactory.decodeStream(mIn)
            if (bitmap == null)
                throw RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'")
        } catch (e: IOException) {
            throw RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'")
        } finally {
            if (mIn != null) {
                try {
                    mIn.close()
                } catch (e: IOException) {
                }

            }
        }

        format = if (bitmap!!.config == Config.RGB_565)
            PixmapFormat.RGB565
        else if (bitmap.config == Config.ARGB_4444)
            PixmapFormat.ARGB4444
        else
            PixmapFormat.ARGB8888

        return AndroidPixmap(bitmap, format)
    }

    override fun clear(color: Int) {
        canvas.drawRGB(color and 0xff0000 shr 16, color and 0xff00 shr 8, color and 0xff)
    }

    override fun drawPixel(x: Int, y: Int, color: Int) {
        paint.color = color
        canvas.drawPoint(x.toFloat(), y.toFloat(), paint)
    }

    override fun drawLine(x: Int, y: Int, x2: Int, y2: Int, color: Int) {
        paint.color = color
        canvas.drawLine(x.toFloat(), y.toFloat(), x2.toFloat(), y2.toFloat(), paint)
    }

    override fun drawRect(x: Int, y: Int, width: Int, height: Int, color: Int) {
        paint.color = color
        paint.style = Style.FILL
        canvas.drawRect(x.toFloat(), y.toFloat(), x + width - 1.toFloat(), y + width - 1.toFloat(), paint)
    }

    override fun drawPixmap(pixmap: Pixmap, x: Int, y: Int, srcX: Int, srcY: Int,
                            srcWidth: Int, srcHeight: Int) {
        srcRect.left = srcX
        srcRect.top = srcY
        srcRect.right = srcX + srcWidth - 1
        srcRect.bottom = srcY + srcHeight - 1

        dstRect.left = x
        dstRect.top = y
        dstRect.right = x + srcWidth - 1
        dstRect.bottom = y + srcHeight - 1

        canvas.drawBitmap((pixmap as AndroidPixmap).bitmap, srcRect, dstRect, null)
    }

    override fun drawPixmap(pixmap: Pixmap, x: Int, y: Int) {
        canvas.drawBitmap((pixmap as AndroidPixmap).bitmap, x.toFloat(), y.toFloat(), null)
    }
}