package com.example.dokkanbattle.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dokkanapi.data.model.Card
import com.example.dokkanapi.ui.viewmodel.CardViewModel
import com.example.dokkanapi.ui.screens.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CardDetailScreen(card: Card, viewModel: CardViewModel, onBack: () -> Unit) {
    val typeCol    = typeColor(card.type)
    val rarityCol  = rarityColor(card.rarity)
    val commentResult by viewModel.commentResult.collectAsState()

    var username by remember { mutableStateOf("") }
    var commentText by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }

    // Show snackbar when comment result arrives
    LaunchedEffect(commentResult) {
        if (commentResult != null) {
            showSnackbar = true
            username = ""
            commentText = ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(card.name, color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color(0xFFFFD700))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1A1A2E))
            )
        },
        snackbarHost = {
            if (showSnackbar && commentResult != null) {
                Snackbar(
                    action = {
                        TextButton(onClick = {
                            showSnackbar = false
                            viewModel.clearCommentResult()
                        }) { Text("OK", color = Color(0xFFFFD700)) }
                    },
                    containerColor = Color(0xFF1A1A2E),
                    contentColor = Color.White,
                    modifier = Modifier.padding(8.dp)
                ) { Text(commentResult ?: "") }
            }
        },
        containerColor = Color(0xFF0D0D1A)
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ── Capçalera ──────────────────────────────────────────────────
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    listOf(typeCol.copy(alpha = 0.3f), Color.Transparent)
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Column {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                TypeBadge(card.type, typeCol, large = true)
                                RarityBadge(card.rarity, rarityCol, large = true)
                            }
                            Spacer(Modifier.height(12.dp))
                            Text(card.name,  color = Color.White,       fontSize = 22.sp, fontWeight = FontWeight.Black)
                            Text(card.title, color = Color(0xFF90CAF9), fontSize = 14.sp)
                            if (card.cost > 0) {
                                Text("Cost: ${card.cost}", color = Color(0xFF78909C), fontSize = 12.sp,
                                    modifier = Modifier.padding(top = 4.dp))
                            }
                        }
                    }
                }
            }

            // ── Stats ──────────────────────────────────────────────────────
            item {
                DetailSection("ESTADÍSTIQUES DE COMBAT") {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        BigStat("HP",  card.hp,      Color(0xFF66BB6A))
                        BigStat("ATK", card.attack,  Color(0xFFEF5350))
                        BigStat("DEF", card.defense, Color(0xFF42A5F5))
                    }
                }
            }

            // ── Leader Skill ───────────────────────────────────────────────
            if (card.leaderSkill.isNotBlank()) {
                item {
                    DetailSection("⭐ LEADER SKILL") {
                        Text(card.leaderSkill, color = Color(0xFFE0E0E0), fontSize = 14.sp, lineHeight = 20.sp)
                    }
                }
            }

            // ── Super Attack ───────────────────────────────────────────────
            if (card.superAttack.isNotBlank()) {
                item {
                    DetailSection("⚡ SUPER ATTACK") {
                        Text(card.superAttack, color = Color(0xFFE0E0E0), fontSize = 14.sp, lineHeight = 20.sp)
                    }
                }
            }

            // ── Passive Skill ──────────────────────────────────────────────
            if (card.passiveSkill.isNotBlank()) {
                item {
                    DetailSection("✨ PASSIVE SKILL") {
                        Text(card.passiveSkill, color = Color(0xFFE0E0E0), fontSize = 14.sp, lineHeight = 20.sp)
                    }
                }
            }

            // ── Link Skills ────────────────────────────────────────────────
            if (card.linkSkills.isNotEmpty()) {
                item {
                    DetailSection("🔗 LINK SKILLS") {
                        card.linkSkills.forEach { link ->
                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Text("▸ ", color = typeCol, fontWeight = FontWeight.Bold)
                                Text(link, color = Color(0xFFE0E0E0), fontSize = 13.sp)
                            }
                        }
                    }
                }
            }

            // ── Categories ─────────────────────────────────────────────────
            if (card.categories.isNotEmpty()) {
                item {
                    DetailSection("🏷 CATEGORIES") {
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            card.categories.forEach { cat ->
                                AssistChip(
                                    onClick = {},
                                    label  = { Text(cat, fontSize = 11.sp, color = Color(0xFF90CAF9)) },
                                    colors = AssistChipDefaults.assistChipColors(containerColor = Color(0xFF0D0D1A)),
                                    border = AssistChipDefaults.assistChipBorder(enabled = true, borderColor = Color(0xFF37474F))
                                )
                            }
                        }
                    }
                }
            }

            // ── Release dates ──────────────────────────────────────────────
            if (card.jpRelease != null || card.globalRelease != null) {
                item {
                    DetailSection("📅 LLANÇAMENT") {
                        card.jpRelease?.let {
                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Text("🇯🇵 JP: ", color = Color(0xFF78909C), fontSize = 13.sp)
                                Text(it, color = Color.White, fontSize = 13.sp)
                            }
                        }
                        card.globalRelease?.let {
                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Text("🌍 GL: ", color = Color(0xFF78909C), fontSize = 13.sp)
                                Text(it, color = Color.White, fontSize = 13.sp)
                            }
                        }
                    }
                }
            }

            // ── Add Comment ────────────────────────────────────────────────
            item {
                DetailSection("💬 AFEGIR COMENTARI") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value         = username,
                            onValueChange = { username = it },
                            placeholder   = { Text("El teu nom", color = Color(0xFF546E7A)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor   = Color(0xFFFFD700),
                                unfocusedBorderColor = Color(0xFF37474F),
                                focusedTextColor     = Color.White,
                                unfocusedTextColor   = Color.White,
                                cursorColor          = Color(0xFFFFD700)
                            ),
                            shape    = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                        )
                        OutlinedTextField(
                            value         = commentText,
                            onValueChange = { commentText = it },
                            placeholder   = { Text("Escriu el teu comentari...", color = Color(0xFF546E7A)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor   = Color(0xFFFFD700),
                                unfocusedBorderColor = Color(0xFF37474F),
                                focusedTextColor     = Color.White,
                                unfocusedTextColor   = Color.White,
                                cursorColor          = Color(0xFFFFD700)
                            ),
                            shape    = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                            keyboardActions = KeyboardActions(onSend = {
                                if (username.isNotBlank() && commentText.isNotBlank()) {
                                    viewModel.addComment(card.id, username, commentText)
                                }
                            })
                        )
                        Button(
                            onClick = {
                                if (username.isNotBlank() && commentText.isNotBlank()) {
                                    viewModel.addComment(card.id, username, commentText)
                                }
                            },
                            enabled  = username.isNotBlank() && commentText.isNotBlank(),
                            modifier = Modifier.align(Alignment.End),
                            colors   = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFD700),
                                disabledContainerColor = Color(0xFF37474F)
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Send, null,
                                tint = Color.Black,
                                modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("ENVIAR", color = Color.Black, fontWeight = FontWeight.Black, fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}