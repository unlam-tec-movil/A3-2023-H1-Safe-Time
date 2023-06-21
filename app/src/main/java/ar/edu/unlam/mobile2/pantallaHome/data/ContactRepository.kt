package ar.edu.unlam.mobile2.pantallaHome.data


import ar.edu.unlam.mobile2.R
import ar.edu.unlam.mobile2.pantallaHome.data.model.Contact
import ar.edu.unlam.mobile2.pantallaListaDeContactos.ContactsFromPhone
import javax.inject.Inject

class ContactRepository @Inject constructor() {

    companion object ContactDataProvider {

        var contacts = listOf(
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

        var contactosDeEmergencia = mutableListOf<ContactsFromPhone>(
        )
    }

    fun deleteContact(contact: ContactsFromPhone) {
        contactosDeEmergencia.remove(contact)
    }

    fun getContactos(): List<Contact> {
        return contacts
    }

    fun getContactosEmergenciaList(): MutableList<ContactsFromPhone> {
        return contactosDeEmergencia.toMutableList()
    }

    fun addContactEmergencia(contacts: List<ContactsFromPhone>) {
        contactosDeEmergencia.addAll(contacts)
    }
}



