package com.example.dokkanapi.data.repository

import com.example.dokkanapi.data.mapper.CardMapper
import com.example.dokkanapi.data.model.Card
import com.example.dokkanapi.data.remote.ApiService

class CardRepository(
    private val apiService: ApiService
) {

    suspend fun getAllCards(): Result<List<Card>> {
        return try {
            val response = apiService.getAllCards()
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
            val response = apiService.getCardsByType(type = type)
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