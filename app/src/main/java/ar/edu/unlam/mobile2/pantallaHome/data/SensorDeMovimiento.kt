package ar.edu.unlam.mobile2.pantallaHome.data

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject


class SensorDeMovimiento @Inject constructor(val context: Context) : SensorEventListener {

    private lateinit var sensorManager: SensorManager
    var sensorState = MutableLiveData(false)

    fun iniciarSensor() {

        sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { sensor ->
            sensorManager.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }


    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {

            val sides = event.values[0]

            if (sensorState.value == false && sides.toInt() >= 20) {
                onSensorActivation()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    fun detenerSensor() {
        sensorManager.unregisterListener(this)
        onSensorDesactivation()
    }

    fun onSensorDesactivation() {
        sensorState.value = false
    }

    fun onSensorActivation() {
        sensorState.value = true
    }
}

