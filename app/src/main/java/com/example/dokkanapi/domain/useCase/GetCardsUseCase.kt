package com.example.dokkanapi.domain.useCase

import com.example.dokkanapi.data.model.Card
import com.example.dokkanapi.data.repository.CardRepository

class GetCardsUseCase(
    private val repository: CardRepository
) {
    suspend operator fun invoke(): Result<List<Card>> {
        return repository.getAllCards()
    }
}