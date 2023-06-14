package ar.edu.unlam.mobile2.pantallaMapa.data.repository

import ar.edu.unlam.mobile2.pantallaMapa.data.model.RouteModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RouteApi {
    @GET("/v2/directions/driving-car")
    suspend fun getRoute(
        @Query("api_key") key: String,
        @Query("start") start: String,
        @Query("end") end: String
    ): Response<RouteModel>

}