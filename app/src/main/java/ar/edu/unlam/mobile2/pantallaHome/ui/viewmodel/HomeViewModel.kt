package ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile2.pantallaHome.data.ContactRepository
import ar.edu.unlam.mobile2.pantallaHome.data.model.Contact
import ar.edu.unlam.mobile2.pantallaMapa.data.repository.Marcador
import ar.edu.unlam.mobile2.pantallaMapa.data.repository.MarcadorRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel() {

    private val contactRepository = ContactRepository()
    private val ubicacionRepository = MarcadorRepository()

    private val _contactos = MutableLiveData(emptyList<Contact>())
    val contactos: LiveData<List<Contact>> = _contactos

    private val _contactosEmergencia = MutableLiveData(emptyList<Contact>())
    val contactosEmergencia: LiveData<List<Contact>> = _contactosEmergencia

    private val _ubicacionesRapidas = MutableLiveData(emptyList<Marcador>())
    val ubicacionesRapidas: LiveData<List<Marcador>> = _ubicacionesRapidas

    private val _ubicacionMapa = MutableLiveData<Marcador>()
    val ubicacionMapa: LiveData<Marcador> = _ubicacionMapa

    val selectedContacts = MutableStateFlow<List<Contact>>(emptyList())
    val selectedAddresses = MutableStateFlow<List<Marcador>>(emptyList())

    private val _locationState = MutableStateFlow<LatLng?>(null)
    val locationState: StateFlow<LatLng?> = _locationState


    var infoQr by mutableStateOf("DEBE LLENAR EL FORMULARIO")

    var screenUbication by mutableStateOf("home_screenn")

    var tabPestañas = MutableLiveData(0)

    var isButtomShow = MutableLiveData(false)

    var textButtomAgregarSeleccionados = MutableLiveData("")
    var isDialogShown = MutableLiveData(false)
        private set
    init {
        viewModelScope.launch {
            _contactosEmergencia.value = contactRepository.getContactosEmergenciaList()
            _ubicacionesRapidas.value = ubicacionRepository.getUbicacionesRapidas()
            _contactos.value = contactRepository.getContactos()
        }
    }


    fun onEmergencyClick() {
        isDialogShown.value=true
    }

    fun onDismissDialog() {
        isDialogShown.value = false
    }

    fun contactoSeleccionado(contacto: Contact) {
        selectedContacts.value = selectedContacts.value + contacto
        textButtomAgregarSeleccionados.value="Agregar a contactos de emergencia"
            isButtomShow.value=true

    }

    fun contactoDesSeleccionado(contacto: Contact) {
        selectedContacts.value = selectedContacts.value - contacto
        if (selectedContacts.value.isEmpty()){
            isButtomShow.value=false
        }

    }

    fun ubicacionSeleccionada(ubicacion: Marcador) {
        selectedAddresses.value = selectedAddresses.value + ubicacion
        textButtomAgregarSeleccionados.value="Agregar a ubicaciones rapidas"
        isButtomShow.value=true
    }

    fun ubicacionDesSeleccionada(ubicacion: Marcador) {
        selectedAddresses.value = selectedAddresses.value - ubicacion
        if (selectedAddresses.value.isEmpty()){
            isButtomShow.value=false
        }

    }


    fun eliminarContactoEmergencia(contact: Contact) {
        viewModelScope.launch {
            contactRepository.deleteContact(contact)
            _contactosEmergencia.value = contactRepository.getContactosEmergenciaList()
        }
    }
    fun eliminarUbicacionRapida(ubicacion: Marcador) {
        viewModelScope.launch {
            ubicacionRepository.borrarUbicacion(ubicacion)
            _ubicacionesRapidas.value = ubicacionRepository.getUbicacionesRapidas()
        }
    }

    fun agregarSeleccionados(valor: Int) {
        if (valor==0){
            contactRepository.agregarContactoEmergencia(selectedContacts.value)
            _contactosEmergencia.value=contactRepository.getContactosEmergenciaList()
            selectedContacts.value = emptyList()
        }else{
            ubicacionRepository.agregarUbicacion(selectedAddresses.value)
            _ubicacionesRapidas.value= ubicacionRepository.getUbicacionesRapidas()
            selectedAddresses.value = emptyList()
        }
    }

    fun limpiarSeleccionados() {
        selectedAddresses.value -= selectedAddresses.value
        selectedContacts.value -= selectedContacts.value
        isButtomShow.value=false
    }

    fun nuevaUbicacionSeleccionadaEnMapa(ubicacion: Marcador) {
        _ubicacionMapa.value = ubicacion
    }

    fun definirPestaña(tab: Int) {
        tabPestañas.value=tab
    }

}