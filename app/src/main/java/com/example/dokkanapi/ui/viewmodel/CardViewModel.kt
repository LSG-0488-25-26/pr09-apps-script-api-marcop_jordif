package com.example.dokkanapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dokkanapi.data.model.Card
import com.example.dokkanapi.domain.useCase.AddCommentUseCase
import com.example.dokkanapi.domain.useCase.GetCardsByTypeUseCase
import com.example.dokkanapi.domain.useCase.GetCardsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CardViewModel(
    private val getCardsUseCase: GetCardsUseCase,
    private val getCardsByTypeUseCase: GetCardsByTypeUseCase,
    private val addCommentUseCase: AddCommentUseCase
) : ViewModel() {

    private val _cards = MutableStateFlow<List<Card>>(emptyList())
    val cards: StateFlow<List<Card>> = _cards

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _commentResult = MutableStateFlow<String?>(null)
    val commentResult: StateFlow<String?> = _commentResult

    fun loadCards() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            getCardsUseCase()
                .onSuccess { _cards.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }

    fun loadCardsByType(type: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            getCardsByTypeUseCase(type)
                .onSuccess { _cards.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }

    fun addComment(cardId: String, user: String, comment: String) {
        viewModelScope.launch {
            addCommentUseCase(cardId, user, comment)
                .onSuccess { _commentResult.value = "Comentari afegit!" }
                .onFailure { _commentResult.value = "Error: ${it.message}" }
        }
    }

    fun clearCommentResult() {
        _commentResult.value = null
    }
}