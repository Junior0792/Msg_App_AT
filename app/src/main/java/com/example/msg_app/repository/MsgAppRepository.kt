package com.example.msg_app.repository

import com.example.msg_app.models.Usuario
import com.example.msg_app.repository.dao.CepApiDao
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


class MsgAppRepository  private constructor() {

    private val BASE_URL =
        "https://viacep.com.br/"

    val retrofitCep: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val cepApiDao: CepApiDao = retrofitCep.create()

    companion object {

        private var INSTANCE: MsgAppRepository? = null

        fun initialize() {
            if (INSTANCE == null) {
                INSTANCE = MsgAppRepository()

                auth = Firebase.auth
                db = Firebase.firestore
                colecaoUsuarios = db.collection("usuarios")

            }
        }

        fun get(): MsgAppRepository {
            return INSTANCE
                ?: throw IllegalStateException("MsgAppRepository deve ser inicializado.")
        }


        lateinit var auth: FirebaseAuth

        lateinit var db: FirebaseFirestore

        lateinit var colecaoUsuarios: CollectionReference

    }


    // Entrar um usuário com um endereço de e-mail e senha
    // https://firebase.google.com/docs/auth/android/password-auth#sign_in_a_user_with_an_email_address_and_password
    fun logarComEmailESenha(
        email: String,
        password: String
    ): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun cadastrarUsuarioComSenha(
        email: String,
        password: String
    ): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }


    fun getCurrentUser() = auth.currentUser

    fun isLoggedIn() : Boolean {
        if (getCurrentUser()!= null) return true

        return false
    }

    fun logout(){
        auth.signOut()
    }

    // Uso do Firestore:

    fun criarUsuario(uid: String, usuario: Usuario) {
        colecaoUsuarios.document(uid).set(usuario)
    }

    // consumo de API:



}
