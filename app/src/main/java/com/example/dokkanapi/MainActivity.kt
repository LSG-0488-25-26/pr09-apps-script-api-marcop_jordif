package com.example.dokkanapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import com.example.dokkanapi.data.remote.RetrofitClient
import com.example.dokkanapi.data.repository.CardRepository
import com.example.dokkanapi.domain.useCase.AddCommentUseCase
import com.example.dokkanapi.domain.useCase.GetCardsByTypeUseCase
import com.example.dokkanapi.domain.useCase.GetCardsUseCase
import com.example.dokkanapi.ui.theme.DokkanApiTheme
import com.example.dokkanapi.ui.viewmodel.CardViewModel
import com.example.dokkanapi.ui.viewmodel.CardViewModelFactory
import com.example.dokkanbattle.ui.screens.CardListScreen

class MainActivity : ComponentActivity() {

    private val viewModel: CardViewModel by viewModels {
        val repository = CardRepository(RetrofitClient.apiService)
        CardViewModelFactory(
            GetCardsUseCase(repository),
            GetCardsByTypeUseCase(repository),
            AddCommentUseCase(repository)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DokkanApiTheme {
                Scaffold {
                    CardListScreen(viewModel = viewModel)
                }
            }
        }
    }
}