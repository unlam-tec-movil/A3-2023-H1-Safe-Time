package ar.edu.unlam.mobile2.data.room

import androidx.room.Query
import ar.edu.unlam.mobile2.data.room.local.MarcadorDAO
import ar.edu.unlam.mobile2.data.room.model.MarcadorEntity
import ar.edu.unlam.mobile2.pantallaMapa.data.repository.MarcadoresFijos
import javax.inject.Inject

class MarcadorRepository @Inject constructor(
    private val marcadorDao: MarcadorDAO
) {
    init {

        if (getAllMarcador().isEmpty()) {
            insertAll(MarcadoresFijos.marcadoresFijos)
        }
    }

    fun getAllMarcador(): List<MarcadorEntity> = marcadorDao.getAll()

    fun getAllFavMarcador(): List<MarcadorEntity> = marcadorDao.getAllFav()

    fun getByName(buscado: String): MarcadorEntity = marcadorDao.getByName(buscado)

    fun insertAll(marcadores: List<MarcadorEntity>) = marcadorDao.insertAll(marcadores)

    fun insert(marcadores: MarcadorEntity) = marcadorDao.insert(marcadores)

    fun update(marcadorEntity: MarcadorEntity) = marcadorDao.update(marcadorEntity)

    fun noEsFavorito(nombre: String) = marcadorDao.noEsFavorito(nombre)

    fun esFavorito(name: String) = marcadorDao.esFavorito(name)

    fun delete(marcadorEntity: MarcadorEntity) = marcadorDao.delete(marcadorEntity)

}
