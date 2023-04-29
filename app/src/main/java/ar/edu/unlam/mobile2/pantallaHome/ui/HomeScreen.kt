package ar.edu.unlam.mobile2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.unlam.mobile2.navigation.AppScreens
import ar.edu.unlam.mobile2.pantallaHome.domain.model.Contact
import ar.edu.unlam.mobile2.pantallaMapa.Bottombar
import ar.edu.unlam.mobile2.pantallaMapa.Toolbar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {


    Scaffold(
        topBar = { Toolbar() },
        bottomBar = { Bottombar(navController) }
    ) {
        Box(modifier = Modifier.padding(it)) {
            ContentHome(navController)
        }
    }
}

@Composable
fun ContentHome(navController: NavController) {
    val contacts = Contact.contacts


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Text(
                text = "Contactos de emergencia",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Start,
                fontSize = 24.sp,
                textDecoration = TextDecoration.Underline,
                style = TextStyle(fontWeight = FontWeight.SemiBold)

            )
        }

        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(contacts) {
                    ContactItem(contact = it)
                }
            }
        }

        item {
            Text(
                text = "Ubicaciones Rapidas",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Start,
                fontSize = 24.sp,
                textDecoration = TextDecoration.Underline,
                style = TextStyle(fontWeight = FontWeight.SemiBold)

            )
        }

        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(contacts) {
                    UbicationItem(contact = it, navController)
                }
            }


        }

        item {
            Divider(modifier = Modifier.width(360.dp))
        }
        item {


            Button(
                onClick = { /*TODO*/ }, modifier = Modifier
                    .height(height = 75.dp)
                    .padding(2.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = RoundedCornerShape(8.dp)
            )
            {
                Text(text = "EMERGENCIA", style = TextStyle(fontSize = 25.sp))

            }


        }
    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactItem(contact: Contact) {

    Card(
        modifier = Modifier
            .height(150.dp)
            .width(120.dp)
            .clip(RoundedCornerShape(20.dp))
            ,
        colors = CardDefaults.cardColors(Color(R.color.safe_purple)),
        onClick = {}
    ) {
        Image(

            painter = painterResource(contact.imagen),
            contentDescription = contact.nombre,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .background(Color.Black)
                .border(1.dp, Color.White, CircleShape)

        )
        Text(
            text = contact.nombre,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = contact.telefono,
            color = Color.White,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterHorizontally)
        )
        IconButton(
            onClick = { /* Acción de la llamada */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(Icons.Default.Call, contentDescription = "Llamar")
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UbicationItem(contact: Contact, navController: NavController) {

    Card(
        elevation = CardDefaults.cardElevation(0.dp),
        modifier = Modifier
            .height(150.dp)
            .width(120.dp)
            .clip(RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(Color(R.color.safe_purple)),
        onClick = {}
    ) {
        Image(
            painter = painterResource(contact.imagen),
            contentDescription = contact.nombre,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .border(1.dp, Color.White, CircleShape)
        )
        Text(
            text = contact.nombre,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = contact.telefono,
            color = Color.White,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterHorizontally)
        )
        IconButton(
            onClick = { navController.navigate(route = AppScreens.MapScreen.route) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(Icons.Default.LocationOn, contentDescription = "Llamar")
        }
    }

}



