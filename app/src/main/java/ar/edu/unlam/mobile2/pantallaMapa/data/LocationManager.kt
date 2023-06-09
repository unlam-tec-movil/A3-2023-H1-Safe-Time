package ar.edu.unlam.mobile2.pantallaMapa.data

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import ar.edu.unlam.mobile2.ui.MainActivity.Companion.REQUEST_LOCATION_PERMISSION
import com.google.android.gms.location.LocationServices

@Composable
fun GeolocationExample() {
    val context = LocalContext.current
    val fusedLocationClient = remember(context) {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val locationState = remember { mutableStateOf<Location?>(null) }

    LaunchedEffect(Unit) {
        if (hasLocationPermission(context)) {
            // Permiso concedido, obtener la ubicación actual
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                            }
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        locationState.value = it
                    }
                }
                .addOnFailureListener { exception: Exception ->
                    // Error al obtener la ubicación
                    Toast.makeText(
                        context,
                        "Error al obtener la ubicación: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            // Permiso denegado, solicitar permisos
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    locationState.value?.let { location ->
        Text("Latitud: ${location.latitude}, Longitud: ${location.longitude}")
    }
}

private fun hasLocationPermission(context: Context): Boolean {
    return ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}