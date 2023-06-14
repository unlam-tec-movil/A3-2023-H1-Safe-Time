package ar.edu.unlam.mobile2.pantallaHome.data.model

import androidx.annotation.DrawableRes
import ar.edu.unlam.mobile2.R

data class Contact(var nombre: String,
                   val telefono: String,
                   @DrawableRes val imagen: Int)
