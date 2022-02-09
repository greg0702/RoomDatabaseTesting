package my.com.testroomdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.com.testroomdb.data.UserDatabase
import my.com.testroomdb.repository.UserRepository
import my.com.testroomdb.model.User

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<User>>
    private val repository: UserRepository

    init {

        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData

    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) { repository.addUser(user) }
    }

    fun updateUser(user: User){
        viewModelScope.launch(Dispatchers.IO) { repository.updateUser(user) }
    }

    fun deleteUser(user: User){
        viewModelScope.launch(Dispatchers.IO) { repository.deleteUser(user) }
    }

    fun deleteAllUser(){
        viewModelScope.launch(Dispatchers.IO) { repository.deleteAllUsers() }
    }

    fun readAllData(): LiveData<List<User>>{ return readAllData }

}