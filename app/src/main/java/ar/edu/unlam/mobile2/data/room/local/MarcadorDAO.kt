package ar.edu.unlam.mobile2.data.room.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ar.edu.unlam.mobile2.data.room.model.MarcadorEntity

@Dao
interface MarcadorDAO {

    @Query("SELECT * FROM marcadorentity")
    fun getAll(): List<MarcadorEntity>

    @Query("SELECT * FROM marcadorentity WHERE favorito LIKE :fav")
    fun getAllFav(fav: Boolean = true): List<MarcadorEntity>

    @Query("SELECT * FROM marcadorentity WHERE nombre LIKE :buscado")
    fun getByName(buscado: String): MarcadorEntity

    @Query("UPDATE marcadorentity SET favorito = NOT favorito WHERE nombre = :nombre")
    fun updateFavorito(nombre: String)

    @Insert
    fun insertAll(vararg marcadores: MarcadorEntity)

    @Insert
    fun insert(marcadores: MarcadorEntity)

    @Update
    fun update(marcadorEntity: MarcadorEntity)

    @Delete
    fun delete(marcadorEntity: MarcadorEntity)
}