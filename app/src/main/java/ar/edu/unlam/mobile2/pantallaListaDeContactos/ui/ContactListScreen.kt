package ar.edu.unlam.mobile2.pantallaListaDeContactos.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.material.Tab
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.unlam.mobile2.navigation.AppScreens
import ar.edu.unlam.mobile2.pantallaHome.domain.model.Adress
import ar.edu.unlam.mobile2.pantallaHome.domain.model.Contact
import ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel.HomeViewModel
import ar.edu.unlam.mobile2.pantallaMapa.ui.Bottombar
import ar.edu.unlam.mobile2.pantallaMapa.ui.Toolbar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(navController: NavController, viewModel: HomeViewModel) {

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

            TabContactosDirecciones(navController)
        }
    }
}

@Composable
fun ContactListView() {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(getContactos()) { contacto ->
            ItemContacto(contacto = contacto)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemContacto(contacto: Contact) {

    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:${contacto.telefono}")

    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.inversePrimary),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp)
            .onFocusEvent { },
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        onClick = {}

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
                onClick = { context.startActivity(intent) },
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

    @Composable
    fun AdressListView(navController: NavController) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(getDirecciones()) { adress ->
                ItemDireccion(adress = adress, navController)
            }

        }
    }

    @Composable
    fun ItemDireccion(adress: Adress,navController: NavController) {

        Card(
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.inversePrimary),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)

        ) {
            Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {

                Column(
                    Modifier
                        .padding(8.dp)
                        .weight(3f)
                ) {
                    Text(
                        text = adress.nombre,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = adress.direccion,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                IconButton(
                    onClick = { navController.navigate(route = AppScreens.MapScreen.route) },
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "Llamar",
                        modifier = Modifier.size(45.dp)
                    )

                }
            }
        }
    }


        fun getContactos(): List<Contact> {
            return Contact.contacts
        }

        private fun getDirecciones(): List<Adress> {
            return Adress.adress
        }


        @Composable
        fun TabContactosDirecciones(navController: NavController) {

            var tabIndex by remember { mutableStateOf(0) }

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
                    0 -> ContactListView()
                    1 -> AdressListView(navController)
                }
            }
        }

