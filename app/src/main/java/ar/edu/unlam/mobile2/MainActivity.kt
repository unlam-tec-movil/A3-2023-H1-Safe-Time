package ar.edu.unlam.mobile2


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import ar.edu.unlam.mobile2.navigation.AppNavigation
import ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel.HomeViewModel
import com.example.compose.AppTheme
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes


class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<HomeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //INTEGRACION DE APPCENTER
        /*AppCenter.start(
            application,
            "0a6e59c922abcfd3bc3ded3870661ccfae411c45",
            Analytics::class.java,
            Crashes::class.java
        )*/

        Log.i("MainActivity", "onCreate")
        setContent {
            AppTheme() {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation(viewModel)
                }
            }
        }


    }
}