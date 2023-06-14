package ar.edu.unlam.mobile2


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ar.edu.unlam.mobile2.navigation.AppNavigation
import ar.edu.unlam.mobile2.pantallaHome.data.SensorDeMovimiento
import ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel.HomeViewModel
import com.example.compose.AppTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<HomeViewModel>()
    private val sensor = SensorDeMovimiento(this)
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        Log.i("MainActivity", "onCreate")
        setContent {
            AppTheme() {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation(viewModel, sensor)
                }
            }
            requestLocationPermissions()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MainActivity.REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permisos de ubicación concedidos
                getCurrentLocation()
            } else {
                // Permiso de ubicación denegado
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun requestLocationPermissions() {
        val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
        val isLocationPermissionGranted =
            ContextCompat.checkSelfPermission(this, locationPermission) ==
                    PackageManager.PERMISSION_GRANTED

        if (isLocationPermissionGranted) {
            getCurrentLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(locationPermission),
                MainActivity.REQUEST_LOCATION_PERMISSION
            )
        }
    }

    fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                MainActivity.REQUEST_LOCATION_PERMISSION
            )
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val result = LatLng(location.latitude, location.longitude)
                    viewModel.setCurrentLocation(result)
                } else {
                    // La ubicación es nula
                    Toast.makeText(this, "La ubicación es nula", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception: Exception ->
                // Error al obtener la ubicación
                Toast.makeText(
                    this,
                    "Error al obtener la ubicación: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 123
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