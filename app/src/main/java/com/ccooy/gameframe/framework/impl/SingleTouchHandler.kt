package com.ccooy.gameframe.framework.impl

import android.view.MotionEvent
import android.view.View
import com.ccooy.gameframe.framework.Input.TouchEvent
import com.ccooy.gameframe.framework.impl.Pool.PoolObjectFactory


class SingleTouchHandler(view: View, internal var scaleX: Float, internal var scaleY: Float) : TouchHandler {
    var isTouched: Boolean = false
    var touchX: Int = 0
    var touchY: Int = 0
    var touchEventPool: Pool<TouchEvent>
    override var touchEvents: MutableList<TouchEvent> = ArrayList()
        get() {
            synchronized(this) {
                val len = touchEvents.size
                for (i in 0 until len)
                    touchEventPool.free(touchEvents[i])
                touchEvents.clear()
                touchEvents.addAll(touchEventsBuffer)
                touchEventsBuffer.clear()
                return touchEvents
            }
        }
    var touchEventsBuffer: MutableList<TouchEvent> = ArrayList<TouchEvent>()

    init {
        val factory = object : PoolObjectFactory<TouchEvent> {
            override fun createObject(): TouchEvent {
                return TouchEvent()
            }
        }
        touchEventPool = Pool<TouchEvent>(factory, 100)
        view.setOnTouchListener(this)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        synchronized(this) {
            val touchEvent = touchEventPool.newObject()
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    touchEvent!!.type = TouchEvent.TOUCH_DOWN
                    isTouched = true
                }
                MotionEvent.ACTION_MOVE -> {
                    touchEvent!!.type = TouchEvent.TOUCH_DRAGGED
                    isTouched = true
                }
                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                    touchEvent!!.type = TouchEvent.TOUCH_UP
                    isTouched = false
                }
            }

            touchX = (event.x * scaleX).toInt()
            touchEvent!!.x = touchX
            touchY = (event.y * scaleY).toInt()
            touchEvent.y = touchY
            touchEventsBuffer.add(touchEvent)

            return true
        }
    }

    override fun isTouchDown(pointer: Int): Boolean {
        synchronized(this) {
            return if (pointer == 0)
                isTouched
            else
                false
        }
    }

    override fun getTouchX(pointer: Int): Int {
        synchronized(this) {
            return touchX
        }
    }

    override fun getTouchY(pointer: Int): Int {
        synchronized(this) {
            return touchY
        }
    }

}