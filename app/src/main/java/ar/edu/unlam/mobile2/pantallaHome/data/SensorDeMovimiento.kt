package ar.edu.unlam.mobile2.pantallaHome.data

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel.HomeViewModel


class SensorDeMovimiento(val context: Context) : SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private val viewModel = HomeViewModel()

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


    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {

            val sides = event.values[0]
            //val upDown = event.values[1]

            val estaQuieto = sides.toInt() == 0

            if (sides.toInt() >= 20) {
                Log.d("SENSOR ACTIVATION", "valor:${viewModel.sensorState.value}")
                viewModel.onSensorActivation()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    fun detenerSensor() {
        sensorManager.unregisterListener(this)
        viewModel.onSensorDesactivation()
    }
}