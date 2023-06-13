package ar.edu.unlam.mobile2.pantallaMapa.domain

import ar.edu.unlam.mobile2.pantallaMapa.data.model.RouteModel
import ar.edu.unlam.mobile2.pantallaMapa.data.repository.RoutesRestRepository
import javax.inject.Inject

class RouteServices @Inject constructor(var repo: RoutesRestRepository) {

    suspend fun getRoutes(start:String,end:String): RouteModel {
       return repo.getRoute(start,end)
    }
}