package ar.edu.unlam.mobile2.pantallaMapa

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile2.R
import ar.edu.unlam.mobile2.pantallaMapa.data.BottomNavItem

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun DefaultPreview() {

    ViewContainer()
}


@Preview
@Composable
fun PantallaMapa() {

/*    val markerUNLAM = LatLng(-34.6690101,-58.5637967)
    GoogleMap(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)){

        rememberMarkerState(position = markerUNLAM)
    }*/
    ViewContainer()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewContainer() {

    /*val scafoldState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()*/
    val context = LocalContext.current.applicationContext
    var listaState by rememberSaveable { mutableStateOf(true) }

    Scaffold(topBar = { Toolbar() }, bottomBar = { Bottombar() }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .padding(top = 15.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .background(
                        Color(R.color.safe_light_purple),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)

            ) {

                TextoViajes()

                Row(
                    modifier = Modifier
                        .background(
                            Color(R.color.safe_purple),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ListaDirecciones(listaState)
                    Icon(
                        imageVector = Icons.Rounded.ArrowDropDown,
                        contentDescription = "Abrir lista",
                        tint = Color.White,
                        modifier = Modifier
                            .size(35.dp)
                            .clickable {
                                listaState = !listaState
                            }
                    )
                }
            }
            //PantallaMapa()
        }
    }


}

@Composable
fun Bottombar(/*navController: NavController*/) {


    //val backStackEntry = navController.currentBackStackEntryAsState()
    val context = LocalContext.current
    val bottomNavItem = listOf(

        BottomNavItem(
            name = "Map",
            route = "map",
            icon = Icons.Rounded.LocationOn
        ),
        BottomNavItem(
            name = "Home",
            route = "home",
            icon = Icons.Rounded.Home
        ),
        BottomNavItem(
            name = "Agenda",
            route = "contacts",
            Icons.Rounded.Call
        )
    )

    NavigationBar(
        containerColor = Color(R.color.safe_purple),
        modifier = Modifier.size(width = 400.dp, height = 60.dp)
    ) {


        bottomNavItem.forEach { item ->
            /*val selected = item.route == backbackStackEntry.value?.destination?.route*/

            NavigationBarItem(
                selected = /*selected*/ false,
                onClick = {
                    Toast.makeText(context, "Click ${item.name}", Toast.LENGTH_SHORT).show()
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.name,
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar() {
    val context = LocalContext.current
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SAFETIME",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Default,
                    modifier = Modifier.padding(end = 10.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.safetimelogo),
                    contentDescription = "TODO",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(35.dp)
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(Color(R.color.safe_purple))/*TopAppBarDefaults.smallTopAppBarColors(containerColor = Blue)*/,
        actions = {
            //Setting Icon
            TopAppBarActionButton(
                imageVector = Icons.Rounded.Settings,
                description = "Settings Icon"
            ) {
                Toast.makeText(context, "Setting click", Toast.LENGTH_SHORT).show()
            }
        }
    )
}

@Composable
fun TopAppBarActionButton(
    imageVector: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    IconButton(onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = description,
            tint = Color.White,
            modifier = Modifier.size(35.dp)
        )
    }
}


@Composable
private fun TextoViajes() {

    Text(
        text = "VIAJANDO A",
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
    )
}

@Composable
private fun ListaDirecciones(state: Boolean) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(start = 4.dp)
                .background(shape = RoundedCornerShape(20.dp), color = Color.Unspecified)
                .size(width = 80.dp, if (state) 30.dp else 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        item {
            ItemsDirecciones("Casa")
            ItemsDirecciones("Escuela")
            ItemsDirecciones("Hospital")
            ItemsDirecciones("Abuela")
            ItemsDirecciones("Mama")
        }
    }

}

@Composable
private fun ItemsDirecciones(direccion: String) {

    Text(
        text = direccion.uppercase(),
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        color = Color.White,
        modifier = Modifier.clickable { /*MECANISMO DE CAMBIO DE MAPA*/ }
    )
}
