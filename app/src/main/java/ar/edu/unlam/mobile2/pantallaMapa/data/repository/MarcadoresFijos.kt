package ar.edu.unlam.mobile2.pantallaMapa.data.repository

import ar.edu.unlam.mobile2.data.room.model.MarcadorEntity

object MarcadoresFijos {

    val marcadoresFijos = listOf(

        MarcadorEntity(
            "UNLAM",
            -34.6690101,
            -58.5637967,
            "Florencio Varela 1903",
            true
        ),
        MarcadorEntity(
            "Hospital",
            -34.669191,
            -58.566337,
            "Gral. Juan Domingo Perón 4190",
            true
        ),
        MarcadorEntity(
            "Shopping",
            -34.684791,
            -58.557518,
            "Av. Brig. Gral. Juan Manuel de Rosas 3910"
        )
    )


    fun marcarFavorito(marcadorEntity: MarcadorEntity) {
        marcadorEntity.fav = true
    }

    fun desmarcarFavorito(marcadorEntity: MarcadorEntity) {
        marcadorEntity.fav = false
    }
}