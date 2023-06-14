package ar.edu.unlam.mobile2.pantallaMapa.data.model

import androidx.compose.ui.text.android.style.FontFeatureSpan

import com.google.gson.annotations.SerializedName

data class RouteModel(
    @SerializedName ("features")val features: List<Feature>
)

data class Feature(
    @SerializedName ("geometry")val geometry: Geometry
)
data class Geometry(
    @SerializedName ("coordinates")val coordinates: List<List<Double>>
)