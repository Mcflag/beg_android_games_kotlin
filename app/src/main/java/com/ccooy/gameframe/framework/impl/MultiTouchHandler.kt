package com.ccooy.gameframe.framework.impl

import android.view.MotionEvent
import android.view.View
import com.ccooy.gameframe.framework.Input.TouchEvent
import com.ccooy.gameframe.framework.impl.Pool.PoolObjectFactory


class MultiTouchHandler(view: View, internal var scaleX: Float, internal var scaleY: Float) : TouchHandler {

    var isTouched = BooleanArray(MAX_TOUCHPOINTS)
    var touchX = IntArray(MAX_TOUCHPOINTS)
    var touchY = IntArray(MAX_TOUCHPOINTS)
    var id = IntArray(MAX_TOUCHPOINTS)
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
    var touchEventsBuffer: MutableList<TouchEvent> = ArrayList()

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
            val action = event.action and MotionEvent.ACTION_MASK
            val pointerIndex = event.action and MotionEvent.ACTION_POINTER_ID_MASK shr MotionEvent.ACTION_POINTER_ID_SHIFT
            val pointerCount = event.pointerCount
            var touchEvent: TouchEvent?
            for (i in 0 until MAX_TOUCHPOINTS) {
                if (i >= pointerCount) {
                    isTouched[i] = false
                    id[i] = -1
                    continue
                }
                val pointerId = event.getPointerId(i)
                if (event.action != MotionEvent.ACTION_MOVE && i != pointerIndex) {
                    // if it's an up/down/cancel/out event, mask the id to see if we should process it for this touch
                    // point
                    continue
                }
                when (action) {
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                        touchEvent = touchEventPool.newObject()
                        touchEvent!!.type = TouchEvent.TOUCH_DOWN
                        touchEvent!!.pointer = pointerId
                        touchX[i] = (event.getX(i) * scaleX).toInt()
                        touchEvent!!.x = touchX[i]
                        touchY[i] = (event.getY(i) * scaleY).toInt()
                        touchEvent!!.y = touchY[i]
                        isTouched[i] = true
                        id[i] = pointerId
                        touchEventsBuffer.add(touchEvent)
                    }

                    MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
                        touchEvent = touchEventPool.newObject()
                        touchEvent!!.type = TouchEvent.TOUCH_UP
                        touchEvent!!.pointer = pointerId
                        touchX[i] = (event.getX(i) * scaleX).toInt()
                        touchEvent!!.x = touchX[i]
                        touchY[i] = (event.getY(i) * scaleY).toInt()
                        touchEvent!!.y = touchY[i]
                        isTouched[i] = false
                        id[i] = -1
                        touchEventsBuffer.add(touchEvent)
                    }

                    MotionEvent.ACTION_MOVE -> {
                        touchEvent = touchEventPool.newObject()
                        touchEvent!!.type = TouchEvent.TOUCH_DRAGGED
                        touchEvent!!.pointer = pointerId
                        touchX[i] = (event.getX(i) * scaleX).toInt()
                        touchEvent!!.x = touchX[i]
                        touchY[i] = (event.getY(i) * scaleY).toInt()
                        touchEvent!!.y = touchY[i]
                        isTouched[i] = true
                        id[i] = pointerId
                        touchEventsBuffer.add(touchEvent)
                    }
                }
            }
            return true
        }
    }

    override fun isTouchDown(pointer: Int): Boolean {
        synchronized(this) {
            val index = getIndex(pointer)
            return if (index < 0 || index >= MAX_TOUCHPOINTS)
                false
            else
                isTouched[index]
        }
    }

    override fun getTouchX(pointer: Int): Int {
        synchronized(this) {
            val index = getIndex(pointer)
            return if (index < 0 || index >= MAX_TOUCHPOINTS)
                0
            else
                touchX[index]
        }
    }

    override fun getTouchY(pointer: Int): Int {
        synchronized(this) {
            val index = getIndex(pointer)
            return if (index < 0 || index >= MAX_TOUCHPOINTS)
                0
            else
                touchY[index]
        }
    }

    // returns the index for a given pointerId or -1 if no index.
    private fun getIndex(pointerId: Int): Int {
        for (i in 0 until MAX_TOUCHPOINTS) {
            if (id[i] == pointerId) {
                return i
            }
        }
        return -1
    }

    companion object {
        private val MAX_TOUCHPOINTS = 10
    }
}