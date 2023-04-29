package ar.edu.unlam.mobile2.pantallaListaDeContactos.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.unlam.mobile2.pantallaHome.domain.model.Adress
import ar.edu.unlam.mobile2.pantallaHome.domain.model.Contact
import ar.edu.unlam.mobile2.pantallaMapa.Bottombar
import ar.edu.unlam.mobile2.pantallaMapa.Toolbar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(navController: NavController) {

    var isContactSelected by remember { mutableStateOf(true) }

    Scaffold(
        topBar = { Toolbar() },
        bottomBar = { Bottombar(navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            isContactSelected = barraContactosDirecciones()
            if (isContactSelected) ContactListView() else AdressListView()

        }
    }
}

@Preview
@Composable
fun ContactListView() {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(getContactos()) { contacto ->
            ItemContacto(contacto = contacto)
        }

    }
}

@Composable
fun ItemContacto(contacto: Contact) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary), modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)

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
                    fontSize = 22.sp

                )
                Text(
                    text = contacto.telefono,
                    fontSize = 18.sp
                )
            }

            Icon(
                Icons.Default.Call,
                contentDescription = "Llamar",
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )


        }
    }
}

@Preview
@Composable
fun Prev() {

    AdressListView()
}

@Composable
fun AdressListView() {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(getDirecciones()) { adress ->
            ItemDireccion(adress = adress)
        }

    }
}

@Composable
fun ItemDireccion(adress: Adress) {

    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary), modifier = Modifier
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
                    fontSize = 24.sp
                )
                Text(
                    text = adress.direccion,
                    fontSize = 18.sp
                )
            }

            Icon(
                Icons.Rounded.LocationOn,
                contentDescription = "Navegar",
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .size(35.dp)
            )


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
private fun barraContactosDirecciones(): Boolean {

    val colorSelected = MaterialTheme.colorScheme.inversePrimary
    val colorUnselected = MaterialTheme.colorScheme.onPrimary

    var isContactSelected by remember { mutableStateOf(true) }

    Row(
        Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(bottom = 5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "CONTACTOS", fontSize = 20.sp, modifier = Modifier.clickable {
                isContactSelected = true
            },
            color = if (isContactSelected) colorSelected else colorUnselected
        )

        Text(
            text = "DIRECCIONES", fontSize = 20.sp, modifier = Modifier.clickable {
                isContactSelected = false
            },
            color = if (isContactSelected) colorUnselected else colorSelected
        )
    }
    return isContactSelected

}
