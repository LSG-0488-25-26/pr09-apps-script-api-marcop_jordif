package com.example.dokkanapi.domain.usecase

import com.example.dokkanapi.data.model.Card
import com.example.dokkanapi.data.repository.CardRepository

class GetCardsByTypeUseCase(
    private val repository: CardRepository
) {
    suspend operator fun invoke(apiKey: String, type: String): Result<List<Card>> {
        return repository.getCardsByType(apiKey, type)
    }
}