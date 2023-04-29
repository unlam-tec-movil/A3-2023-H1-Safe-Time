package ar.edu.unlam.mobile2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
<<<<<<<<< Temporary merge branch 1
import ar.edu.unlam.mobile2.navigation.AppNavigation
import ar.edu.unlam.mobile2.pantallaMapa.ViewContainer
=========
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import ar.edu.unlam.mobile2.pantallaMapa.PantallaMapa
>>>>>>>>> Temporary merge branch 2

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //INTEGRACION DE APPCENTER
        AppCenter.start(
            application,
            "0a6e59c922abcfd3bc3ded3870661ccfae411c45",
            Analytics::class.java,
            Crashes::class.java
        )

        Log.i("MainActivity", "onCreate")
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                AppNavigation()
            }
        }
    }
}