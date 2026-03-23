package com.example.dokkanapi.domain.useCase

import com.example.dokkanapi.data.repository.CardRepository

class AddCommentUseCase(
    private val repository: CardRepository
) {
    suspend operator fun invoke(cardId: String, user: String, comment: String): Result<Unit> {
        return repository.addComment(cardId, user, comment)
    }
}