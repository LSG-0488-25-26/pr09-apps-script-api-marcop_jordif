package com.example.dokkanapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import com.example.dokkanapi.data.remote.ApiService
import com.example.dokkanapi.data.repository.CardRepository
import com.example.dokkanapi.domain.useCase.GetCardsByTypeUseCase
import com.example.dokkanapi.domain.useCase.GetCardsUseCase
import com.example.dokkanapi.ui.theme.DokkanApiTheme
import com.example.dokkanapi.ui.viewmodel.CardViewModel
import com.example.dokkanapi.ui.viewmodel.CardViewModelFactory
import com.example.dokkanbattle.ui.screens.CardListScreen
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class MainActivity : ComponentActivity() {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://script.google.com/macros/s/YOUR_SCRIPT_ID/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ApiService::class.java)
    }

    private val viewModel: CardViewModel by viewModels {
        val repository = CardRepository(apiService)
        val cardViewModelFactory = CardViewModelFactory(
            com.example.dokkanapi.domain.useCase.GetCardsUseCase(repository),
            GetCardsByTypeUseCase(repository)
        )
        cardViewModelFactory
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