package my.com.testroomdb.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// data access object
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) //onConflict can specify what to do if have item with same ID is added
    suspend fun addUser(user:User) //suspend is for calling the method using coroutine

    @Query("SELECT * FROM user_table ORDER BY id ASC") //query meaning -> select all from user_table and order it by ID ascending
    fun readAllData(): LiveData<List<User>> //this function return a list of user as livedata

}