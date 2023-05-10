package ar.edu.unlam.mobile2.dialogQR

data class InfoQR(
    val nombre: String,
    val apellido: String,
    val numero: String,
    val direccion: String
) {
    override fun toString(): String {
        return "LA SIGUIENTE INFORMACION DEBE USARSE CON TOTAL RESPOSABILIDAD YA QUE ES INFROMACION PERSONAL, SI TIENE ACCESO A ELLA ES PORQUE EL USUARIO DEBE NECESITAR AYUDA POR FAVOR COMUNIQUESE AL NUMERO QUE SE LE INDIQUE O ACOMPAÑE A LA PERSONA HASTA LA DIRECCION INDICADA \nNombre: $nombre\nApellido: $apellido\nNúmero: $numero\nDirección: $direccion"
    }
}