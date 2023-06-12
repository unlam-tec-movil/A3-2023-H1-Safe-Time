package ar.edu.unlam.mobile2.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService


class SensorMovimiento(context: Context) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val gyroscopio : Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    private var lastUpdate: Long = 0
    private var initialRoll: Float = 0f
    private var isTiltedLeft: Boolean = false
    private var isTiltedRight: Boolean = false

    override fun onSensorChanged(event: SensorEvent?) {
        TODO("Not yet implemented")
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }
}
