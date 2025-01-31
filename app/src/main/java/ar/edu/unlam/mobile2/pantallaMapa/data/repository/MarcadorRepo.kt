package ar.edu.unlam.mobile2.pantallaMapa.data.repository

import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

data class Marcador(val nombre: String, val latLng: LatLng) {
}

class MarcadorRepository @Inject constructor() {

companion object UbicacionesDataProvider {


    var ubicaciones = mutableListOf(
        Marcador("UNLAM", LatLng(-34.6690101, -58.5637967)),
        Marcador("HOSPITAL", LatLng(-34.669191, -58.566337)),
        Marcador("SHOPPING", LatLng(-34.684791, -58.557518)),
        Marcador("ROMA", LatLng(41.890326, 12.491753)),
        Marcador("MONTE FUJI", LatLng(35.361919, 138.729209)),
        Marcador("OBELISCO", LatLng(-34.603823, -58.381576))
    )

    var ubicacionesRapidas = mutableListOf(
        Marcador("UNLAM", LatLng(-34.6690101, -58.5637967)),
        Marcador("HOSPITAL", LatLng(-34.669191, -58.566337)),
        Marcador("SHOPPING", LatLng(-34.684791, -58.557518)),
        Marcador("ROMA", LatLng(41.890326, 12.491753)),
        Marcador("MONTE FUJI", LatLng(35.361919, 138.729209)),
        Marcador("OBELISCO", LatLng(-34.603823, -58.381576))
    )
}
    fun getUbicacionesRapidas(): MutableList<Marcador> {
        return ubicacionesRapidas.toMutableList()
    }

    fun borrarUbicacion(ubicacion:Marcador){
        ubicacionesRapidas.remove(ubicacion)
    }

    fun agregarUbicacion(ubicacion:List<Marcador>){
        ubicacionesRapidas.addAll(ubicacion)
    }
}