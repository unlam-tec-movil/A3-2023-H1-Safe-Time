package ar.edu.unlam.mobile2.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marcadorentity")
data class MarcadorEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "nombre") val nombre: String?,
    @ColumnInfo(name = "latitud") val latitud: Double?,
    @ColumnInfo(name = "longitud") val longitud: Double?
)
