package ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val contactRepository: ContactRepository,
    private val marcadorRepository: ar.edu.unlam.mobile2.data.room.MarcadorRepository

) : ViewModel() {

    private val _openScan = MutableLiveData<Boolean>()
    val openScan: LiveData<Boolean> = _openScan

    private val _contactosEmergencia = MutableLiveData(emptyList<ContactsFromPhone>())
    val contactosEmergencia: LiveData<List<ContactsFromPhone>> = _contactosEmergencia

    private val _marcadoresFav = MutableLiveData(emptyList<MarcadorEntity>())
    val marcadoresFav: LiveData<List<MarcadorEntity>> = _marcadoresFav


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
            _marcadoresFav.value = marcadorRepository.getAllFavMarcador()

        }
    }


    fun onCompartirClick() {
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


    fun definirPestaña(tab: Int) {
        tabPestañas.value = tab
    }


    fun setContactsFromPhone(name: String, number: String) {
        _contactosFromPhone.value =
            contactosFromPhone.value?.plus(ContactsFromPhone(name, number))
    }

    fun informacionACompartir(info: Any) {
        val gson = Gson()
        infoQr = gson.toJson(info)
    }

    fun openScanClick() {
        _openScan.value = true
    }

    fun recargarMarcadoresFavoritos() {
        _marcadoresFav.value = marcadorRepository.getAllFavMarcador()
    }


}