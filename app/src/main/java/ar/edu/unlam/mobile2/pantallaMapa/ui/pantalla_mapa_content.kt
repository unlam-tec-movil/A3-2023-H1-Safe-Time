package ar.edu.unlam.mobile2.pantallaMapa.ui

import android.location.Geocoder
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FloatingActionButton
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import ar.edu.unlam.mobile2.R
import ar.edu.unlam.mobile2.data.room.model.MarcadorEntity
import ar.edu.unlam.mobile2.navigation.AppScreens
import ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel.HomeViewModel
import ar.edu.unlam.mobile2.pantallaMapa.data.BottomNavItem
import ar.edu.unlam.mobile2.pantallaMapa.ui.viewmodel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch


@Composable
fun PantallaMapa(
    navController: NavController,
    homeViewModel: HomeViewModel,
    mapViewModel: MapViewModel
) {
    val polylineOptions by mapViewModel.polylineOptions.observeAsState()
    val currentLocation by mapViewModel.userLocation.observeAsState()
    val destino by mapViewModel.ubicacionMapa.observeAsState()
    val nuevoMarker by mapViewModel.nuevoMarker.observeAsState(LatLng(0.0, 0.0))

    currentLocation?.let {
        destino?.let { it1 ->
            ViewContainer(
                navController, homeViewModel, mapViewModel, it, polylineOptions,
                LatLng(it1.latitud, it1.longitud), nuevoMarker
            )
        }
    }
}


@Composable
fun MapScreen(
    mapViewModel: MapViewModel,
    destino: LatLng,
    currentLocation: LatLng,
    polylineOptions: PolylineOptions?, nuevoMarker: LatLng?
) {
    val initialUbication = LatLng(currentLocation.latitude, currentLocation.longitude)

    mapViewModel.geocoder = Geocoder(LocalContext.current)

    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.NORMAL,
                isMyLocationEnabled = true
            )
        )
    }

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                rotationGesturesEnabled = false,
                myLocationButtonEnabled = true
            )
        )
    }

    val scope = rememberCoroutineScope()
    val posicionCamara = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialUbication, 15f)
    }

    Box(
        Modifier
            .padding(5.dp)
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
            cameraPositionState = posicionCamara,
            properties = mapProperties,
            uiSettings = uiSettings,
            onMapLongClick = {
                mapViewModel.getDireccion(it)
                Log.i("MAP_LOG", "Te encontras en: ${mapViewModel.nuevaDireccion}")
                scope.launch {
                    if (it != LatLng(0.0, 0.0)) {
                        posicionCamara.animate(CameraUpdateFactory.newLatLng(it))
                        mapViewModel.setNuevoMarker(it)
                        mapViewModel.isActivadoNuevoMarcador()
                    }
                }
            },
            onMapClick = {
                if (mapViewModel.isAgregarNuevoMarcador.value) {
                    scope.launch {
                        mapViewModel.isDesactivadoNuevoMarcador()
                    }
                }
            }
        ) {

            LaunchedEffect(currentLocation) {

                if (currentLocation != LatLng(0.0, 0.0))
                    posicionCamara.animate(CameraUpdateFactory.newLatLng(currentLocation))
            }

            LaunchedEffect(destino) {

                if (destino != LatLng(0.0, 0.0))
                    posicionCamara.animate(CameraUpdateFactory.newLatLng(destino))
            }

            if (mapViewModel.isAgregarNuevoMarcador.value) {

                Marker(
                    title = mapViewModel.nuevaDireccion,
                    state = MarkerState(nuevoMarker!!),
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                )
            }

            if (polylineOptions != null) {
                val polylinePoints =
                    polylineOptions.points.map { LatLng(it.latitude, it.longitude) }
                val currentPolylinePoints = polylinePoints.filter { it != initialUbication }
                Polyline(
                    points = currentPolylinePoints,
                    color = Color(0xFF4285F4),
                    width = 5.dp.value
                )

                Marker(state = MarkerState(destino), icon = BitmapDescriptorFactory.defaultMarker())
            }
            Circle(
                center = initialUbication,
                radius = 10.0,
                fillColor = Color(0x664285F4),
                strokeWidth = 2.dp.value,
                strokeColor = Color(0xFF4285F4)
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewContainer(
    navController: NavController,
    viewModel: HomeViewModel,
    mapViewModel: MapViewModel,
    currentLocation: LatLng,
    polylineOptions: PolylineOptions?,
    destino: LatLng,
    nuevoMarker: LatLng?

) {

    LocalContext.current.applicationContext
    var mUbicacionSeleccionada by remember {
        mutableStateOf(mapViewModel.marcadores.value?.get(1))
    }

    Scaffold(
        topBar = { Toolbar(navController) },
        bottomBar = { Bottombar(navController, viewModel, mapViewModel) }) {


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                mUbicacionSeleccionada =
                    mapViewModel.marcadores.value?.let { it1 ->
                        selectorDeUbicacionesRegistradas(
                            it1,
                            viewModel, mapViewModel
                        )
                    }
                MapScreen(
                    mapViewModel,
                    destino ?: LatLng(0.0, 0.0),
                    currentLocation,
                    polylineOptions,
                    nuevoMarker
                )
                Spacer(modifier = Modifier.size(width = 0.dp, height = 5.dp))
            }

            if (mapViewModel.isAgregarNuevoMarcador.value) {

                mapViewModel.nombreMarcador = textoAgregarMarcador(
                    isShowButton = mapViewModel.isAgregarNuevoMarcador.value,
                    agregarMarcadorAlaBDD = {
                        if (nuevoMarker != null) {
                            mapViewModel.agregarNuevoMarcador(
                                MarcadorEntity(
                                    nombre = mapViewModel.nombreMarcador,
                                    nuevoMarker.latitude,
                                    nuevoMarker.longitude,
                                    direccion = mapViewModel.nuevaDireccion
                                )
                            )
                        }
                        viewModel.definirPestaña(1)
                        mapViewModel.isDesactivadoNuevoMarcador()
                        navController.navigate(AppScreens.ContactListScreen.route)
                    }
                )

            }
        }
    }
}


