package com.example.dokkanapi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dokkanapi.data.model.Card
import com.example.dokkanapi.ui.viewmodel.CardViewModel
import com.example.dokkanpi.ui.screens.StatsTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListScreen(viewModel: CardViewModel = viewModel(), modifier: Modifier) {
    val cards     by viewModel.cards.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error     by viewModel.error.collectAsState()

    var selectedTab  by remember { mutableStateOf(0) }
    var selectedType by remember { mutableStateOf<String?>(null) }
    var selectedCard by remember { mutableStateOf<Card?>(null) }

    val types = listOf("ALL", "STR", "AGL", "TEQ", "INT", "PHY")
    val tabs  = listOf("Cards", "Buscar", "Stats")

    LaunchedEffect(Unit) { viewModel.loadCards() }

    LaunchedEffect(selectedType) {
        if (selectedType == null || selectedType == "ALL")
            viewModel.loadCards()
        else
            viewModel.loadCardsByType(selectedType!!)
    }

    selectedCard?.let { card ->
        CardDetailScreen(card = card, onBack = { selectedCard = null })
        return
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(listOf(Color(0xFF1A1A2E), Color(0xFF16213E)))
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("⚡ DOKKAN", fontSize = 22.sp, fontWeight = FontWeight.Black, color = Color(0xFFFFD700), letterSpacing = 3.sp)
                    Text(" BATTLE",  fontSize = 22.sp, fontWeight = FontWeight.Black, color = Color.White,        letterSpacing = 3.sp)
                    Spacer(Modifier.weight(1f))
                    Text("${cards.size} cartes", fontSize = 12.sp, color = Color(0xFF90CAF9))
                }

                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFFFFD700),
                    indicator = { tabPositions ->
                        SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = Color(0xFFFFD700)
                        )
                    }
                ) {
                    tabs.forEachIndexed { i, title ->
                        Tab(
                            selected = selectedTab == i,
                            onClick  = { selectedTab = i },
                            text = {
                                Text(
                                    title,
                                    color = if (selectedTab == i) Color(0xFFFFD700) else Color(0xFF78909C),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        )
                    }
                }

                if (selectedTab == 0) {
                    LazyRow(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(types) { type ->
                            val isSelected = (type == "ALL" && selectedType == null) || type == selectedType
                            val color = if (type == "ALL") Color(0xFF78909C) else typeColor(type)
                            FilterChip(
                                selected = isSelected,
                                onClick  = { selectedType = if (type == "ALL") null else type },
                                label    = { Text(type, fontWeight = FontWeight.Bold, fontSize = 12.sp) },
                                colors   = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = color,
                                    selectedLabelColor     = Color.White,
                                    containerColor         = Color(0xFF1A1A2E),
                                    labelColor             = color
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled             = true,
                                    selected            = isSelected,
                                    borderColor         = color,
                                    selectedBorderColor = color
                                )
                            )
                        }
                    }
                }
            }
        },
        containerColor = Color(0xFF0D0D1A)
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (selectedTab) {
                0 -> CardsTab(cards = cards, isLoading = isLoading, error = error, onCardClick = { selectedCard = it })
                1 -> SearchTab(viewModel = viewModel, onCardClick = { selectedCard = it })
                2 -> StatsTab(cards = cards)
            }
        }
    }
}

@Composable
fun CardsTab(cards: List<Card>, isLoading: Boolean, error: String?, onCardClick: (Card) -> Unit) {
    when {
        isLoading       -> LoadingView()
        error != null   -> ErrorView(error)
        cards.isEmpty() -> EmptyView()
        else -> LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(cards) { card -> CardGridItem(card = card, onClick = { onCardClick(card) }) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTab(viewModel: CardViewModel, onCardClick: (Card) -> Unit) {
    var query     by remember { mutableStateOf("") }
    val cards     by viewModel.cards.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val characters = listOf("Goku", "Vegeta", "Gohan", "Gogeta")

    val filtered = remember(query, cards) {
        if (query.isBlank()) cards
        else cards.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.title.contains(query, ignoreCase = true)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            "BUSCAR PERSONATGE",
            color = Color(0xFFFFD700),
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp,
            fontSize = 13.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(characters) { char ->
                AssistChip(
                    onClick = { query = char },
                    label   = { Text(char, fontWeight = FontWeight.Bold, color = Color(0xFFFFD700)) },
                    colors  = AssistChipDefaults.assistChipColors(containerColor = Color(0xFF1A1A2E)),
                    border  = AssistChipDefaults.assistChipBorder(
                        enabled = true,
                        borderColor = Color(0xFFFFD700).copy(alpha = 0.4f)
                    )
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value         = query,
            onValueChange = { query = it },
            placeholder   = { Text("goku, vegeta, gohan...", color = Color(0xFF546E7A)) },
            leadingIcon   = { Icon(Icons.Default.Search, null, tint = Color(0xFFFFD700)) },
            trailingIcon  = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(Icons.Default.Clear, null, tint = Color(0xFF78909C))
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = Color(0xFFFFD700),
                unfocusedBorderColor = Color(0xFF37474F),
                focusedTextColor     = Color.White,
                unfocusedTextColor   = Color.White,
                cursorColor          = Color(0xFFFFD700)
            ),
            modifier   = Modifier.fillMaxWidth(),
            shape      = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(Modifier.height(16.dp))

        if (isLoading) {
            LoadingView()
        } else {
            Text(
                "${filtered.size} resultats",
                color = Color(0xFF78909C),
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filtered) { card -> CardListItem(card = card, onClick = { onCardClick(card) }) }
            }
        }
    }
}