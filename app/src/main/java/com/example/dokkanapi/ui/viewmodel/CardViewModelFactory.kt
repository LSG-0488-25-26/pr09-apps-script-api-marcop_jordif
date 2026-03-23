package com.example.dokkanapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dokkanapi.domain.useCase.AddCommentUseCase
import com.example.dokkanapi.domain.useCase.GetCardsByTypeUseCase
import com.example.dokkanapi.domain.useCase.GetCardsUseCase

class CardViewModelFactory(
    private val getCardsUseCase: GetCardsUseCase,
    private val getCardsByTypeUseCase: GetCardsByTypeUseCase,
    private val addCommentUseCase: AddCommentUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CardViewModel(getCardsUseCase, getCardsByTypeUseCase, addCommentUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}