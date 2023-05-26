package ar.edu.unlam.mobile2.pantallaListaDeContactos.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Tab
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import ar.edu.unlam.mobile2.navigation.AppScreens
import ar.edu.unlam.mobile2.pantallaHome.data.model.Contact
import ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel.HomeViewModel
import ar.edu.unlam.mobile2.pantallaMapa.data.repository.Marcador
import ar.edu.unlam.mobile2.pantallaMapa.data.repository.MarcadorRepository
import ar.edu.unlam.mobile2.pantallaMapa.ui.Bottombar
import ar.edu.unlam.mobile2.pantallaMapa.ui.Toolbar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(navController: NavController, viewModel: HomeViewModel, tab: Int) {
    val contactList by viewModel.contactos.observeAsState(initial = emptyList())
    val isShowButton by viewModel.isButtomShow.observeAsState(initial = false)
    val textButtonAgregarSeleccionados = remember { viewModel.selectedContacts.value.isNotEmpty()}
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_DIAL)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            context.startActivity(intent)
        }
    }


    Scaffold(
        topBar = { Toolbar(navController) },
        bottomBar = { Bottombar(navController, viewModel) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TabContactosDirecciones(
                navController,
                contactList,
                tab,
                isShowButton ,
                onClickAgregarSeleccionados={viewModel.agregarContactosSeleccionados()},
                onClickLlamar= {

                    intent.data = Uri.parse("tel:${it.telefono}")
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
            textButtonAgregarSeleccionados,
                onContactSelected = {viewModel.contactoSeleccionado(it)},
                onContactUnSelected = {viewModel.contactoDesSeleccionado(it)}
            )
        }
    }
}

@Composable
fun ContactListView(
    contacts: List<Contact>,
    isShowButton: Boolean,
    onClickAgregarSeleccionados: () -> Unit,
    onClickLlamar: (contacto: Contact) -> Unit,
    textButtonAgregarSeleccionados: Boolean,
    onContactSelected: (contact:Contact) -> Unit,
    onContactUnSelected: (contact:Contact) -> Unit

) {

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(contacts) { contacto ->
                ItemContacto(
                    contacto,
                    onClickLlamar={onClickLlamar(contacto)},
                    onContactSelected = { onContactSelected(contacto) },
                    onContactUnSelected= {onContactUnSelected(contacto)}
                )
            }
        }

        BotonAgregarSeleccionados(onClickAgregarSeleccionados, isShowButton,textButtonAgregarSeleccionados)
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun BotonAgregarSeleccionados(onClickAgregarSeleccionados: ()->Unit,isShowButton: Boolean,textButtonAgregarSeleccionados: Boolean ) {

    Box(modifier = Modifier.fillMaxSize()) {


        AnimatedVisibility(
            isShowButton,
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            FloatingActionButton(
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,

                onClick = {
                   onClickAgregarSeleccionados()
                },
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
                    .size(200.dp, 60.dp),

            ) {

                if (textButtonAgregarSeleccionados) {
                    Text(text = "Agregar a contactos de emergencia", textAlign = TextAlign.Center)
                } else {
                    Text(text = "Agregar a ubicaciones rapidas", textAlign = TextAlign.Center)
                }

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemContacto(contacto: Contact,
                 onClickLlamar: () -> Unit,
                 onContactSelected:()-> Unit,
                 onContactUnSelected: () -> Unit
) {

    val isSelected = rememberSaveable { mutableStateOf(false) }

    Card(
        colors = if (isSelected.value) {
            CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
        } else {
            CardDefaults.cardColors(MaterialTheme.colorScheme.inversePrimary)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp)
            .onFocusEvent {},
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        onClick = {
            isSelected.value = !isSelected.value
            if (isSelected.value) {
                onContactSelected()
            } else {
              onContactUnSelected()
            }

        }
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(contacto.imagen),
                contentDescription = "Imagen del contacto",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .weight(1f)
            )

            Column(
                Modifier
                    .padding(8.dp)
                    .weight(3f)
            ) {
                Text(
                    text = contacto.nombre,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer

                )
                Text(
                    text = contacto.telefono,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            IconButton(
                onClick = { onClickLlamar() },
            ) {
                Icon(
                    Icons.Default.Call,
                    contentDescription = "Llamar",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(45.dp)
                )


            }
        }
    }
}
/*
@Composable
fun AdressListView(navController: NavController, viewModel: HomeViewModel) {

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(getDirecciones()) { adress ->
                ItemDireccion(ubicacion = adress, navController, viewModel)
            }

        }
        BotonAgregarSeleccionados(viewModel = viewModel, navController = navController)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDireccion(ubicacion: Marcador, navController: NavController, viewModel: HomeViewModel) {

    val isSelected = rememberSaveable { mutableStateOf(false) }

    Card(
        colors = if (isSelected.value) {
            CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
        } else {
            CardDefaults.cardColors(MaterialTheme.colorScheme.inversePrimary)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        onClick = {
            isSelected.value = !isSelected.value
            if (isSelected.value) {
                viewModel.ubicacionSeleccionada(ubicacion)
            } else {
                viewModel.ubicacionDesSeleccionada(ubicacion)
            }

        }

    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {

            Column(
                Modifier
                    .padding(8.dp)
                    .weight(3f)
            ) {
                Text(
                    text = ubicacion.nombre,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = ubicacion.latLng.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            IconButton(
                onClick = {
                    viewModel.nuevaUbicacionSeleccionadaEnMapa(ubicacion)
                    navController.navigate(route = AppScreens.MapScreen.route) },
            ) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = "Llamar",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(45.dp)
                )

            }
        }
    }
}

private fun getDirecciones(): List<Marcador> {
    return MarcadorRepository.ubicaciones
}
*/

@Composable
fun TabContactosDirecciones(
    navController: NavController,
    conctactList: List<Contact>,
    tab: Int = 0,
    isShowButton: Boolean,
    onClickAgregarSeleccionados: () -> Unit,
    onClickLlamar: (contacto: Contact) -> Unit,
    textButtonAgregarSeleccionados: Boolean,
    onContactSelected: (contact:Contact) -> Unit,
    onContactUnSelected:(contact:Contact)->Unit
) {

    var tabIndex by remember { mutableStateOf(tab) }

    val tabs = listOf("CONTACTOS", "DIRECCIONES")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = tabIndex,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedContentColor = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        }

        when (tabIndex) {
            0 -> {
                ContactListView(conctactList, isShowButton , onClickAgregarSeleccionados ,onClickLlamar,textButtonAgregarSeleccionados, onContactSelected, onContactUnSelected)
            }

           /* 1 -> {
                viewModel.limpiarSeleccionados()
                AdressListView(navController, viewModel)
            }*/
        }
    }
}

