package com.example.dokkanapi.domain.useCase

import com.example.dokkanapi.data.repository.CardRepository
import com.example.dokkanapi.data.model.Card

class GetCardsUseCase(
    private val repository: CardRepository
) {
    suspend operator fun invoke(apiKey: String): Result<List<Card>> {
        return repository.getAllCards(apiKey)
    }
}