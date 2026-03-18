package com.example.dokkanapi.data.repository

import com.example.dokkanapi.data.mapper.CardMapper
import com.example.dokkanapi.data.model.Card
import com.example.dokkanapi.data.remote.ApiService
import java.io.IOException

class CardRepository(
    private val apiService: ApiService
) {

    suspend fun getAllCards(): Result<List<Card>> {
        return try {
            println("Sol licitant getAllCards")
            val response = apiService.getAllCards()
            println("Resposta rebuda: success=${response.success}, count=${response.count}")

            if (response.success && response.data != null) {
                val cards = CardMapper.mapToCardList(response.data)
                println("Cards mapejades: ${cards.size}")
                Result.success(cards)
            } else {
                val errorMsg = response.error ?: "Error desconegut"
                println("Error a la resposta: $errorMsg")
                Result.failure(Exception("Error de l'API: $errorMsg"))
            }
        } catch (e: retrofit2.HttpException) {
            println("Error HTTP: ${e.code()} - ${e.message()}")
            val errorBody = e.response()?.errorBody()?.string()
            println("Cos d error: $errorBody")
            Result.failure(IOException("Error HTTP ${e.code()}: ${e.message()}"))
        } catch (e: kotlinx.serialization.SerializationException) {
            println("Error de serialitzacio: ${e.message}")
            Result.failure(Exception("Error en processar les dades: ${e.message}"))
        } catch (e: Exception) {
            println("Error inesperat: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getCardsByType(type: String): Result<List<Card>> {
        return try {
            println("Sol licitant getCardsByType: $type")
            val response = apiService.getCardsByType(type = type)
            println("Resposta rebuda: success=${response.success}, count=${response.count}")

            if (response.success && response.data != null) {
                val cards = CardMapper.mapToCardList(response.data)
                println("Cards mapejades: ${cards.size}")
                Result.success(cards)
            } else {
                val errorMsg = response.error ?: "Error desconegut"
                println("Error a la resposta: $errorMsg")
                Result.failure(Exception("Error de l'API: $errorMsg"))
            }
        } catch (e: retrofit2.HttpException) {
            println("Error HTTP: ${e.code()} - ${e.message()}")
            val errorBody = e.response()?.errorBody()?.string()
            println("Cos d error: $errorBody")
            Result.failure(IOException("Error HTTP ${e.code()}: ${e.message()}"))
        } catch (e: kotlinx.serialization.SerializationException) {
            println("Error de serialitzacio: ${e.message}")
            Result.failure(Exception("Error en processar les dades: ${e.message}"))
        } catch (e: Exception) {
            println("Error inesperat: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }
}