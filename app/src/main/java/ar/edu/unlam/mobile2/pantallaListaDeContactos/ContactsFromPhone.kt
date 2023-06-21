package ar.edu.unlam.mobile2.pantallaListaDeContactos

import androidx.annotation.DrawableRes
import ar.edu.unlam.mobile2.R

data class ContactsFromPhone(
    val name: String,
    val number: String,
    @DrawableRes val imagen: Int = R.drawable.ic_launcher_foreground
)