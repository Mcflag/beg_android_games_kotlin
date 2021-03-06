package com.ccooy.gameframe.basictest

import android.view.WindowManager
import android.os.Bundle
import android.view.Window


class FullScreenTest : SingleTouchTest() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
    }

}