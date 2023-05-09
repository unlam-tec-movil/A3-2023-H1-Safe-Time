package ar.edu.unlam.mobile2.pantallaMapa.data.repository

import com.google.android.gms.maps.model.LatLng
import kotlin.reflect.KProperty

data class Marcador(val nombre: String, val latLng: LatLng) {
}

object MarcadorRepo {

    val ubicaciones = listOf(
        Marcador("UNLAM", LatLng(-34.6690101, -58.5637967)),
        Marcador("HOSPITAL", LatLng(-34.669191, -58.566337)),
        Marcador("SHOPPING", LatLng(-34.684791, -58.557518))
    )
}