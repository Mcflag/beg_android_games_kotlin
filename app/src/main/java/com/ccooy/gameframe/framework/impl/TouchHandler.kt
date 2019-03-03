package com.ccooy.gameframe.framework.impl

import android.view.View.OnTouchListener
import com.ccooy.gameframe.framework.Input.TouchEvent


interface TouchHandler : OnTouchListener {

    val touchEvents: List<TouchEvent>

    fun isTouchDown(pointer: Int): Boolean

    fun getTouchX(pointer: Int): Int

    fun getTouchY(pointer: Int): Int
}