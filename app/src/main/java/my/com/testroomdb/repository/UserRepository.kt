package my.com.testroomdb.repository

import androidx.lifecycle.LiveData
import my.com.testroomdb.data.UserDao
import my.com.testroomdb.model.User

//repository abstract access to multiple data source (optional but best practice for code separation & architecture)
class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){ userDao.addUser(user) }

    suspend fun updateUser(user: User){ userDao.updateUser(user) }

    suspend fun deleteUser(user: User){ userDao.deleteUser(user) }

    suspend fun deleteAllUsers(){ userDao.deleteAllUser() }

}