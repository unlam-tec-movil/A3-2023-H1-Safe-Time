package ar.edu.unlam.mobile2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile2.dialogQR.QRDialog
import ar.edu.unlam.mobile2.pantallaConfiguracion.ConfiguracionQRScreen
import ar.edu.unlam.mobile2.pantallaHome.data.SensorDeMovimiento
import ar.edu.unlam.mobile2.pantallaHome.ui.HomeScreen
import ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel.HomeViewModel
import ar.edu.unlam.mobile2.pantallaListaDeContactos.ui.ContactListScreen
import ar.edu.unlam.mobile2.pantallaMapa.ui.PantallaMapa

@Composable
fun AppNavigation(viewModel: HomeViewModel, sensor: SensorDeMovimiento) {

    val navController = rememberNavController()

    val sensorState by sensor.sensorState.observeAsState(false)


    if (sensorState == true) {

        QRDialog(info = viewModel.infoQr) {
            sensor.onSensorDesactivation()
        }

    }

    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.route) {

        composable(route = AppScreens.HomeScreen.route) {
            viewModel.screenUbication = "home_screen"
            HomeScreen(navController, viewModel)
        }
        composable(route = AppScreens.MapScreen.route) {
            viewModel.screenUbication = "map_screen"
            PantallaMapa(navController, viewModel)
        }
        composable(route = AppScreens.ContactListScreen.route) {
            viewModel.screenUbication = "list_screen"
            val tab = viewModel.tabPestañas.value
            if (tab != null) {
                ContactListScreen(navController, viewModel, tab)
            }
        }
        composable(route = AppScreens.InfoQrScreen.route) {
            viewModel.screenUbication = "infoQr_screen"
            ConfiguracionQRScreen(navController, viewModel)
        }
    }
}