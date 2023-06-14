package ar.edu.unlam.mobile2.pantallaMapa.data.repository

import ar.edu.unlam.mobile2.pantallaMapa.data.model.RouteModel

interface RouteRepository {
        suspend fun getRoute(start:String,end:String): RouteModel

}