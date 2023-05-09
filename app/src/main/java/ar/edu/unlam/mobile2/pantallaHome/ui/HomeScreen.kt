package ar.edu.unlam.mobile2

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.unlam.mobile2.dialogQR.QRDialog
import ar.edu.unlam.mobile2.navigation.AppScreens
import ar.edu.unlam.mobile2.pantallaHome.domain.model.Contact
import ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel.HomeViewModel
import ar.edu.unlam.mobile2.pantallaMapa.Bottombar
import ar.edu.unlam.mobile2.pantallaMapa.Toolbar
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {

    Scaffold(
        topBar = { Toolbar() },
        bottomBar = { Bottombar(navController, viewModel) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(0.5.dp),
            contentAlignment = Alignment.Center
        ) {
            ContentHome(navController, viewModel)
        }
    }
}

@Composable
fun ContentHome(navController: NavController, viewModel: HomeViewModel) {
    val contacts = Contact.contacts


    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            TextoContactos()
        }

        item {
            FilaContactos(contacts, navController)
        }

        item {
            TextoUbicaciones()
        }

        item {
            FilaUbicaciones(contacts, navController)
        }

        item {
            Divider(modifier = Modifier.width(360.dp))
        }
        item {
            BotonEmergencia(viewModel, contacts)
        }
    }

}


@Composable
fun BotonEmergencia(viewModel: HomeViewModel, contacts: List<Contact>) {

    Button(
        onClick = { viewModel.onEmergencyClick() }, modifier = Modifier
            .height(height = 75.dp)
            .padding(2.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        shape = RoundedCornerShape(8.dp)
    )
    {
        Text(text = "EMERGENCIA", style = TextStyle(fontSize = 25.sp))
    }

    if (viewModel.isDialogShown) {
        QRDialog(
            onDismiss = { viewModel.onDismissDialog() },
            info = "${contacts.get(2).nombre} TELEFONO DE CONTACTO ${contacts.get(2).telefono}"
        )
    }
}


@Composable
fun FilaUbicaciones(contacts: List<Contact>, navController: NavController) {
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

        item {
            IconButton(
                onClick = { navController.navigate(route = AppScreens.ContactListScreen.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(70.dp))
                    .clip(shape = RoundedCornerShape(70.dp))
            ) {
                Icon(Icons.Default.Add, contentDescription = "agregar")
            }
        }
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
fun FilaContactos(contacts: List<Contact>, navController: NavController) {

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

        item {
            IconButton(
                onClick = { navController.navigate(route = AppScreens.ContactListScreen.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(70.dp))
                    .clip(shape = RoundedCornerShape(70.dp))
            ) {
                Icon(Icons.Default.Add, contentDescription = "agregar")
            }
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContactItem(contact: Contact) {


    val callPermissionState = rememberPermissionState(Manifest.permission.CALL_PHONE)
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_CALL)
    intent.data = Uri.parse("tel:${contact.telefono}")

    Card(
        modifier = Modifier
            .size(width = 145.dp, height = 170.dp)
            .clip(RoundedCornerShape(20.dp)),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
    ) {

        Spacer(modifier = Modifier.padding(top = 5.dp))

        Image(
            painter = painterResource(contact.imagen),
            contentDescription = contact.nombre,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.CenterHorizontally)
                .padding(1.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background)
                .border(2.dp, MaterialTheme.colorScheme.secondaryContainer, CircleShape)

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
            onClick = {
                if (callPermissionState.status.isGranted) {
                    context.startActivity(intent)
                } else {
                    callPermissionState.launchPermissionRequest()
                }
            },
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
        modifier = Modifier
            .size(width = 140.dp, height = 160.dp)
            .clip(RoundedCornerShape(20.dp)),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
        onClick = {}
    ) {
        Spacer(modifier = Modifier.padding(top = 5.dp))

        Image(
            painter = painterResource(contact.imagen),
            contentDescription = contact.nombre,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.CenterHorizontally)
                .padding(1.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background)
                .border(2.dp, MaterialTheme.colorScheme.secondaryContainer, CircleShape)
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







