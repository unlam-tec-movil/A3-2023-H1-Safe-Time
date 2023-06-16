package ar.edu.unlam.mobile2.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MarcadorDAO {

    @Query("SELECT * FROM marcadorentity")
    fun getAll(): List<MarcadorEntity>

    @Insert
    fun insertAll(vararg marcadores: MarcadorEntity)

    @Insert
    fun insert(marcadores: MarcadorEntity)

    @Update
    fun update(marcadorEntity: MarcadorEntity)

    @Delete
    fun delete(marcadorEntity: MarcadorEntity)
}