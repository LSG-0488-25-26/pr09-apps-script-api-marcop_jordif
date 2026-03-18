package com.example.dokkanapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dokkanapi.data.model.Card
import com.example.dokkanapi.domain.useCase.GetCardsUseCase
import com.example.dokkanapi.domain.useCase.GetCardsByTypeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CardViewModel(
    private val getCardsUseCase: GetCardsUseCase,
    private val getCardsByTypeUseCase: GetCardsByTypeUseCase
) : ViewModel() {

    private val _cards = MutableStateFlow<List<Card>>(emptyList())
    val cards: StateFlow<List<Card>> = _cards

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadCards(apiKey: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result: Result<List<Card>> = getCardsUseCase(apiKey)

            result.onSuccess { cardList: List<Card> ->
                _cards.value = cardList
            }.onFailure { exception: Throwable ->
                _error.value = exception.message
            }

            _isLoading.value = false
        }
    }

    fun loadCardsByType(apiKey: String, type: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result: Result<List<Card>> = getCardsByTypeUseCase(apiKey, type)

            result.onSuccess { cardList: List<Card> ->
                _cards.value = cardList
            }.onFailure { exception: Throwable ->
                _error.value = exception.message
            }

            _isLoading.value = false
        }
    }
}