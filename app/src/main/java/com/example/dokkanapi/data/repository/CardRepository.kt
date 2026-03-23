package com.example.dokkanapi.data.repository

import com.example.dokkanapi.data.mapper.CardMapper
import com.example.dokkanapi.data.model.Card
import com.example.dokkanapi.data.remote.ApiService

private const val API_KEY = "dokkan1919"

class CardRepository(
    private val apiService: ApiService
) {

    suspend fun getAllCards(): Result<List<Card>> {
        return try {
            val response = apiService.getAllCards(apiKey = API_KEY)
            if (response.success && response.data != null) {
                Result.success(CardMapper.mapToCardList(response.data))
            } else {
                Result.failure(Exception(response.error ?: "Error desconegut"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCardsByType(type: String): Result<List<Card>> {
        return try {
            val response = apiService.getCardsByType(apiKey = API_KEY, type = type)
            if (response.success && response.data != null) {
                Result.success(CardMapper.mapToCardList(response.data))
            } else {
                Result.failure(Exception(response.error ?: "Error desconegut"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}