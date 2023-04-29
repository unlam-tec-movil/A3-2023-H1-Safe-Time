package ar.edu.unlam.mobile2.pantallaHome.data

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat

class LocationPermissionHandler(private val context: Context) {
    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION


    fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(context, locationPermission) == PackageManager.PERMISSION_GRANTED
    }

    // Función para solicitar permisos de ubicación
    fun requestLocationPermission(activity: Activity, requestId: Int) {
        ActivityCompat.requestPermissions(activity, arrayOf(locationPermission), requestId)
    }
}


@Composable
fun LocationPermissionScreen(
    permissionHandler: LocationPermissionHandler,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }

    if (permissionHandler.hasLocationPermission()) {
        onPermissionGranted()
    } else {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}



