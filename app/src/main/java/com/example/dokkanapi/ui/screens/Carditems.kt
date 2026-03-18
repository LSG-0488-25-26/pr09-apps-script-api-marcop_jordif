package com.example.dokkanapi.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dokkanapi.data.model.Card

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardGridItem(card: Card, onClick: () -> Unit) {
    val typeCol   = typeColor(card.type)
    val rarityCol = rarityColor(card.rarity)

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.72f),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E)),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, typeCol.copy(alpha = 0.3f))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Gradient de fons
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .align(Alignment.TopCenter)
                    .background(
                        Brush.verticalGradient(
                            listOf(typeCol.copy(alpha = 0.25f), Color.Transparent)
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    TypeBadge(card.type, typeCol)
                    RarityBadge(card.rarity, rarityCol)
                }
                Column {
                    Text(
                        card.name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 16.sp
                    )
                    Text(
                        card.title,
                        color = Color(0xFF78909C),
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    MiniStat("HP",  card.hp,      Color(0xFF66BB6A))
                    MiniStat("ATK", card.attack,  Color(0xFFEF5350))
                    MiniStat("DEF", card.defense, Color(0xFF42A5F5))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListItem(card: Card, onClick: () -> Unit) {
    val typeCol = typeColor(card.type)

    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, typeCol.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(typeCol)
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(card.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(
                    card.title,
                    color = Color(0xFF78909C),
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    TypeBadge(card.type, typeCol)
                    RarityBadge(card.rarity, rarityColor(card.rarity))
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("HP ${card.hp}",       color = Color(0xFF66BB6A), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text("ATK ${card.attack}",  color = Color(0xFFEF5350), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text("DEF ${card.defense}", color = Color(0xFF42A5F5), fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}