package ar.edu.unlam.mobile2.pantallaMapa.ui.viewmodel

import android.location.Geocoder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile2.pantallaMapa.data.model.AutocompleteResult
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {

    lateinit var placesClient: PlacesClient
    lateinit var geocoder: Geocoder

    var locationAutofill = mutableStateListOf<AutocompleteResult>()
    var currentLocation by mutableStateOf(LatLng(0.0, 0.0))
    var text by mutableStateOf("")
    private var job: Job? = null

    fun buscarLugares(direccion: String) {
        job?.cancel()
        locationAutofill.clear()
        job = viewModelScope.launch {
            val request = FindAutocompletePredictionsRequest.builder().setQuery(direccion).build()
            placesClient.findAutocompletePredictions(request).addOnSuccessListener { respuesta ->
                locationAutofill += respuesta.autocompletePredictions.map {
                    AutocompleteResult(it.getFullText(null).toString(), it.placeId)
                }
            }.addOnFailureListener {
                it.printStackTrace()
                println(it.cause)
                println(it.message)
            }
        }
    }

    fun getCoordenadas(resultado: AutocompleteResult) {

        val placeFields = listOf(Place.Field.LAT_LNG)
        val request = FetchPlaceRequest.newInstance(resultado.placeId, placeFields)

        placesClient.fetchPlace(request).addOnSuccessListener {
            if (it != null) {
                currentLocation = it.place.latLng!!
            }
        }.addOnFailureListener {
            it.printStackTrace()
        }
    }


    fun getDireccion(latLng: LatLng) {
        viewModelScope.launch {
            val direccion = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            text =
                "${direccion?.get(0)?.thoroughfare.toString()} ${direccion?.get(0)?.featureName.toString()}, ${
                    direccion?.get(
                        0
                    )?.locality.toString()
                }"
        }
    }
}