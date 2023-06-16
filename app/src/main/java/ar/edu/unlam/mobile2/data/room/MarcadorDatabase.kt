package ar.edu.unlam.mobile2.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MarcadorEntity::class], version = 1)
abstract class MarcadorDatabase : RoomDatabase() {
    abstract fun marcadorDao(): MarcadorDAO

}