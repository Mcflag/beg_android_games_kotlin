package com.ccooy.gameframe.basictest

import android.content.Context
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.hardware.Sensor
import android.widget.TextView
import android.os.Bundle
import android.hardware.SensorEventListener
import android.support.v7.app.AppCompatActivity


class AccelerometerTest : AppCompatActivity(), SensorEventListener {
    lateinit var textView: TextView
    private var builder = StringBuilder()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView = TextView(this)
        setContentView(textView)

        val manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size == 0) {
            textView.text = "No accelerometer installed"
        } else {
            val accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER)[0]
            if (!manager.registerListener(
                    this, accelerometer,
                    SensorManager.SENSOR_DELAY_GAME
                )
            ) {
                textView.text = "Couldn't register sensor listener"
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        builder.setLength(0)
        builder.append("x: ")
        builder.append(event.values[0])
        builder.append(", y: ")
        builder.append(event.values[1])
        builder.append(", z: ")
        builder.append(event.values[2])
        textView.text = builder.toString()
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // nothing to do here
    }
}
