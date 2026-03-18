package com.example.dokkanapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dokkanapi.domain.useCase.GetCardsUseCase
import com.example.dokkanapi.domain.useCase.GetCardsByTypeUseCase

class CardViewModelFactory(
    private val getCardsUseCase: GetCardsUseCase,
    private val getCardsByTypeUseCase: GetCardsByTypeUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CardViewModel(getCardsUseCase, getCardsByTypeUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}