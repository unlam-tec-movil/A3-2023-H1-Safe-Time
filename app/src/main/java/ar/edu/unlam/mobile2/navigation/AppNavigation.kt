package ar.edu.unlam.mobile2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile2.HomeScreen
import ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel.HomeViewModel
import ar.edu.unlam.mobile2.pantallaListaDeContactos.ui.ContactListScreen
import ar.edu.unlam.mobile2.pantallaMapa.PantallaMapa

@Composable
fun AppNavigation(viewModel: HomeViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination =AppScreens.HomeScreen.route){
        composable(route = AppScreens.HomeScreen.route){
            HomeScreen(navController,viewModel)
        }
        composable(route = AppScreens.MapScreen.route){
            PantallaMapa(navController)
        }
        composable(route = AppScreens.ContactListScreen.route){
            ContactListScreen(navController,viewModel)
        }
    }
}