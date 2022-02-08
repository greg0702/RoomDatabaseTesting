package my.com.testroomdb.data

import androidx.lifecycle.LiveData

//repository abstract access to multiple data source (optional but best practice for code separation & architecture)
class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

}