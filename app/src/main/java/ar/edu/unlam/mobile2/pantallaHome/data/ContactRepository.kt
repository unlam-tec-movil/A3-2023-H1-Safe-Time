package ar.edu.unlam.mobile2.pantallaHome.data

import ar.edu.unlam.mobile2.R
import ar.edu.unlam.mobile2.pantallaHome.data.model.Contact
import javax.inject.Inject

class ContactRepository @Inject constructor() {
    companion object ContactDataProvider {

        var ubicaciones = listOf(
            Contact(
                "Bruno Mendez",
                "+549113017809",
                R.drawable.safetimelogo
            ),
            Contact(
                "Maria Garcia",
                "555-555-2222",
                R.drawable.ic_launcher_foreground
            ),
            Contact(
                "Pedro Rodriguez",
                "555-555-3333",
                R.drawable.ic_launcher_foreground
            ),
            Contact(
                "Lucia Hernandez",
                "555-555-4444",
                R.drawable.ic_launcher_foreground
            ),
            Contact(
                "Carlos Torres",
                "555-555-5555",
                R.drawable.ic_launcher_foreground
            ),
            Contact(
                "Maria Garcia",
                "555-555-2222",
                R.drawable.ic_launcher_foreground
            ),
            Contact(
                "Pedro Rodriguez",
                "555-555-3333",
                R.drawable.ic_launcher_foreground
            ),
            Contact(
                "Lucia Hernandez",
                "555-555-4444",
                R.drawable.ic_launcher_foreground
            ),
            Contact(
                "Carlos Torres",
                "555-555-5555",
                R.drawable.ic_launcher_foreground
            ),
            Contact(
                "Maria Garcia",
                "555-555-2222",
                R.drawable.ic_launcher_foreground
            )
        )

        var contactosDeEmergencia = mutableListOf(
            Contact(
                "PEPO Mendez",
                "+549113017809",
                R.drawable.safetimelogo
            ),
            Contact(
                "Maria Garcia",
                "555-555-2222",
                R.drawable.ic_launcher_foreground
            ),
            Contact(
                "Pedro Rodriguez",
                "555-555-3333",
                R.drawable.ic_launcher_foreground
            ),
            Contact(
                "Lucia Hernandez",
                "555-555-4444",
                R.drawable.ic_launcher_foreground
            )
        )
    }

    fun deleteContact(contact: Contact) {
        contactosDeEmergencia.remove(contact)
    }

    fun getContactos(): List<Contact> {
        return ubicaciones
    }

    fun getContactosEmergenciaList(): MutableList<Contact> {
        return contactosDeEmergencia.toMutableList()
    }

    fun addContactEmergencia(contacts: List<Contact>) {
        contactosDeEmergencia.addAll(contacts)
    }
}



