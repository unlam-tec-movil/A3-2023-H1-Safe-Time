package ar.edu.unlam.mobile2


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import ar.edu.unlam.mobile2.navigation.AppNavigation
import ar.edu.unlam.mobile2.pantallaHome.data.SensorDeMovimiento
import ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel.HomeViewModel
import com.example.compose.AppTheme


class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<HomeViewModel>()
    private val sensor = SensorDeMovimiento(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("MainActivity", "onCreate")
        setContent {
            AppTheme() {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation(viewModel, sensor)
                }
            }
        }
    }

    override fun onResume() {
        sensor.iniciarSensor()
        super.onResume()
    }

    override fun onPause() {
        sensor.detenerSensor()
        super.onPause()
    }

}