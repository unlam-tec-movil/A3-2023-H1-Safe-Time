package ar.edu.unlam.mobile2.pantallaMapa.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import ar.edu.unlam.mobile2.R
import ar.edu.unlam.mobile2.navigation.AppScreens
import ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel.HomeViewModel
import ar.edu.unlam.mobile2.pantallaMapa.data.BottomNavItem
import ar.edu.unlam.mobile2.pantallaMapa.data.repository.Marcador
import ar.edu.unlam.mobile2.pantallaMapa.data.repository.MarcadorRepo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState


@Composable
fun PantallaMapa(navController: NavController, viewModel: HomeViewModel) {

    ViewContainer(navController, viewModel)
}

@Composable
fun MapScreen(currentUbication: LatLng) {

    val initialUbication = LatLng(-34.6690101, -58.5637967)
    val mapProperties by remember { mutableStateOf(MapProperties(mapType = MapType.HYBRID)) }
    val uiSettings by remember { mutableStateOf(MapUiSettings(rotationGesturesEnabled = false)) }

    Box(
        Modifier
            .padding(top = 5.dp)
            .background(
                color = MaterialTheme.colorScheme.inversePrimary,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(shape = RoundedCornerShape(percent = 10))
    ) {
        GoogleMap(
            modifier = Modifier
                .size(width = 380.dp, height = 500.dp)
                .padding(8.dp),
            cameraPositionState = cameraUpdate(initialUbication, currentUbication),
            properties = mapProperties,
            uiSettings = uiSettings
        ) {

            Marker(state = MarkerState(currentUbication))
        }
    }
}

private fun cameraUpdate(current : LatLng, next : LatLng) : CameraPositionState{

    if(current != next) {

        return CameraPositionState(position = CameraPosition(next, 16.6f, 0f, 0f))
    }

    return CameraPositionState(position = CameraPosition(current, 16.6f, 0f, 0f))
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewContainer(navController: NavController, viewModel: HomeViewModel) {

    LocalContext.current.applicationContext
    var mUbicacionSeleccionada by remember {
        mutableStateOf(MarcadorRepo.ubicaciones[2])
    }

    Scaffold(topBar = { Toolbar() }, bottomBar = { Bottombar(navController, viewModel) }) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .padding(top = 15.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                mUbicacionSeleccionada = selectorDeUbicacionesRegistradas(MarcadorRepo.ubicaciones)
            }

            item {
                MapScreen(mUbicacionSeleccionada.latLng)
            }

            item {
                Spacer(modifier = Modifier.size(width = 0.dp, height = 5.dp))
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround

                ) {
                    Row(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .size(width = 180.dp, height = 50.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center

                    ) {

                        CartelDistanciaDelPunto(distancia = 0.0)

                    }
                    Row(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .size(width = 180.dp, 50.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center

                    ) {
                        CartelLlegadaEstimada(tiempo = 0)
                    }
                }
            }
        }
    }
}


@Composable
fun Bottombar(navController: NavController, viewModel: HomeViewModel) {

    val context = LocalContext.current
    val bottomNavItem = listOf(

        BottomNavItem(
            name = "Map",
            route = "map_screen",
            icon = Icons.Rounded.LocationOn
        ),
        BottomNavItem(
            name = "Home",
            route = "home_screen",
            icon = Icons.Rounded.Home
        ),
        BottomNavItem(
            name = "Agenda",
            route = "list_screen",
            Icons.Rounded.Call
        )
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 60.dp)
    ) {


        bottomNavItem.forEach { item ->

            NavigationBarItem(
                selected = false,
                onClick = {
                    if (viewModel.screenUbication != item.route) {
                        when (item.route) {
                            "home_screen" -> navController.navigate(route = AppScreens.HomeScreen.route)
                            "map_screen" -> navController.navigate(route = AppScreens.MapScreen.route)
                            "list_screen" -> navController.navigate(route = AppScreens.ContactListScreen.route)
                        }
                    }
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
        colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primary)/*TopAppBarDefaults.smallTopAppBarColors(containerColor = Blue)*/,
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
private fun ItemsDirecciones(direccion: String) {

    Text(
        text = direccion.uppercase(),
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = Modifier.clickable { /*MECANISMO DE CAMBIO DE MAPA*/ }
    )
}

@Composable
private fun CartelDistanciaDelPunto(distancia: Double) {

    Text(
        text = "Distancia del punto: ",
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
    )

    Text(
        text = "$distancia",
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
    )
}

@Composable
private fun CartelLlegadaEstimada(tiempo: Int) {

    Text(
        text = "Llegada estimada: ",
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
    )

    Text(
        text = "$tiempo",
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun selectorDeUbicacionesRegistradas(listaMarcadores: List<Marcador>): Marcador {

    var mExpanded by remember { mutableStateOf(false) }

    val options: List<Marcador> = listaMarcadores

    var mSelectedUbi by remember { mutableStateOf(listaMarcadores[0]) }
    var mSelectedText by remember { mutableStateOf(listaMarcadores[0].nombre) }

    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (mExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)) {

        OutlinedTextField(
            value = mSelectedText,
            onValueChange = { mSelectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    mTextFieldSize = coordinates.size.toSize()
                },
            readOnly = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                placeholderColor = MaterialTheme.colorScheme.primary,
            ),
            label = {
                Text(
                    "Viajar a",
                    color = MaterialTheme.colorScheme.primary
                )
            },
            trailingIcon = {
                Icon(icon, "expanded icon",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
        )

        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
        ) {

            options.forEach { opcion ->
                DropdownMenuItem(
                    onClick = {
                        mSelectedText = opcion.nombre
                        mExpanded = false
                        mSelectedUbi = opcion
                    },
                    text = { Text(text = opcion.nombre) })
            }
        }
    }
    return mSelectedUbi
}