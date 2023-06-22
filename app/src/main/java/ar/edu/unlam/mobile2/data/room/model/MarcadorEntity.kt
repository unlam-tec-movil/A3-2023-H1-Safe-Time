package ar.edu.unlam.mobile2.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marcadorentity")
data class MarcadorEntity(
    @PrimaryKey
    @ColumnInfo(name = "nombre") val nombre: String = "",
    @ColumnInfo(name = "latitud") val latitud: Double = 0.0,
    @ColumnInfo(name = "longitud") val longitud: Double = 0.0,
    @ColumnInfo(name = "dirección") val direccion: String = "",
    @ColumnInfo(name = "favorito") val fav: Boolean = false
)
