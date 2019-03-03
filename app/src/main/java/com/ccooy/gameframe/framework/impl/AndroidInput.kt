package com.ccooy.gameframe.framework.impl

import android.content.Context
import android.os.Build.VERSION
import android.view.View
import com.ccooy.gameframe.framework.Input
import com.ccooy.gameframe.framework.Input.KeyEvent
import com.ccooy.gameframe.framework.Input.TouchEvent


class AndroidInput(context: Context, view: View, scaleX: Float, scaleY: Float) : Input {
    var accelHandler: AccelerometerHandler = AccelerometerHandler(context)
    var keyHandler: KeyboardHandler = KeyboardHandler(view)
    var touchHandler: TouchHandler =
            if (Integer.parseInt(VERSION.SDK) < 5) {
                SingleTouchHandler(view, scaleX, scaleY)
            } else {
                MultiTouchHandler(view, scaleX, scaleY)
            }

    override fun isKeyPressed(keyCode: Int): Boolean {
        return keyHandler.isKeyPressed(keyCode)
    }

    override fun isTouchDown(pointer: Int): Boolean {
        return touchHandler.isTouchDown(pointer)
    }

    override fun getTouchX(pointer: Int): Int {
        return touchHandler.getTouchX(pointer)
    }

    override fun getTouchY(pointer: Int): Int {
        return touchHandler.getTouchY(pointer)
    }

    override fun getAccelX(): Float {
        return accelHandler.accelX
    }

    override fun getAccelY(): Float {
        return accelHandler.accelY
    }

    override fun getAccelZ(): Float {
        return accelHandler.accelZ
    }

    override fun getKeyEvents(): List<KeyEvent> {
        return keyHandler.getKeyEvents()
    }

    override fun getTouchEvents(): List<TouchEvent> {
        return touchHandler.touchEvents
    }
}