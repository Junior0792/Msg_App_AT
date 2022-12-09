package com.example.msg_app.main

import okhttp3.OkHttpClient
import retrofit2.Retrofit

class Instancia {

    companion object {

        val baseUrl = "https://gorest.co.in/public-api/"

        fun getInstancia(): Retrofit {

            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}