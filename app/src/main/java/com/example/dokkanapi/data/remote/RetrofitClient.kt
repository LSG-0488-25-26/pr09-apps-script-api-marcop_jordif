// data/remote/RetrofitClient.kt
package com.tuapp.dokkanbattle.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "https://script.google.com/macros/s/AKfycbyiHzDN6D6oCE_dHQi5B8SViZpuNj5aEtcBvHH7tmjpngRlYTgq8paxDuKUemCkgfNiAQ/"  // La teva URL

    private val json = Json {
        ignoreUnknownKeys = true  // Important: ignora camps que no tenim al model
        coerceInputValues = true
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY  // Per veure les peticions al Logcat
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}