package com.example.dokkanapi.data.remote

import com.example.dokkanapi.data.model.ApiResponse
import kotlinx.serialization.json.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("exec")
    suspend fun getAllCards(
        @Query("api_key") apiKey: String,
        @Query("action") action: String = "getAllCards"
    ): ApiResponse<List<JsonObject>>

    @GET("exec")
    suspend fun getCardsByType(
        @Query("api_key") apiKey: String,
        @Query("action") action: String = "getCardsByType",
        @Query("type") type: String
    ): ApiResponse<List<JsonObject>>

    @GET("exec")
    suspend fun getComments(
        @Query("api_key") apiKey: String,
        @Query("action") action: String = "getComments"
    ): ApiResponse<List<JsonObject>>
}