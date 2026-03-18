package com.example.dokkanapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.dokkanapi.data.repository.CardRepository
import com.example.dokkanapi.data.remote.RetrofitClient
import com.example.dokkanapi.domain.useCase.GetCardsByTypeUseCase
import com.example.dokkanapi.domain.useCase.GetCardsUseCase
import com.example.dokkanapi.ui.screens.CardListScreen
import com.example.dokkanapi.ui.theme.DokkanApiTheme
import com.example.dokkanapi.ui.viewmodel.CardViewModel
import com.example.dokkanapi.ui.viewmodel.CardViewModelFactory

class MainActivity : ComponentActivity() {

    private val apiService = RetrofitClient.apiService

    private val viewModel: CardViewModel by viewModels {
        val repository = CardRepository(apiService)
        CardViewModelFactory(
            GetCardsUseCase(repository),
            GetCardsByTypeUseCase(repository)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DokkanApiTheme {
                Scaffold { paddingValues ->
                    CardListScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}