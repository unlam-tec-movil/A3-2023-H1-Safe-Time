package ar.edu.unlam.mobile2.pantallaHome.domain.model

import androidx.annotation.DrawableRes
import ar.edu.unlam.mobile2.R

data class Contact(val nombre: String,
                   val telefono: String,
                   @DrawableRes val imagen: Int){


    companion object ContactDataProvider {
        val contacts = listOf(
            Contact(
                "Juan Perez",
                "555-555-1111",
                R.drawable.safetimelogo
            ),
            Contact(
                "Maria Garcia",
                "555-555-2222",
                R.drawable.ic_launcher_foreground
            ),
            Contact(
                "Pedro Rodriguez",
                "555-555-3333",
                R.drawable.ic_launcher_foreground
            ),
            Contact(
                "Lucia Hernandez",
                "555-555-4444",
                R.drawable.ic_launcher_foreground
            ),
            Contact(
                "Carlos Torres",
                "555-555-5555",
                R.drawable.ic_launcher_foreground
            ),
            Contact(
                "Maria Garcia",
                "555-555-2222",
                R.drawable.ic_launcher_foreground
            ),
            Contact(
                "Pedro Rodriguez",
                "555-555-3333",
                R.drawable.ic_launcher_foreground
            ),
            Contact(
                "Lucia Hernandez",
                "555-555-4444",
                R.drawable.ic_launcher_foreground
            ),
            Contact(
                "Carlos Torres",
                "555-555-5555",
                R.drawable.ic_launcher_foreground
            ),
            Contact(
                "Maria Garcia",
                "555-555-2222",
                R.drawable.ic_launcher_foreground
            )
        )
    }
}
