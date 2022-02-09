package my.com.testroomdb.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

//represent an entity
@Entity(tableName = "user_table") //annotate this data class as entity with table name of user_table in room db
data class User(

    @PrimaryKey(autoGenerate = true) //annotate id as primary key; autoGenerate will auto generate unique id
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int,
    @Embedded
    val address: Address

)

data class Address(
    val streetName: String,
    val streetNumber: Int,
    val postCode: Int,
    val houseNumber: Int
)