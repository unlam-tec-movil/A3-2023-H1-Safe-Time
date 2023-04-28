package ar.edu.unlam.mobile2.pantallaListaDeContactos.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.unlam.mobile2.R
import ar.edu.unlam.mobile2.pantallaHome.domain.model.Contact
import ar.edu.unlam.mobile2.pantallaMapa.Bottombar
import ar.edu.unlam.mobile2.pantallaMapa.Toolbar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContactListScreen(navController: NavController) {

    Scaffold(
        topBar = { Toolbar() },
        bottomBar = { Bottombar(navController) }
    ) {
        ContactListView()
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
    Card(colors = CardDefaults.cardColors(Color.Blue), modifier = Modifier.fillMaxWidth().height(100.dp).padding(8.dp)) {
        Row (modifier = Modifier.padding(8.dp)){
            Image(
                painter = painterResource(contacto.imagen),
                contentDescription = "Imagen del contacto",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(5.dp, Color.Cyan, CircleShape)
                    .weight(1f)

            )

            Column(Modifier.padding(8.dp).weight(3f)) {
                Text(
                    text = contacto.nombre,

                )
                Text(text = contacto.telefono)
            }

            Icon(
                Icons.Default.Call,
                contentDescription = "Llamar",
                modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
            )


        }
    }
}


fun getContactos(): List<Contact> {
    return Contact.contacts
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ItemContacto(
        contacto = Contact(
            "Juan Perez",
            "555-555-1111",
            R.drawable.ic_launcher_foreground
        )
    )
}
