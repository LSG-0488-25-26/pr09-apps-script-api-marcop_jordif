// ui/screens/CardListScreen.kt
package com.tuapp.dokkanbattle.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dokkanapi.ui.viewmodel.CardViewModel
import com.example.dokkanapi.data.model.Card

@Composable
fun CardListScreen(
    viewModel: CardViewModel = viewModel()
) {
    val cards by viewModel.cards.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Carrega les dades quan la pantalla es mostri
    LaunchedEffect(Unit) {
        viewModel.loadCards("dokkan123")  // La teva API Key
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (error != null) {
            Text(
                text = "Error: $error",
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cards) { card ->
                    CardItem(card = card)
                }
            }
        }
    }
}

@Composable
fun CardItem(card: Card) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = card.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "${card.title} - ${card.type} - ${card.rarity}",
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("HP: ${card.hp}")
                Text("ATK: ${card.attack}")
                Text("DEF: ${card.defense}")
            }
        }
    }
}