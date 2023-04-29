package ar.edu.unlam.mobile2.pantallaHome.domain.model

data class Adress(
    val nombre: String,
    val direccion: String,
    val latitud: Double,
    val altitud: Double
){
    companion object DirectionData {
        val adress = listOf(
            Adress("Jose", "Alsina 5800", 32.0, 43.0),
            Adress("Juan", "Rosales 200", 10.0,23.0),
            Adress("Roberto", "Espora 170", 54.0,21.0),
            Adress("Jose", "Alsina 5800", 32.0, 43.0),
            Adress("Juan", "Rosales 200", 10.0,23.0),
            Adress("Roberto", "Espora 170", 54.0,21.0),
            Adress("Jose", "Alsina 5800", 32.0, 43.0),
            Adress("Juan", "Rosales 200", 10.0,23.0),
            Adress("Roberto", "Espora 170", 54.0,21.0),
            Adress("Jose", "Alsina 5800", 32.0, 43.0),
            Adress("Juan", "Rosales 200", 10.0,23.0),
            Adress("Roberto", "Espora 170", 54.0,21.0)
        )
    }
}




