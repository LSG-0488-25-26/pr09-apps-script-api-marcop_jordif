package com.example.dokkanapi.data.remote

import com.example.dokkanapi.data.model.ApiResponse
import kotlinx.serialization.json.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("exec")
    suspend fun getAllCards(
        @Query("action") action: String = "getAllCards"
    ): ApiResponse<List<JsonObject>>

    @GET("exec")
    suspend fun getCardsByType(
        @Query("action") action: String = "getCardsByType",
        @Query("type") type: String
    ): ApiResponse<List<JsonObject>>
}