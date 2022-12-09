package com.example.msg_app.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.msg_app.models.Usuario
import com.example.msg_app.repository.MsgAppRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult



class LoginViewModel : ViewModel() {

    val _email = MutableLiveData("")
    val email: LiveData<String> = _email
    fun setEmail(value: String) {
        _email.postValue(value)
    }

    val _password = MutableLiveData("")
    val password: LiveData<String> = _password
    fun setPassword(value: String) {
        _password.postValue(value)
    }

    val _endereco = MutableLiveData(Endereco())
    val endereco: LiveData<Endereco> = _endereco
    fun setEndereco(value: Endereco) {
        _endereco.postValue(value)
    }


    val TAG = "ViewModel"
    val repository = MsgAppRepository.get()

    // Auth:
//
    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }

    //
    fun login(
        email: String,
        password: String
    ): Task<AuthResult> {
        return repository.logarComEmailESenha(email, password)
    }

    //
    fun signOn(
        email: String,
        password: String
    ): Task<AuthResult> {
        return repository.cadastrarUsuarioComSenha(
            email,
            password
        )
    }

    // consome API de cep
    suspend fun fetchEnderecoFromCep(cep: String): Endereco {
        return repository.fetchEnderecoFromCep(cep)
    }

    fun cadastrar() {
        signOn(email?.value ?: "", password?.value ?: "").addOnSuccessListener {
            val uid = it.user?.uid ?: ""
            repository.criarUsuario(uid, getUsuario())
        }.addOnFailureListener {

        }

    }

    fun getUsuario(): Usuario {
        return Usuario(
            nome = "Nome",
            email = email?.value ?: "",
            cep = endereco.value?.cep ?: "",
            logradouro = endereco.value?.logradouro ?: "",
            bairro = endereco.value?.bairro ?: "",
            cidade = endereco.value?.cidade ?: "",
            estado = endereco.value?.estado ?: ""
        )
    }

}