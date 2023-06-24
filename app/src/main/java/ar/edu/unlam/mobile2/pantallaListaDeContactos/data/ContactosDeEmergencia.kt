package ar.edu.unlam.mobile2.pantallaListaDeContactos.data

import ar.edu.unlam.mobile2.data.room.model.ContactsFromPhone

object ContactosDeEmergencia {
    val contactosDeEmergencia = listOf(
        ContactsFromPhone("Policia", "911", esDefault = true),
        ContactsFromPhone("Bomberos", "100", esDefault = true),
        ContactsFromPhone("Emergencias", "107", esDefault = true),
        ContactsFromPhone("Lionel Messi", "333")
    )
}