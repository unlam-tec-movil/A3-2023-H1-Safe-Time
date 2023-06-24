package ar.edu.unlam.mobile2.data.room.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ar.edu.unlam.mobile2.data.room.model.ContactsFromPhone

@Dao
interface ContactFavDAO {


    @Query("SELECT * FROM contactosfav WHERE name LIKE :name")
    fun getByName(name: String): ContactsFromPhone

    @Query("SELECT * FROM contactosfav WHERE number LIKE :number")
    fun getByNumber(number: String): ContactsFromPhone

    @Query("SELECT * FROM contactosfav")
    fun getAll(): List<ContactsFromPhone>

    @Query("DELETE FROM contactosfav WHERE number LIKE :number AND `default` = 0")
    fun trueDelete(number: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(new: ContactsFromPhone)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<ContactsFromPhone>)

    @Update
    fun update(update: ContactsFromPhone)

    @Update
    fun updateAll(updates: List<ContactsFromPhone>)

    @Delete
    fun delete(remove: ContactsFromPhone)

    @Delete
    fun deleteAll(removes: List<ContactsFromPhone>)
}