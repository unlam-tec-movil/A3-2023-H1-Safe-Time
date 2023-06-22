package ar.edu.unlam.mobile2



import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
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
            getCurrentLocation()
            requestContactPermissions()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permisos de ubicación concedidos
                getCurrentLocation()
                Toast.makeText(this, "Permiso de ubicación concedido", Toast.LENGTH_SHORT).show()
            } else {
                // Permiso de ubicación denegado
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestLocationPermissions() {
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
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    private fun requestContactPermissions() {
        val contactPermission = Manifest.permission.READ_CONTACTS
        val isContactPermissionGranted =
            ContextCompat.checkSelfPermission(this, contactPermission) ==
                    PackageManager.PERMISSION_GRANTED

        if (isContactPermissionGranted) {
            getContactsPhone()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(contactPermission),
                REQUEST_CONTACTS_PERMISSION
            )
        }
    }

    @SuppressLint("Range", "Recycle")
    fun getContactsPhone() {

        val contentResolver: ContentResolver = contentResolver
        val uri: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)

        if (cursor != null) {
            Log.i("CONTACT_PROVIDER_DEMO", "TOTAL of contacts " + cursor.count.toString())
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val contactName =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val contactNumber =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                    viewModel.setContactsFromPhone(contactName, contactNumber)
                    Log.i(
                        "CONTACT_PROVIDER_DEMO",
                        "Name:$contactName, Numero: $contactNumber"
                    )
                }

            }
        } else {
            Toast.makeText(this, "NO SE ENCONTRARON CONTACTOS", Toast.LENGTH_SHORT).show()
        }


    }

    private fun getCurrentLocation() {
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
                REQUEST_LOCATION_PERMISSION
            )
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val result = LatLng(location.latitude, location.longitude)
                    viewModel.setCurrentLocation(result)
                    Toast.makeText(this, "se obtuvo la ubi y esta en el viewmodel", Toast.LENGTH_SHORT).show()
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
        private const val REQUEST_CONTACTS_PERMISSION = 0
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