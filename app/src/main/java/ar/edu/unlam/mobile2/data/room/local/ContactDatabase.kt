package ar.edu.unlam.mobile2.data.room.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unlam.mobile2.data.room.model.ContactsFromPhone

@Database(entities = [ContactsFromPhone::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactosDAO(): ContactFavDAO
}