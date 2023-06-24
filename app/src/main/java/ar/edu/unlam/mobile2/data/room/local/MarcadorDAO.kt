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

    @Query("UPDATE marcadorentity SET favorito = 0 WHERE nombre LIKE :name")
    fun noEsFavorito(name: String)

    @Query("UPDATE marcadorentity SET favorito = 1 WHERE nombre LIKE :name")
    fun esFavorito(name: String)

    @Insert
    fun insertAll(marcadores: List<MarcadorEntity>)

    @Insert
    fun insert(marcadores: MarcadorEntity)

    @Update
    fun update(marcadorEntity: MarcadorEntity)

    @Delete
    fun delete(marcadorEntity: MarcadorEntity)
}