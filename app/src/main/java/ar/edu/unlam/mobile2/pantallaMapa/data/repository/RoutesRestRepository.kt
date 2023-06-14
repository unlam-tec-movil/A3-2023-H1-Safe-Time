package ar.edu.unlam.mobile2.pantallaMapa.data.repository

import ar.edu.unlam.mobile2.pantallaMapa.data.model.RouteModel
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import javax.inject.Inject

class RoutesRestRepository @Inject constructor(builder: Builder) :RouteRepository {

    val apiKey = "5b3ce3597851110001cf6248f935b6973e1142b09c905eb3ceea8fda"

            var retrofit: Retrofit = builder
            .baseUrl("https://api.openrouteservice.org/")
            .build()

    override suspend fun getRoute(start:String,end:String): RouteModel {
            val kittyApi = retrofit.create(RouteApi::class.java)

            val call = kittyApi.getRoute(apiKey,start,end)
            val route = call.body()

            if (call.isSuccessful) {
                return call.body()!!
            }

            return error("error en la obtencion de rutas")
        }
    }