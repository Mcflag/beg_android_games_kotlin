package com.ccooy.gameframe.basictest

import android.Manifest
import android.widget.Toast
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.TextView
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import java.io.*


class ExternalStorageTest : AppCompatActivity() {
    companion object {
        private const val REQUEST_READ_EXTERNAL_STORAGE = 123
        private const val REQUEST_WRITE_EXTERNAL_STORAGE = 123
    }

    lateinit var textView: TextView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView = TextView(this)
        setContentView(textView)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_WRITE_EXTERNAL_STORAGE
            )

        } else {
            readExternalStorage()
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_READ_EXTERNAL_STORAGE -> for (i in permissions.indices) {
                val permission = permissions[i]
                val grantResult = grantResults[i]

                if (permission == Manifest.permission.READ_EXTERNAL_STORAGE || permission == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        readExternalStorage()
                    } else {
                        Toast.makeText(this@ExternalStorageTest, "Permission Denied", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun readExternalStorage() {
        val state = Environment.getExternalStorageState()
        if (state != Environment.MEDIA_MOUNTED) {
            textView.text = "No external storage mounted"
        } else {
            val externalDir = Environment.getExternalStorageDirectory()
            val textFile = File(
                externalDir.absolutePath
                        + File.separator + "text.txt"
            )
            try {
                writeTextFile(textFile, "This is a test. Roger")
                val text = readTextFile(textFile)
                textView.text = text
                if (!textFile.delete()) {
                    textView.text = "Couldn't remove temporary file"
                }
            } catch (e: IOException) {
                textView.text = "Something went wrong! " + e.message
            }

        }
    }

    @Throws(IOException::class)
    private fun writeTextFile(file: File, text: String) {
        val writer = BufferedWriter(FileWriter(file))
        writer.write(text)
        writer.close()
    }

    @Throws(IOException::class)
    private fun readTextFile(file: File): String {
        var text = StringBuilder()
        file.readLines().forEach {
            text.append(it)
            text.append("\n")
        }

        return text.toString()
    }

}