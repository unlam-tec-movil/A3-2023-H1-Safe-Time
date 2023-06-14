package ar.edu.unlam.mobile2.pantallaMapa.domain

import ar.edu.unlam.mobile2.pantallaMapa.data.model.RouteModel
import ar.edu.unlam.mobile2.pantallaMapa.data.repository.RoutesRestRepository
import com.google.android.gms.maps.model.PolylineOptions
import javax.inject.Inject

class RouteServices @Inject constructor(var repo: RoutesRestRepository) {

    suspend fun getRoutes(start:String,end:String): PolylineOptions {
       return repo.getRoute(start,end)
    }
}