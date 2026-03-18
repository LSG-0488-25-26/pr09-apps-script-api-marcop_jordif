package com.example.dokkanapi.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "https://script.google.com/macros/s/AKfycbxLAvVdpj-bsPdg5VtYxPX0bS5CuygQOcp2Tc81SfE4ddag9smXF8WBAGcQXssYsESHsg/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    private val redirectInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        var response = chain.proceed(originalRequest)
        var redirectCount = 0
        val maxRedirects = 5

        println("Peticio inicial: ${originalRequest.url}")

        while (response.isRedirect && redirectCount < maxRedirects) {
            val location = response.header("Location")
            println("Redireccio ${redirectCount + 1} a: $location")

            if (location.isNullOrEmpty()) break

            response.close()

            val redirectRequest = originalRequest.newBuilder()
                .url(location)
                .header("Accept", "application/json")
                .build()

            response = chain.proceed(redirectRequest)
            redirectCount++
        }

        println("Content-Type: ${response.header("Content-Type")}")
        println("Codi resposta: ${response.code}")

        response
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(redirectInterceptor)
        .addInterceptor(loggingInterceptor)
        .followRedirects(false)
        .followSslRedirects(false)
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