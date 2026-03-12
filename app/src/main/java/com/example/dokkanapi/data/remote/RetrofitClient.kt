package com.example.dokkanapi.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // TODO: Canvia aquesta URL per la teva URL real de Apps Script
    private const val BASE_URL = "https://script.google.com/macros/s/AKfycbyiHzDN6D6oCE_dHQi5B8SViZpuNj5aEtcBvHH7tmjpngRlYTgq8paxDuKUemCkgfNiAQ/"

    // Configuració de Json per ignorar camps desconeguts
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    // Configuració del client OkHttp amb logging
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    // Configuració de Retrofit
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    // Creació de l'ApiService - AQUÍ ESTÀ LA CLAU
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}