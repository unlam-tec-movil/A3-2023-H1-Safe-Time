package ar.edu.unlam.mobile2.data.room

import ar.edu.unlam.mobile2.data.room.local.MarcadorDAO
import ar.edu.unlam.mobile2.data.room.model.MarcadorEntity
import javax.inject.Inject

class MarcadorRepository @Inject constructor(
    private val marcadorDao: MarcadorDAO
) {


    fun getAllMarcador(): List<MarcadorEntity> = marcadorDao.getAll()

    fun getAllFavMarcador(): List<MarcadorEntity> = marcadorDao.getAllFav()

    fun getByName(buscado: String): MarcadorEntity = marcadorDao.getByName(buscado)

    fun insertAll(vararg marcadores: MarcadorEntity) = marcadorDao.insertAll(*marcadores)

    fun insert(marcadores: MarcadorEntity) = marcadorDao.insert(marcadores)

    fun update(marcadorEntity: MarcadorEntity) = marcadorDao.update(marcadorEntity)

    fun delete(marcadorEntity: MarcadorEntity) = marcadorDao.delete(marcadorEntity)

}