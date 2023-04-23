package ar.edu.unlam.mobile2

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile2.pantallaHome.domain.model.Contact


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun HomeScreen(){

    Box(
        Modifier
            .fillMaxSize()
            .padding(5.dp)){
        Content(
            Modifier
                .align(Alignment.Center)
                .fillMaxSize())
        Spacer(modifier = Modifier.fillMaxHeight(1f))

    }

}


@Composable
fun Content(modifier:Modifier){


    Spacer(modifier = Modifier.height(10.dp))
    ContactList()

    }


/*@Composable
fun UbicationList() {
    val contacts= Contact.contacts

    LazyColumn(
        modifier = Modifier
            .height(60.dp)
            .background(Color.Gray),
        horizontalAlignment = Alignment.Start
    ) {
        items(contacts){
            ContactItem(contact = it)
        }
    }
}*/


//@Preview(showBackground = true)
@Composable
fun ContactList() {
    val contacts= Contact.contacts
LazyColumn(){

item {
    Text(text = "CONTACTOS DE EMERGENCIA",
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        fontFamily = FontFamily.Monospace,
        textAlign = TextAlign.Center,
        fontSize = 24.sp

            )
}

    item {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)    ) {
            items(contacts){
                ContactItem(contact = it)
            }
        }
    }

    item {
        Text(text = "UBICACIONES RAPIDAS",
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center,
            fontSize = 24.sp

        )
    }

    item {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)    ) {
            items(contacts){
                UbicationItem(contact = it)
            }
        }
    }
}

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactItem(contact: Contact) {

    Card(onClick = { /*TODO*/ }) {
        Column(
            modifier = Modifier
                .height(165.dp)

                .background(color = Color.Red),
        ) {
            Image(
                painter = painterResource(contact.imagen),
                contentDescription = contact.nombre,
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = contact.nombre, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = contact.telefono, color = Color.White, modifier = Modifier.padding(4.dp))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Spacer(modifier = Modifier.weight(3f))
            IconButton(
                onClick = { /* Acción de la llamada */ },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(Icons.Default.Call, contentDescription = "Llamar" )
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UbicationItem(contact: Contact) {

    Card(onClick = { /*TODO*/ }) {
        Column(
            modifier = Modifier
                .height(165.dp)

                .background(color = Color.Green),
        ) {
            Image(
                painter = painterResource(contact.imagen),
                contentDescription = contact.nombre,
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = contact.nombre, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = contact.telefono, color = Color.White, modifier = Modifier.padding(4.dp))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Spacer(modifier = Modifier.weight(3f))
            IconButton(
                onClick = { /* Acción de la llamada */ },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = "Llamar" )
            }
        }
    }

}

