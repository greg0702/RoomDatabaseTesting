package my.com.testroomdb.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import my.com.testroomdb.model.User


//database
@Database(entities = [User::class], version = 1, exportSchema = false) //specify entity in the database; perimeter required: entities, database version, whether to export db schema
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{

        @Volatile //rights for this field are visible to other thread immediately
        private var INSTANCE: UserDatabase? = null //make user database singleton class (user database only have one instance of its class)

        fun getDatabase(context: Context): UserDatabase{

            val tempInstance = INSTANCE

            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){

                //everything inside this block will be protected from concurrent execution by multiple thread
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()

                INSTANCE = instance // replace the previous db instance with the new one to create singleton for database

                return instance

            }

        }

    }

}