package ar.edu.unlam.mobile2.pantallaListaDeContactos.data

import ar.edu.unlam.mobile2.data.room.model.ContactsFromPhone

object ContactosDeEmergencia {
    val contactosDeEmergencia = listOf(
        ContactsFromPhone("Policia", "911", inborrable = 1),
        ContactsFromPhone("Bomberos", "100", inborrable = 1),
        ContactsFromPhone("Emergencias", "107", inborrable = 1),
    )
}