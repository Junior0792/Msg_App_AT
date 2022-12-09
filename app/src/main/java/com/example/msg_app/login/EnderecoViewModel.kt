package com.example.msg_app.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.msg_app.main.Instancia
import com.example.msg_app.models.User
import com.example.msg_app.models.UserResponse
import com.example.msg_app.service.InstanceService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnderecoViewModel : ViewModel() {

    lateinit var createNewUserLiveData: MutableLiveData<UserResponse?>
    lateinit var loadUserData: MutableLiveData<UserResponse?>
    lateinit var deleteUserLiveData: MutableLiveData<UserResponse?>

    init {
        createNewUserLiveData = MutableLiveData()
        loadUserData = MutableLiveData()
        deleteUserLiveData = MutableLiveData()
    }

    fun getEnderecoObservable(): MutableLiveData<UserResponse?> {
        return  createNewUserLiveData
    }

    fun getDeleteUserObservable(): MutableLiveData<UserResponse?> {
        return  deleteUserLiveData
    }

    fun getLoadUserObservable(): MutableLiveData<UserResponse?> {
        return  loadUserData
    }

    fun createUser(user: User) {
        val instancia = Instancia.getInstancia().create(InstanceService::class.java)
        val call = instancia.createUser(user)
        call.enqueue(object : Callback<UserResponse?> {
            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                if(response.isSuccessful) {
                    createNewUserLiveData.postValue(response.body())
                } else {
                    createNewUserLiveData.postValue(null)
                }
            }
        })
    }

    fun updateUser(user_id: String, user: User) {
        val instancia = Instancia.getInstancia().create(InstanceService::class.java)
        val call = instancia.updateUser(user_id, user)
        call.enqueue(object : Callback<UserResponse?> {
            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                if(response.isSuccessful) {
                    createNewUserLiveData.postValue(response.body())
                } else {
                    createNewUserLiveData.postValue(null)
                }
            }
        })
    }

    fun deleteUser(user_id: String?) {
        val instancia = Instancia.getInstancia().create(InstanceService::class.java)
        val call = instancia.deleteUser(user_id!!)
        call.enqueue(object : Callback<UserResponse?> {
            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                deleteUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                if(response.isSuccessful) {
                    deleteUserLiveData.postValue(response.body())
                } else {
                    deleteUserLiveData.postValue(null)
                }
            }
        })
    }

    fun getUserData(user_id: String?) {
        val instancia = Instancia.getInstancia().create(InstanceService::class.java)
        val call = instancia.getUser(user_id!!)
        call.enqueue(object : Callback<UserResponse?> {
            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                loadUserData.postValue(null)
            }

            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                if(response.isSuccessful) {
                    loadUserData.postValue(response.body())
                } else {
                    loadUserData.postValue(null)
                }
            }
        })
    }
}