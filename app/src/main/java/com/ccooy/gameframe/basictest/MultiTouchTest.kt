package com.ccooy.gameframe.basictest

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.widget.TextView
import android.os.Bundle
import android.view.View.OnTouchListener
import android.support.v7.app.AppCompatActivity
import android.view.View

@SuppressLint("ClickableViewAccessibility")
class MultiTouchTest : AppCompatActivity(), OnTouchListener {
    private var builder = StringBuilder()
    lateinit var textView: TextView
    private var x = FloatArray(10)
    private var y = FloatArray(10)
    private var touched = BooleanArray(10)
    private var id = IntArray(10)

    private fun updateTextView() {
        builder.setLength(0)
        for (i in 0..9) {
            builder.append(touched[i])
            builder.append(", ")
            builder.append(id[i])
            builder.append(", ")
            builder.append(x[i])
            builder.append(", ")
            builder.append(y[i])
            builder.append("\n")
        }
        textView.text = builder.toString()
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView = TextView(this)
        textView.text = "Touch and drag (multiple fingers supported)!"
        textView.setOnTouchListener(this)
        setContentView(textView)
        for (i in 0..9) {
            id[i] = -1
        }
        updateTextView()
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val action = event.action and MotionEvent.ACTION_MASK
        val pointerIndex = event.action and MotionEvent.ACTION_POINTER_ID_MASK shr MotionEvent.ACTION_POINTER_ID_SHIFT
        val pointerCount = event.pointerCount
        for (i in 0..9) {
            if (i >= pointerCount) {
                touched[i] = false
                id[i] = -1
                continue
            }
            if (event.action != MotionEvent.ACTION_MOVE && i != pointerIndex) {
                // if it's an up/down/cancel/out event, mask the id to see if we should process it for this touch point
                continue
            }
            val pointerId = event.getPointerId(i)
            when (action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                    touched[i] = true
                    id[i] = pointerId
                    x[i] = event.getX(i).toInt().toFloat()
                    y[i] = event.getY(i).toInt().toFloat()
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_OUTSIDE, MotionEvent.ACTION_CANCEL -> {
                    touched[i] = false
                    id[i] = -1
                    x[i] = event.getX(i).toInt().toFloat()
                    y[i] = event.getY(i).toInt().toFloat()
                }

                MotionEvent.ACTION_MOVE -> {
                    touched[i] = true
                    id[i] = pointerId
                    x[i] = event.getX(i).toInt().toFloat()
                    y[i] = event.getY(i).toInt().toFloat()
                }
            }
        }

        updateTextView()
        return true
    }
}