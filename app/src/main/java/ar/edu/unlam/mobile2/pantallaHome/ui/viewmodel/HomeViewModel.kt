package ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile2.pantallaHome.data.ContactRepository
import ar.edu.unlam.mobile2.pantallaHome.domain.model.Contact
import ar.edu.unlam.mobile2.pantallaMapa.data.repository.Marcador
import ar.edu.unlam.mobile2.pantallaMapa.data.repository.MarcadorRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel() {
    private val contactRepository = ContactRepository()

    private val _contactosEmergencia = MutableLiveData(emptyList<Contact>())
    val contactosEmergencia: LiveData<List<Contact>> = _contactosEmergencia

    private val _ubicacionesRapidas = MutableLiveData(emptyList<Marcador>())
    val ubicacionesRapidas: LiveData<List<Marcador>> = _ubicacionesRapidas

    private val _ubicacionMapa = MutableLiveData<Marcador>()
    val ubicacionMapa: LiveData<Marcador> = _ubicacionMapa

    val selectedContacts = MutableStateFlow<List<Contact>>(emptyList())
    val selectedAddresses = MutableStateFlow<List<Marcador>>(emptyList())


    var infoQr by mutableStateOf("DEBE LLENAR EL FORMULARIO")
    var screenUbication by mutableStateOf("home_screenn")
    var tabPestañas by mutableStateOf(0)
    var isDialogShown by mutableStateOf(false)
        private set
    init {
        viewModelScope.launch {
            _contactosEmergencia.value = contactRepository.getContactosEmergenciaList()
            _ubicacionesRapidas.value = MarcadorRepo.ubicaciones
        }
    }


    fun onEmergencyClick() {
        isDialogShown = true
    }

    fun onDismissDialog() {
        isDialogShown = false
    }

    fun contactoSeleccionado(contacto: Contact) {
        selectedContacts.value = selectedContacts.value + contacto
    }
    fun ubicacionSeleccionada(ubicacion: Marcador) {
        selectedAddresses.value = selectedAddresses.value + ubicacion
    }

    fun contactoDesSeleccionado(contacto: Contact) {
        selectedContacts.value = selectedContacts.value - contacto
    }




    fun eliminarContacto(contact: Contact) {
        viewModelScope.launch {
            _contactosEmergencia.value = _contactosEmergencia.value?.minus(contact)
        }
    }
    fun eliminarUbicacion(ubicacion: Marcador) {
        viewModelScope.launch {
            _ubicacionesRapidas.value = _ubicacionesRapidas.value?.minus(ubicacion)
        }
    }

    fun agregarContactosSeleccionados() {
        _contactosEmergencia.value = contactosEmergencia.value?.plus(selectedContacts.value)
        selectedContacts.value = emptyList()
    }
    fun agregarUbicacionesSeleccionadas() {
       _ubicacionesRapidas.value = ubicacionesRapidas.value?.plus(selectedAddresses.value)
        selectedAddresses.value = emptyList()
    }

    fun ubicacionDesSeleccionada(ubicacion: Marcador) {
        selectedAddresses.value = selectedAddresses.value - ubicacion

    }

    fun limpiarSeleccionados() {
        selectedAddresses.value = emptyList()
        selectedContacts.value = emptyList()
    }

    fun nuevaUbicacionSeleccionadaEnMapa(ubicacion: Marcador) {
        _ubicacionMapa.value = ubicacion
    }

    fun definirPestaña(tab: Int) {
        tabPestañas=tab
    }

}