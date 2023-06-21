package ar.edu.unlam.mobile2.data.room.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unlam.mobile2.data.room.model.MarcadorEntity

@Database(entities = [MarcadorEntity::class], version = 1)
abstract class MarcadorDatabase : RoomDatabase() {
    abstract fun marcadorDao(): MarcadorDAO

}