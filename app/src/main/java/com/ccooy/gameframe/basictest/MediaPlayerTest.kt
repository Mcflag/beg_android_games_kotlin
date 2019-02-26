package com.ccooy.gameframe.basictest

import android.os.Bundle
import android.view.Window
import android.view.WindowManager

class MediaPlayerTest : SingleTouchTest() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
    }

}