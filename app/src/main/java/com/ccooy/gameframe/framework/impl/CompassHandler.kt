package com.ccooy.gameframe.framework.impl

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.hardware.SensorEventListener


class CompassHandler(context: Context) : SensorEventListener {
    var yaw: Float = 0.toFloat()
        internal set
    var pitch: Float = 0.toFloat()
        internal set
    var roll: Float = 0.toFloat()
        internal set
    var mAccelerometer: Sensor
    var mMagnetometer: Sensor
    var mLastAccelerometer = FloatArray(3)
    var mLastMagnetometer = FloatArray(3)
    var mLastAccelerometerSet = false
    var mLastMagnetometerSet = false
    var mR = FloatArray(9)
    var mOrientation = FloatArray(3)

    init {
        val manager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mMagnetometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }


    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // nothing to do here
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor == mAccelerometer) {
            System.arraycopy(
                event.values, 0, mLastAccelerometer,
                0, event.values.size
            )
            mLastAccelerometerSet = true
        } else if (event.sensor == mMagnetometer) {
            System.arraycopy(
                event.values, 0,
                mLastMagnetometer, 0, event.values.size
            )
            mLastMagnetometerSet = true
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(
                mR, null,
                mLastAccelerometer, mLastMagnetometer
            )
            SensorManager.getOrientation(mR, mOrientation)
            yaw = mOrientation[0]
            pitch = mOrientation[1]
            roll = mOrientation[2]
        }
    }
}