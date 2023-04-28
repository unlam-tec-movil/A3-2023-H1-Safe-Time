package ar.edu.unlam.mobile2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile2.HomeScreen
import ar.edu.unlam.mobile2.pantallaListaDeContactos.ui.ContactListScreen
import ar.edu.unlam.mobile2.pantallaMapa.PantallaMapa
import ar.edu.unlam.mobile2.pantallaMapa.ViewContainer

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination =AppScreens.HomeScreen.route){
        composable(route = AppScreens.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(route = AppScreens.MapScreen.route){
            PantallaMapa(navController)
        }
        composable(route = AppScreens.ContactListScreen.route){
            ContactListScreen(navController)
        }
    }
}