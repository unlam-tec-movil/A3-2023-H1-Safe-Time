package ar.edu.unlam.mobile2.pantallaListaDeContactos.data

import ar.edu.unlam.mobile2.R
import ar.edu.unlam.mobile2.data.room.model.ContactsFromPhone

object ContactosDeEmergencia {
    val contactosDeEmergencia = listOf(
        ContactsFromPhone("Policia", "911", esDefault = true, imagen = R.drawable.policia),
        ContactsFromPhone("Bomberos", "100", esDefault = true, imagen = R.drawable.bomberos),
        ContactsFromPhone("Emergencias", "107", esDefault = true, imagen = R.drawable.ambulancias),
        ContactsFromPhone("Lionel Messi", "333", imagen = R.drawable.messi)
    )
}