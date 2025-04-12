package com.example.cacatrackermobileapp.data.api

import com.example.cacatrackermobileapp.data.models.Users

import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    private val client: OkHttpClient
        get() = OkHttpClient()

    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int): Call<Users>

    suspend fun login(username: String, password: String): Response {
        val mediaType = "application/json".toMediaType()
        val json = Gson().toJson(mapOf("username" to username, "password" to password))
        val body = RequestBody.create(mediaType, json)

        val request = Request.Builder()
            .url("https://yourapi.com/login")
            .post(body)
            .build()

        return client.newCall(request).execute()
    }
}