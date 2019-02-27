package com.ccooy.gameframe.framework.impl

import android.content.Context
import android.preference.PreferenceManager
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.os.Environment
import com.ccooy.gameframe.framework.FileIO
import java.io.*


class AndroidFileIO(internal var context: Context) : FileIO {
    var assets: AssetManager = context.assets
    var externalStoragePath: String = Environment.getExternalStorageDirectory().absolutePath + File.separator

    val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(context)

    @Throws(IOException::class)
    override fun readAsset(fileName: String): InputStream {
        return assets.open(fileName)
    }

    @Throws(IOException::class)
    override fun readFile(fileName: String): InputStream {
        return FileInputStream(externalStoragePath + fileName)
    }

    @Throws(IOException::class)
    override fun writeFile(fileName: String): OutputStream {
        return FileOutputStream(externalStoragePath + fileName)
    }
}