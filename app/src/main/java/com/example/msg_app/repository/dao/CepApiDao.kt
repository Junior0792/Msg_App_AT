package com.example.msg_app.repository.dao

import retrofit2.http.GET
import retrofit2.http.Path


interface CepApiDao {

    //https://viacep.com.br/
    @GET("ws/{cep}/json")
    suspend fun getEncedereco (
        @Path("cep") cep: String
    ) : Endereco

}