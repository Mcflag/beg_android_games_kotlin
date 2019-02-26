package com.ccooy.gameframe.basictest

import android.widget.TextView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class AssetsTest : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        setContentView(textView)

        val assetManager = this.assets
        var inputStream: InputStream? = null
        try {
            inputStream = assetManager.open("myawesometext.txt")
            val text = loadTextFile(inputStream)
            textView.text = text
        } catch (e: IOException) {
            textView.text = "Couldn't load file"
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    textView.text = "Couldn't close file"
                }

        }
    }

    @Throws(IOException::class)
    fun loadTextFile(inputStream: InputStream): String {
        val byteStream = ByteArrayOutputStream()
        val bytes = ByteArray(4096)
        var len = 0
        while ((inputStream.read(bytes)).also { len = it } > 0)
            byteStream.write(bytes, 0, len)
        return String(byteStream.toByteArray(), Charsets.UTF_8)
    }
}