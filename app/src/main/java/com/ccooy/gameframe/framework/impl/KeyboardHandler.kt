package com.ccooy.gameframe.framework.impl

import android.view.View
import com.ccooy.gameframe.framework.Input.KeyEvent
import com.ccooy.gameframe.framework.impl.Pool.PoolObjectFactory


class KeyboardHandler(view: View) : View.OnKeyListener {
    var pressedKeys = BooleanArray(128)
    var keyEventPool: Pool<KeyEvent>
    var keyEventsBuffer: MutableList<KeyEvent> = ArrayList()
    private var keyEvents: MutableList<KeyEvent> = ArrayList()

    init {
        val factory = object : PoolObjectFactory<KeyEvent> {
            override fun createObject(): KeyEvent {
                return KeyEvent()
            }
        }
        keyEventPool = Pool(factory, 100)
        view.setOnKeyListener(this)
        view.isFocusableInTouchMode = true
        view.requestFocus()
    }

    override fun onKey(v: View, keyCode: Int, event: android.view.KeyEvent): Boolean {
        if (event.action == android.view.KeyEvent.ACTION_MULTIPLE)
            return false

        synchronized(this) {
            val keyEvent = keyEventPool.newObject()
            keyEvent!!.keyCode = keyCode
            keyEvent.keyChar = event.unicodeChar.toChar()
            if (event.action == android.view.KeyEvent.ACTION_DOWN) {
                keyEvent.type = KeyEvent.KEY_DOWN
                if (keyCode > 0 && keyCode < 127)
                    pressedKeys[keyCode] = true
            }
            if (event.action == android.view.KeyEvent.ACTION_UP) {
                keyEvent.type = KeyEvent.KEY_UP
                if (keyCode > 0 && keyCode < 127)
                    pressedKeys[keyCode] = false
            }
            keyEventsBuffer.add(keyEvent)
        }
        return false
    }

    fun isKeyPressed(keyCode: Int): Boolean {
        return if (keyCode < 0 || keyCode > 127) false else pressedKeys[keyCode]
    }

    fun getKeyEvents(): List<KeyEvent> {
        synchronized(this) {
            val len = keyEvents.size
            for (i in 0 until len) {
                keyEventPool.free(keyEvents[i])
            }
            keyEvents.clear()
            keyEvents.addAll(keyEventsBuffer)
            keyEventsBuffer.clear()
            return keyEvents
        }
    }
    
}