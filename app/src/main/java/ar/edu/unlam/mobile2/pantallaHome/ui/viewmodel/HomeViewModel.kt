package ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile2.data.room.ContactRepository
import ar.edu.unlam.mobile2.data.room.model.ContactsFromPhone
import ar.edu.unlam.mobile2.data.room.model.MarcadorEntity
import ar.edu.unlam.mobile2.pantallaMapa.data.repository.MarcadoresFijos
import ar.edu.unlam.mobile2.pantallaMapa.domain.RouteServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val contactRepository: ContactRepository,
    private val routeServices: RouteServices,
    private val marcadorRepository: ar.edu.unlam.mobile2.data.room.MarcadorRepository

) : ViewModel() {

    private val _polylineOptions = MutableLiveData<PolylineOptions?>()
    val polylineOptions: LiveData<PolylineOptions?> = _polylineOptions

    private val _contactosEmergencia = MutableLiveData(emptyList<ContactsFromPhone>())
    val contactosEmergencia: LiveData<List<ContactsFromPhone>> = _contactosEmergencia

    private val _marcadores = MutableLiveData(emptyList<MarcadorEntity>())
    val marcadores: LiveData<List<MarcadorEntity>> = _marcadores

    private val _marcadoresFav = MutableLiveData(emptyList<MarcadorEntity>())
    val marcadoresFav: LiveData<List<MarcadorEntity>> = _marcadoresFav

    private val _ubicacionMapa = MutableLiveData<MarcadorEntity>()
    val ubicacionMapa: LiveData<MarcadorEntity> = _ubicacionMapa

    private var _currentLocation = MutableLiveData<LatLng?>()
    val currentLocation: LiveData<LatLng?> = _currentLocation

    val selectedContacts = MutableStateFlow<List<ContactsFromPhone>>(emptyList())
    val marcadorSeleccionado = MutableStateFlow<List<MarcadorEntity>>(emptyList())

    private val _contactosFromPhone = MutableLiveData(emptyList<ContactsFromPhone>())
    val contactosFromPhone: LiveData<List<ContactsFromPhone>> = _contactosFromPhone


    var infoQr by mutableStateOf("DEBE LLENAR EL FORMULARIO")

    var screenUbication by mutableStateOf("home_screenn")

    var tabPestañas = MutableLiveData(0)

    var isButtomShow = MutableLiveData(false)

    var textButtomAgregarSeleccionados = MutableLiveData("")
    var isDialogShown = MutableLiveData(false)
        private set

    init {
        viewModelScope.launch {

            _contactosEmergencia.value = contactRepository.getAll()
            _marcadores.value = marcadorRepository.getAllMarcador()
            _marcadoresFav.value = marcadorRepository.getAllFavMarcador()

        }
    }


    fun onEmergencyClick() {
        isDialogShown.value = true
    }

    fun onDismissDialog() {
        isDialogShown.value = false
    }

    fun contactoSeleccionado(contacto: ContactsFromPhone) {
        selectedContacts.value = selectedContacts.value + contacto
        textButtomAgregarSeleccionados.value = "Agregar a contactos de emergencia"
        isButtomShow.value = true
    }

    fun contactoDesSeleccionado(contacto: ContactsFromPhone) {
        selectedContacts.value = selectedContacts.value - contacto
        if (selectedContacts.value.isEmpty()) {
            isButtomShow.value = false
        }

    }

    fun marcadorSeleccionado(marcador: MarcadorEntity) {
        marcadorSeleccionado.value = marcadorSeleccionado.value + marcador
        textButtomAgregarSeleccionados.value = "Agregar a ubicaciones rapidas"
        isButtomShow.value = true

        Log.i("MARCADOR_DB", "Seleccionados: ${marcadorSeleccionado.value}")
    }


    fun marcadorDeseleccionado(marcador: MarcadorEntity) {
        marcadorSeleccionado.value = marcadorSeleccionado.value - marcador
        if (marcadorSeleccionado.value.isEmpty()) {
            isButtomShow.value = false
        }

    }

    fun eliminarContactoEmergencia(contact: ContactsFromPhone) {
        viewModelScope.launch {
            contactRepository.trueDelete(contact.number)
            _contactosEmergencia.value = contactRepository.getAll()

        }
    }

    fun agregarContactoEmergencia(contact: ContactsFromPhone) {
        viewModelScope.launch {
            contactRepository.insert(contact)
            _contactosEmergencia.value = contactRepository.getAll()
        }
    }

    fun marcarFavorito(nuevo: MarcadorEntity) {
        viewModelScope.launch {
            marcadorRepository.esFavorito(nuevo.nombre)
            _marcadoresFav.value = marcadorRepository.getAllFavMarcador()
        }
    }

    fun desmarcarFavorito(nuevo: MarcadorEntity) {
        viewModelScope.launch {
            marcadorRepository.noEsFavorito(nuevo.nombre)
            _marcadoresFav.value = marcadorRepository.getAllFavMarcador()
        }
    }


    fun agregarSeleccionados(valor: Int) {
        if (valor == 0) {
            contactRepository.insertAll(selectedContacts.value)
            _contactosEmergencia.value = contactRepository.getAll()
            selectedContacts.value = emptyList()
        } else {
            marcadorSeleccionado.value.forEach {

                marcarFavorito(it)
                MarcadoresFijos.marcarFavorito(it)
            }
            marcadorSeleccionado.value = emptyList()
        }
    }

    fun limpiarSeleccionados() {
        selectedContacts.value -= selectedContacts.value
        marcadorSeleccionado.value -= marcadorSeleccionado.value
        isButtomShow.value = false
    }

    fun nuevaUbicacionSeleccionadaEnMapa(ubicacion: MarcadorEntity) {
        _ubicacionMapa.value = ubicacion
        createRoute()
    }

    fun definirPestaña(tab: Int) {
        tabPestañas.value = tab
    }


    fun createRoute() {


        val stringCurrentLocation =
            "${_currentLocation.value?.longitude},${currentLocation.value?.latitude}"

        val stringDestino =
            "${_ubicacionMapa.value?.longitud},${_ubicacionMapa.value?.latitud}"

        _polylineOptions.value?.points?.clear()

        viewModelScope.launch {
            val response = routeServices.getRoutes(stringCurrentLocation, stringDestino)
            _polylineOptions.value = response
        }

    }

    fun setCurrentLocation(result: LatLng) {
        _currentLocation.value = result
    }

    fun setContactsFromPhone(name: String, number: String) {

        _contactosFromPhone.value =
            contactosFromPhone.value?.plus(ContactsFromPhone(name, number))
    }


    private val _isLocationPermissionGranted = MutableLiveData(false)
    val isLocationPermissionGranted: LiveData<Boolean> = _isLocationPermissionGranted

}