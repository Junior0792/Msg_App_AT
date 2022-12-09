package com.example.msg_app.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.msg_app.models.UserList
import com.example.msg_app.repository.MsgAppRepository
import com.example.msg_app.service.InstanceService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {


    lateinit var recyclerListData: MutableLiveData<UserList>

    init {
        recyclerListData = MutableLiveData()

    }

    fun getUserListObserverable() : MutableLiveData<UserList> {
        return recyclerListData
    }

    fun getUsersList() {

        val retroInstance = Instancia.getInstancia().create(InstanceService::class.java)
        val call = retroInstance.getUsersList()
        call.enqueue(object : Callback<UserList> {
            override fun onFailure(call: Call<UserList>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                if(response.isSuccessful) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }
        })
    }

    fun searchUser(searchText: String) {

        val retroInstance = Instancia.getInstancia().create(InstanceService::class.java)
        val call = retroInstance.searchUsers(searchText)
        call.enqueue(object : Callback<UserList>{
            override fun onFailure(call: Call<UserList>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                if(response.isSuccessful) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }
        })
    }


    val repository = MsgAppRepository.get()

    fun getCurrentUserEmail(): String {
        return repository.getCurrentUser()?.email ?: "Email não encontrado"
    }

    fun logout() {
        repository.logout()
    }
}