// data/remote/ApiService.kt
package com.example.dokkanapi.data.remote

import com.example.dokkanapi.data.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("exec")  // La teva URL base ja inclou /exec
    suspend fun getAllCards(
        @Query("api_key") apiKey: String,
        @Query("action") action: String = "getAllCards"
    ): ApiResponse<List<Map<String, Any>>>  // Retrofit convertirà automàticament

    @GET("exec")
    suspend fun getCardsByType(
        @Query("api_key") apiKey: String,
        @Query("action") action: String = "getCardsByType",
        @Query("type") type: String? = null // ARREGLAR!!!!!!!!!!!!!!!!!!!!
    ): ApiResponse<List<Map<String, Any>>>

    @GET("exec")
    suspend fun getComments(
        @Query("api_key") apiKey: String,
        @Query("action") action: String = "getComments"
    ): ApiResponse<List<Map<String, Any>>>
}