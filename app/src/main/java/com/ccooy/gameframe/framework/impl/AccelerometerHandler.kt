package com.ccooy.gameframe.framework.impl

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.hardware.SensorEventListener


class AccelerometerHandler(context: Context) : SensorEventListener {
    var accelX: Float = 0.toFloat()
        internal set
    var accelY: Float = 0.toFloat()
        internal set
    var accelZ: Float = 0.toFloat()
        internal set

    init {
        val manager = context
            .getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size != 0) {
            val accelerometer = manager.getSensorList(
                Sensor.TYPE_ACCELEROMETER
            )[0]
            manager.registerListener(
                this, accelerometer,
                SensorManager.SENSOR_DELAY_GAME
            )
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // nothing to do here
    }

    override fun onSensorChanged(event: SensorEvent) {
        accelX = event.values[0]
        accelY = event.values[1]
        accelZ = event.values[2]
    }
}