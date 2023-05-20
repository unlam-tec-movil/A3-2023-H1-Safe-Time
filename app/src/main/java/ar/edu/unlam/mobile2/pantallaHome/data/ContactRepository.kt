package ar.edu.unlam.mobile2.pantallaHome.data

import ar.edu.unlam.mobile2.R
import ar.edu.unlam.mobile2.pantallaHome.domain.model.Contact

class ContactRepository {
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

    fun getContactList(): List<Contact> {
        return contactosDeEmergencia
    }

    // Elimina un contacto de la lista
    fun deleteContact(contact: Contact) {
            contactosDeEmergencia.remove(contact)
    }
}



