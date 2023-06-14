package ar.edu.unlam.mobile2.pantallaMapa.data.repository

import ar.edu.unlam.mobile2.pantallaMapa.data.model.RouteModel
import com.google.android.gms.maps.model.PolylineOptions
import retrofit2.Response

interface RouteRepository {
        suspend fun getRoute(start:String,end:String):PolylineOptions

}