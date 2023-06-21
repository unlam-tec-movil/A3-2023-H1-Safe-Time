package ar.edu.unlam.mobile2.pantallaMapa.data.repository

import android.util.Log
import ar.edu.unlam.mobile2.pantallaMapa.data.model.RouteModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import javax.inject.Inject

class RoutesRestRepository @Inject constructor(builder: Builder) :RouteRepository {

    val apiKey = "5b3ce3597851110001cf6248573e19e0c2ce4c8aa3277a353b2f8f92"

    var retrofit: Retrofit = builder
        .baseUrl("https://api.openrouteservice.org/")
        .build()

    override suspend fun getRoute(start:String,end:String): PolylineOptions {
        val kittyApi = retrofit.create(RouteApi::class.java)
        val call = kittyApi.getRoute(apiKey,start,end)
        Log.i("bruno", "getRoute: entre a la funcion getroute ")


            if (call.isSuccessful) {
                val polyLineOptions = PolylineOptions()

                call.body()?.features?.first()?.geometry?.coordinates?.forEach {
                    Log.i("bruno", "getRoute: entre al foreach ")
                    polyLineOptions.add(LatLng(it[1],it[0]))
                }

                Log.i("bruno", "getRoute:retorne poly ")
                return polyLineOptions
            }

        Log.i("bruno", "getRoute: fallo la llamada ")


        return error("error en la obtencion de rutas")
        }
    }