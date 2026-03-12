package com.example.dokkanapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dokkanapi.data.model.Card
import com.example.dokkanapi.domain.useCase.GetCardsUseCase
import com.example.dokkanapi.domain.usecase.GetCardsByTypeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CardViewModel(
    private val getCardsUseCase: GetCardsUseCase,
    private val getCardsByTypeUseCase: GetCardsByTypeUseCase  // AFEGIT
) : ViewModel() {

    private val _cards = MutableStateFlow<List<Card>>(emptyList())
    val cards: StateFlow<List<Card>> = _cards

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadCards(apiKey: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = getCardsUseCase(apiKey)

            result.onSuccess { cards ->
                _cards.value = cards
            }.onFailure { exception ->
                _error.value = exception.message
            }

            _isLoading.value = false
        }
    }

    // NOVA FUNCIO
    fun loadCardsByType(apiKey: String, type: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            // PASSEM ELS DOS PARAMETRES
            val result = getCardsByTypeUseCase(apiKey, type)

            result.onSuccess { cards ->
                _cards.value = cards
            }.onFailure { exception ->
                _error.value = exception.message
            }

            _isLoading.value = false
        }
    }
}