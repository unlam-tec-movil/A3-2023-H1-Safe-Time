package ar.edu.unlam.mobile2

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ar.edu.unlam.mobile2.pantallaMapa.PantallaMapa
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //INTEGRACION DE APPCENTER
        AppCenter.start(
            Application(),
            "0a6e59c922abcfd3bc3ded3870661ccfae411c45",
            Analytics::class.java,
            Crashes::class.java
        )

        Log.i("MainActivity", "onCreate")
        setContent {
            PantallaMapa()
        }
    }
}