package com.ccooy.gameframe.basictest

import android.widget.TextView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log


class LifeCycleTest : AppCompatActivity() {
    private var builder = StringBuilder()
    lateinit var textView: TextView

    private fun log(text: String) {
        Log.d("LifeCycleTest", text)
        builder.append(text)
        builder.append('\n')
        textView.text = builder.toString()
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView = TextView(this)
        textView.text = builder.toString()
        setContentView(textView)
        log("created")
    }

    override fun onResume() {
        super.onResume()
        log("resumed")
    }

    override fun onPause() {
        super.onPause()
        log("paused")

        if (isFinishing) {
            log("finishing")
        }
    }
}