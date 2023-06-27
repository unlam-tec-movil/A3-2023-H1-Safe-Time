package ar.edu.unlam.mobile2.pantallaHome.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import ar.edu.unlam.mobile2.data.room.model.ContactsFromPhone
import ar.edu.unlam.mobile2.data.room.model.MarcadorEntity
import ar.edu.unlam.mobile2.dialogQR.QRDialog
import ar.edu.unlam.mobile2.navigation.AppScreens
import ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel.HomeViewModel
import ar.edu.unlam.mobile2.pantallaMapa.ui.Bottombar
import ar.edu.unlam.mobile2.pantallaMapa.ui.Toolbar
import ar.edu.unlam.mobile2.pantallaMapa.ui.viewmodel.MapViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel, mapViewModel: MapViewModel) {

    Scaffold(
        topBar = { Toolbar(navController) },
        bottomBar = { Bottombar(navController, viewModel, mapViewModel) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(0.5.dp),
            contentAlignment = Alignment.Center
        ) {
            ContentHome(navController, viewModel, mapViewModel)

        }
    }
}

@Composable
fun ContentHome(
    navController: NavController,
    viewModel: HomeViewModel,
    mapViewModel: MapViewModel
) {

    val contacts by viewModel.contactosEmergencia.observeAsState(initial = emptyList())

    val ubicacion by viewModel.marcadoresFav.observeAsState(initial = emptyList())

    val isDialogShow by viewModel.isDialogShown.observeAsState(initial = false)

    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_CALL) }

    viewModel.recargarMarcadoresFavoritos()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            context.startActivity(intent)
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            TextoContactos()
        }

        item {

            FilaContactos(contacts,
                onClickAgregar = {
                    viewModel.definirPestaña(0)
                    navController.navigate(route = AppScreens.ContactListScreen.route)
                },

                onClickLlamar = {
                    intent.data = Uri.parse("tel:${it}")
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CALL_PHONE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        context.startActivity(Intent.createChooser(intent, "Llamar"))
                    } else {
                        launcher.launch(Manifest.permission.CALL_PHONE)
                    }
                },
                onClickEliminarContacto = {
                    if (!it.esDefault) {
                        viewModel.eliminarContactoEmergencia(it)
                    } else {
                        Toast.makeText(
                            context.applicationContext,
                            "IMPOSIBLE ELIMINAR",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                onClickCompartirContacto = {
                    viewModel.informacionACompartir(it)
                    viewModel.onCompartirClick()
                }
            )
        }

        item {
            TextoUbicaciones()
        }

        item {

            FilaUbicaciones(ubicacion,
                onClickAgregarUbi = {
                    viewModel.definirPestaña(1)
                    navController.navigate(route = AppScreens.ContactListScreen.route)
                },
                onClickIrMapa = {
                    mapViewModel.nuevaUbicacionSeleccionadaEnMapa(it)
                    navController.navigate(route = AppScreens.MapScreen.route)
                },
                onClickEliminarUbicacion = {
                    viewModel.desmarcarFavorito(it)
                },
                onClickCompartirUbicacion = {
                    viewModel.informacionACompartir(it)
                    viewModel.onCompartirClick()
                }

            )

        }

        item {
            Divider(modifier = Modifier.width(360.dp), thickness = 2.dp)
        }

        item {
            if (isDialogShow) {
                QRDialog(
                    onDismiss = { viewModel.onDismissDialog() },
                    info = viewModel.infoQr
                )
            }

        }

        item {
            Button(onClick = { viewModel.openScanClick() }) {
                Text(text = "SCANEAR QR")

            }
        }

    }

}


@Composable
fun TextoContactos() {
    Text(
        text = "Contactos de emergencia",
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp),
        fontFamily = FontFamily.SansSerif,
        textAlign = TextAlign.Start,
        fontSize = 28.sp,
        color = MaterialTheme.colorScheme.onBackground,
        textDecoration = TextDecoration.Underline,
        style = TextStyle(fontWeight = FontWeight.Bold)

    )
}

@Composable
fun FilaContactos(
    contacts: List<ContactsFromPhone>,
    onClickAgregar: () -> Unit,
    onClickLlamar: (numero: String) -> Unit,
    onClickEliminarContacto: (contacto: ContactsFromPhone) -> Unit,
    onClickCompartirContacto: (contacto: ContactsFromPhone) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        items(contacts) {
            ContactItem(
                contact = it,
                onClickLlamar = { onClickLlamar(it.number) },
                onClickEliminarContacto = { onClickEliminarContacto(it) },
                onClickCompartirContacto = { onClickCompartirContacto(it) })
        }

        item {
            IconButton(
                onClick = {
                    onClickAgregar()
                },
                modifier = Modifier
                    .padding(4.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(70.dp)
                    )
                    .clip(shape = RoundedCornerShape(70.dp))
            ) {
                Icon(Icons.Default.Add, contentDescription = "agregar")
            }
        }
    }
}


