package com.example.dokkanapi.data.repository

import com.tuapp.dokkanbattle.data.mapper.CardMapper
import com.tuapp.dokkanbattle.data.model.Card
import com.tuapp.dokkanbattle.data.remote.ApiService

class CardRepository(
    private val apiService: ApiService
) {

    suspend fun getAllCards(apiKey: String): Result<List<Card>> {
        return try {
            val response = apiService.getAllCards(apiKey)

            if (response.success && response.data != null) {
                // 👇 AQUÍ USES EL MAPPER
                val cards = CardMapper.mapToCardList(response.data)
                Result.success(cards)
            } else {
                Result.failure(Exception(response.error ?: "Error desconegut"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCardsByType(apiKey: String, type: String): Result<List<Card>> {
        return try {
            val response = apiService.getCardsByType(apiKey, type)

            if (response.success && response.data != null) {
                // 👇 AQUÍ TAMBÉ
                val cards = CardMapper.mapToCardList(response.data)
                Result.success(cards)
            } else {
                Result.failure(Exception(response.error ?: "Error desconegut"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}