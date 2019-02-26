package com.ccooy.gameframe.basictest

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.widget.TextView
import android.os.Bundle
import android.view.View.OnTouchListener
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View


@SuppressLint("ClickableViewAccessibility")
open class SingleTouchTest : AppCompatActivity(), OnTouchListener {
    private var builder = StringBuilder()
    lateinit var textView: TextView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView = TextView(this)
        textView.text = "Touch and drag (one finger only)!"
        textView.setOnTouchListener(this)
        setContentView(textView)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        builder.setLength(0)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> builder.append("down, ")
            MotionEvent.ACTION_MOVE -> builder.append("move, ")
            MotionEvent.ACTION_CANCEL -> builder.append("cancel, ")
            MotionEvent.ACTION_UP -> builder.append("up, ")
        }
        builder.append(event.x)
        builder.append(", ")
        builder.append(event.y)
        val text = builder.toString()
        Log.d("TouchTest", text)
        textView.text = text
        return true
    }
}