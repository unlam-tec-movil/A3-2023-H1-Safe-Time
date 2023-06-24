package ar.edu.unlam.mobile2.data.room.model

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ar.edu.unlam.mobile2.R

@Entity(tableName = "ContactosFav")
data class ContactsFromPhone(
    @ColumnInfo("name")
    val name: String = "",
    @PrimaryKey
    @ColumnInfo("number")
    val number: String = "",
    @ColumnInfo("imagen")
    @DrawableRes val imagen: Int = R.drawable.ic_launcher_foreground,
    @ColumnInfo(name = "default") val esDefault : Boolean = false
)