package ar.edu.unlam.mobile2.pantallaHome.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile2.pantallaHome.data.ContactRepository
import ar.edu.unlam.mobile2.pantallaHome.domain.model.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel() {
    private val contactRepository = ContactRepository()
    private val _contactos = MutableLiveData(emptyList<Contact>())
    val selectedContacts = MutableStateFlow<List<Contact>>(emptyList())
    val selectedAddresses = mutableStateListOf<Contact>()

    val contactos: LiveData<List<Contact>> = _contactos
    var infoQr by mutableStateOf("DEBE LLENAR EL FORMULARIO")
    var screenUbication by mutableStateOf("home_screenn")
    var isDialogShown by mutableStateOf(false)
        private set
    init {
        viewModelScope.launch {
            _contactos.value = contactRepository.getContactosEmergenciaList()
        }
    }

    fun eliminarContacto(contact: Contact) {
        viewModelScope.launch {
            _contactos.value = _contactos.value?.minus(contact)
        }
    }

    fun agregarContacto(contact: Contact) {
        viewModelScope.launch {
            _contactos.value = _contactos.value?.plus(contact)
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

    fun contactoDesSeleccionado(contacto: Contact) {
        selectedContacts.value = selectedContacts.value - contacto
    }

    fun borrarSeleccionados() {
        selectedContacts.value = selectedContacts.value - selectedContacts.value.toSet()
    }

    fun agregarContactosSeleccionados() {
        _contactos.value = contactos.value?.plus(selectedContacts.value)
        selectedContacts.value = emptyList()
    }
}