@Composable
fun ContactItem(
    contact: ContactsFromPhone,
    onClickLlamar: () -> Unit,
    onClickEliminarContacto: () -> Unit,
    onClickCompartirContacto: () -> Unit
) {

    Box(Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .size(width = 145.dp, height = 180.dp)
                .clip(RoundedCornerShape(20.dp)),
            elevation = CardDefaults.elevatedCardElevation(10.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
        ) {
            Box {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(contact.imagen),
                        contentDescription = contact.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(64.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 1.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.background)
                            .border(2.dp, MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                    )

                    Text(
                        text = contact.name,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = contact.number,
                        color = Color.White,
                        modifier = Modifier
                            .padding(4.dp)
                            .align(Alignment.CenterHorizontally),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    IconButton(
                        onClick = {
                            onClickLlamar()
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Icon(
                            Icons.Default.Call,
                            contentDescription = "Llamar",
                            modifier = Modifier.size(45.dp)
                        )
                    }
                }
            }
        }
        MenuOpciones(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(end = 8.dp),
            modifierIcon = Modifier.align(Alignment.TopEnd),
            onClickEliminar = { onClickEliminarContacto() },
            onClickCompartir = { onClickCompartirContacto() }
        )
    }
}


@Composable
fun MenuOpciones(
    modifier: Modifier,
    modifierIcon: Modifier,
    onClickEliminar: () -> Unit,
    onClickCompartir: () -> Unit
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    if (isMenuExpanded) {
        Box(
            modifier = modifier
        ) {
            DropdownMenu(
                expanded = true,
                onDismissRequest = { isMenuExpanded = false }
            ) {
                DropdownMenuItem(onClick = {
                    onClickCompartir()
                    isMenuExpanded = false
                }) {
                    Text("Compartir QR")
                }
                DropdownMenuItem(onClick = {
                    onClickEliminar()
                    isMenuExpanded = false
                }) {
                    Text("Eliminar")
                }
            }
        }
    }
    IconButton(
        onClick = { isMenuExpanded = !isMenuExpanded },
        modifier = modifierIcon
    ) {
        Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
    }
}

@Composable
fun TextoUbicaciones() {
    Text(
        text = "Ubicaciones Rapidas",
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp),
        fontFamily = FontFamily.SansSerif,
        textAlign = TextAlign.Start,
        fontSize = 28.sp,
        color = MaterialTheme.colorScheme.onBackground,
        textDecoration = TextDecoration.Underline,
        style = TextStyle(fontWeight = FontWeight.Bold)

    )
}

@Composable
fun FilaUbicaciones(
    ubicacion: List<MarcadorEntity>,
    onClickAgregarUbi: () -> Unit,
    onClickEliminarUbicacion: (ubicacion: MarcadorEntity) -> Unit,
    onClickIrMapa: (ubicacion: MarcadorEntity) -> Unit,
    onClickCompartirUbicacion: (ubicacion: MarcadorEntity) -> Unit
) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(ubicacion) {
            UbicationItem(
                ubicacion = it,
                onClickIrMapa = { onClickIrMapa(it) },
                onClickEliminarUbicacion = { onClickEliminarUbicacion(it) },
                onClickCompartirUbicacion = { onClickCompartirUbicacion(it) }
            )
        }

        item {
            IconButton(
                onClick = {
                    onClickAgregarUbi()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(70.dp)
                    )
                    .clip(shape = RoundedCornerShape(70.dp))
            ) {
                Icon(Icons.Default.Add, contentDescription = "agregar")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UbicationItem(
    ubicacion: MarcadorEntity,
    onClickIrMapa: () -> Unit,
    onClickEliminarUbicacion: () -> Unit,
    onClickCompartirUbicacion: () -> Unit
) {

    Box(modifier = Modifier.fillMaxSize()) {

        Card(
            modifier = Modifier
                .size(width = 145.dp, height = 180.dp)
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.Center),
            elevation = CardDefaults.elevatedCardElevation(10.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
            onClick = {}
        ) {
            Spacer(modifier = Modifier.padding(top = 20.dp))

            Text(
                text = ubicacion.nombre,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = ubicacion.direccion,
                color = Color.White,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            IconButton(
                onClick = {
                    onClickIrMapa()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = "Llamar",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
        MenuOpciones(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(end = 8.dp),
            modifierIcon = Modifier.align(Alignment.TopEnd),
            onClickEliminar = { onClickEliminarUbicacion() },
            onClickCompartir = { onClickCompartirUbicacion() })
    }
}

