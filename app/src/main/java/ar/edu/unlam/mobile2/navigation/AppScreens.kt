package ar.edu.unlam.mobile2.navigation

sealed class AppScreens(val route:String){
    object HomeScreen:AppScreens("home_screen")
    object MapScreen:AppScreens("map_screen")
    object ContactListScreen:AppScreens("contacts")
}
