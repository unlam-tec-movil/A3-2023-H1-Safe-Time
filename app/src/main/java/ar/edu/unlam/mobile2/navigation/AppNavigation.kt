package ar.edu.unlam.mobile2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile2.HomeScreen
import ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel.HomeViewModel
import ar.edu.unlam.mobile2.pantallaListaDeContactos.ui.ContactListScreen
import ar.edu.unlam.mobile2.pantallaMapa.ui.PantallaMapa

@Composable
fun AppNavigation(viewModel: HomeViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination =AppScreens.HomeScreen.route){
        composable(route = AppScreens.HomeScreen.route){
            viewModel.screenUbication = "home_screen"
            HomeScreen(navController,viewModel)
        }
        composable(route = AppScreens.MapScreen.route){
            viewModel.screenUbication = "map_screen"
            PantallaMapa(navController,viewModel)
        }
        composable(route = AppScreens.ContactListScreen.route){
            viewModel.screenUbication = "list_screen"
            ContactListScreen(navController,viewModel)
        }
    }
}