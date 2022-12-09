package com.example.msg_app.models

data class Usuario(
    val nome: String? = "",
    val email: String? = "",
    val cep :String?  = "",
    val logradouro: String? = "",
    val bairro: String? = "",
    val cidade: String?  = "",
    val estado: String? = ""
)
