package ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeViewModel:ViewModel() {
    var infoQr by mutableStateOf("DEBE LLENAR EL FORMULARIO")

    var screenUbication by mutableStateOf("home_screenn")

    var isDialogShown by mutableStateOf(false)
        private set

    fun onEmergencyClick(){
        isDialogShown=true
    }

    fun onDismissDialog(){
        isDialogShown=false
    }
}