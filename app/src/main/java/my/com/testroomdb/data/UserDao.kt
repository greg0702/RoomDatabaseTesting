package my.com.testroomdb.data

import androidx.lifecycle.LiveData
import androidx.room.*
import my.com.testroomdb.model.User

// data access object
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) //onConflict can specify what to do if have item with same ID is added
    suspend fun addUser(user: User) //suspend is for calling the method using coroutine

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_table") //query meaning -> delete all from user_table
    suspend fun deleteAllUser()

    @Query("SELECT * FROM user_table ORDER BY id ASC") //query meaning -> select all from user_table and order it by ID ascending
    fun readAllData(): LiveData<List<User>> //this function return a list of user as livedata

}