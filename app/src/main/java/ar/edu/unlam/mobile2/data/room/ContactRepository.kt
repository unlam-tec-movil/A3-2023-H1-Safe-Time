package ar.edu.unlam.mobile2.data.room

import ar.edu.unlam.mobile2.data.room.local.ContactFavDAO
import ar.edu.unlam.mobile2.data.room.model.ContactsFromPhone
import ar.edu.unlam.mobile2.pantallaListaDeContactos.data.ContactosDeEmergencia
import javax.inject.Inject

class ContactRepository @Inject constructor(
    private val contactFavDAO: ContactFavDAO
) {

    init {
        insertAll(ContactosDeEmergencia.contactosDeEmergencia)
    }

    fun getByName(name: String): ContactsFromPhone = contactFavDAO.getByName(name)

    fun getByNumber(number: String): ContactsFromPhone = contactFavDAO.getByNumber(number)

    fun getAll(): List<ContactsFromPhone> = contactFavDAO.getAll()

    fun insert(new: ContactsFromPhone) = contactFavDAO.insert(new)

    fun insertAll(news: List<ContactsFromPhone>) = contactFavDAO.insertAll(news)

    fun update(update: ContactsFromPhone) = contactFavDAO.update(update)

    fun updateAll(updates: List<ContactsFromPhone>) = contactFavDAO.updateAll(updates)

    fun delete(remove: ContactsFromPhone) = contactFavDAO.delete(remove)

    fun trueDelete(name: String) = contactFavDAO.trueDelete(name)

    fun deleteAll(removes: List<ContactsFromPhone>) = contactFavDAO.deleteAll(removes)

}