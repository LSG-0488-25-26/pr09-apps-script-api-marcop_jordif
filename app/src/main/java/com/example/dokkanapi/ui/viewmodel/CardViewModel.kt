package com.example.dokkanapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dokkanapi.domain.useCase.GetCardsUseCase
import com.tuapp.dokkanbattle.data.model.Card
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardViewModel(
    private val getCardsUseCase: GetCardsUseCase
) : ViewModel() {

    private val _cards = MutableStateFlow<List<Card>>(emptyList())
    val cards: StateFlow<List<Card>> = _cards.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

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
}