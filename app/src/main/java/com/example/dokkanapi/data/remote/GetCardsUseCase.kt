// domain/usecase/GetCardsUseCase.kt
package com.tuapp.dokkanbattle.domain.usecase

import com.tuapp.dokkanbattle.data.model.Card
import com.tuapp.dokkanbattle.data.repository.CardRepository

class GetCardsUseCase(
    private val repository: CardRepository
) {
    suspend operator fun invoke(apiKey: String): Result<List<Card>> {
        return repository.getAllCards(apiKey)
    }
}