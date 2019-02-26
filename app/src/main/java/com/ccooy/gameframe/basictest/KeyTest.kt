package com.ccooy.gameframe.basictest

import android.widget.TextView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.View

class KeyTest : AppCompatActivity(), View.OnKeyListener {
    private var builder = StringBuilder()
    lateinit var textView: TextView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView = TextView(this)
        textView.text = "Press keys (if you have some)!"
        textView.setOnKeyListener(this)
        textView.isFocusableInTouchMode = true
        textView.requestFocus()
        setContentView(textView)
    }

    override fun onKey(view: View, keyCode: Int, event: KeyEvent): Boolean {
        builder.setLength(0)
        when (event.action) {
            KeyEvent.ACTION_DOWN -> builder.append("down, ")
            KeyEvent.ACTION_UP -> builder.append("up, ")
        }
        builder.append(event.keyCode)
        builder.append(", ")
        builder.append(event.unicodeChar)
        val text = builder.toString()
        Log.d("KeyTest", text)
        textView.text = text

        return event.keyCode != KeyEvent.KEYCODE_BACK
    }
}