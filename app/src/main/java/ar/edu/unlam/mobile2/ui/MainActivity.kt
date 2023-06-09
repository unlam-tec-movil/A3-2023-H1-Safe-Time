package ar.edu.unlam.mobile2.ui



import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import ar.edu.unlam.mobile2.navigation.AppNavigation
import ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel.HomeViewModel
import com.example.compose.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<HomeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("MainActivity", "onCreate")
        setContent {
            AppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation(viewModel = viewModel)
                }
            }
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
            // Verificar si se concedió el permiso de ubicación
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this,
                    "Permiso de ubicación concedido",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Permiso de ubicación denegado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
         const val REQUEST_LOCATION_PERMISSION = 123
    }
}