@Composable
fun Bottombar(navController: NavController, viewModel: HomeViewModel, mapViewModel: MapViewModel) {
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
                            "home_screen" -> {
                                mapViewModel.isDesactivadoNuevoMarcador()
                                navController.navigate(route = AppScreens.HomeScreen.route)
                            }

                            "map_screen" -> {
                                navController.navigate(route = AppScreens.MapScreen.route)
                            }

                            "list_screen" -> {
                                mapViewModel.isDesactivadoNuevoMarcador()
                                navController.navigate(route = AppScreens.ContactListScreen.route)
                            }

                            "infoQr_screen" -> navController.navigate(route = AppScreens.InfoQrScreen.route)
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
fun Toolbar(navController: NavController) {
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
                description = "Settings Icon",
                onClick = { navController.navigate(route = AppScreens.InfoQrScreen.route) }
            )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun selectorDeUbicacionesRegistradas(
    listaMarcadores: List<MarcadorEntity>,
    viewModel: HomeViewModel, mapViewModel: MapViewModel,
): MarcadorEntity? {

    var mExpanded by remember { mutableStateOf(false) }

    val options: List<MarcadorEntity> = listaMarcadores

    val mSelectedUbi by mapViewModel.ubicacionMapa.observeAsState(
        initial = mapViewModel.marcadores.value?.get(
            1
        )
    )
    var mSelectedText by remember { mutableStateOf(listaMarcadores[0].nombre) }

    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (mExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)) {

        mSelectedUbi?.let {
            OutlinedTextField(
                value = it.nombre,
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
        }

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
                        // mSelectedUbi = opcion
                        mapViewModel.nuevaUbicacionSeleccionadaEnMapa(opcion)

                    },
                    text = { Text(text = opcion.nombre) })
            }
        }
    }
    return mSelectedUbi
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun textoAgregarMarcador(
    isShowButton: Boolean,
    agregarMarcadorAlaBDD: () -> Unit
): String {

    var nuevoNombre by remember {
        mutableStateOf("")
    }


    val keyboard = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clip(shape = RectangleShape)
    ) {
        AnimatedVisibility(
            visible = isShowButton,
            enter = slideInVertically(),
            exit = slideOutVertically(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(end = 40.dp)
        ) {

            OutlinedTextField(
                value = nuevoNombre,
                onValueChange = {
                    nuevoNombre = it
                },
                label = { Text(text = "Nombre") },
                singleLine = true,
                keyboardActions = KeyboardActions(onDone = {
                    keyboard?.hide()
                })
            )

            FloatingActionButton(
                backgroundColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    agregarMarcadorAlaBDD()
                },
                modifier = Modifier
                    .padding(top = 70.dp)
                    .size(150.dp, 40.dp)
                    .align(Alignment.BottomCenter),
            ) {
                Text(
                    text = "Guardar marcador",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
    return nuevoNombre
}

