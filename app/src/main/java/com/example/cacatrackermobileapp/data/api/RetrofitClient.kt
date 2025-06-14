package com.example.cacatrackermobileapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
     private const val BASE_URL = "http://10.0.2.2:8080/cacatrackerapi/rest/"
    //private const val BASE_URL ="http://192.168.1.134:8080/cacatrackerapi/rest/"
    //private const val BASE_URL ="https://4f58-2a0c-5a82-150e-cf00-b8bc-2413-b16f-24ec.ngrok-free.app/cacatrackerapi/rest/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}