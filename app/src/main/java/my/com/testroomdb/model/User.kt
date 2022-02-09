package my.com.testroomdb.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//represent an entity
@Parcelize
@Entity(tableName = "user_table") //annotate this data class as entity with table name of user_table in room db
data class User(

    @PrimaryKey(autoGenerate = true) //annotate id as primary key; autoGenerate will auto generate unique id
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int

): Parcelable