package ar.edu.unlam.mobile2.pantallaConfiguracion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.unlam.mobile2.dialogQR.InfoQR
import ar.edu.unlam.mobile2.navigation.AppScreens
import ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel.HomeViewModel
import ar.edu.unlam.mobile2.pantallaMapa.ui.Bottombar
import ar.edu.unlam.mobile2.pantallaMapa.ui.Toolbar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfiguracionQRScreen(navController:NavController,viewModel:HomeViewModel) {

    val nombre = remember { mutableStateOf("") }
    val apellido = remember { mutableStateOf("") }
    val numero = remember { mutableStateOf("") }
    val direccion = remember { mutableStateOf("") }

    Scaffold(
        topBar = { Toolbar(navController) },
        bottomBar = { Bottombar(navController, viewModel) }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .padding(top = 15.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "En esta pantalla de configuración vas a poder configurar los datos que queres que aparezcan en el QR de emergencia",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = nombre.value,
                    onValueChange = { nombre.value = it },
                    label = { Text("Nombre (familiar o conocido)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = apellido.value,
                    onValueChange = { apellido.value = it },
                    label = { Text("Apellido") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = numero.value,
                    onValueChange = { numero.value = it },
                    label = { Text("Número de emergencia") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = direccion.value,
                    onValueChange = { direccion.value = it },
                    label = { Text("Dirección de emergencia") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val infoQR = InfoQR(
                            nombre = nombre.value,
                            apellido = apellido.value,
                            numero = numero.value,
                            direccion = direccion.value
                        )
                         viewModel.infoQr = infoQR.toString()
                        navController.navigate(route = AppScreens.HomeScreen.route)
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}