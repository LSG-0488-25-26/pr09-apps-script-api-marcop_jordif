package com.example.dokkanpi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dokkanapi.data.model.Card
import com.example.dokkanapi.ui.screens.rarityColor
import com.example.dokkanapi.ui.screens.typeColor

@Composable
fun StatsTab(cards: List<Card>) {
    val typeCount   = cards.groupBy { it.type.uppercase() }.mapValues { it.value.size }.toSortedMap()
    val rarityCount = cards.groupBy { it.rarity.uppercase() }.mapValues { it.value.size }.toSortedMap()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "ESTADÍSTIQUES",
                color = Color(0xFFFFD700),
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp,
                fontSize = 13.sp
            )
        }

        item {
            StatCard(title = "TOTAL CARTES") {
                Text(
                    "${cards.size}",
                    color = Color(0xFFFFD700),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }

        item {
            StatCard(title = "PER TIPUS") {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("STR", "AGL", "TEQ", "INT", "PHY").forEach { type ->
                        val count = typeCount[type] ?: 0
                        val max   = (typeCount.values.maxOrNull() ?: 1).toFloat()
                        StatBar(label = type, value = count, fraction = count / max, color = typeColor(type))
                    }
                }
            }
        }

        item {
            StatCard(title = "PER RARESA") {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("LR", "UR", "SSR", "SR", "R", "N").forEach { rarity ->
                        val count = rarityCount[rarity] ?: 0
                        val max   = (rarityCount.values.maxOrNull() ?: 1).toFloat()
                        StatBar(label = rarity, value = count, fraction = count / max, color = rarityColor(rarity))
                    }
                }
            }
        }

        if (cards.isNotEmpty()) {
            item {
                StatCard(title = "TOP CARTES") {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        cards.maxByOrNull { it.hp }?.let      { TopStatRow("❤ HP màxim",  it.name, it.hp.toString()) }
                        cards.maxByOrNull { it.attack }?.let  { TopStatRow("⚔ ATK màxim", it.name, it.attack.toString()) }
                        cards.maxByOrNull { it.defense }?.let { TopStatRow("🛡 DEF màxim", it.name, it.defense.toString()) }
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                title,
                color = Color(0xFF78909C),
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                letterSpacing = 1.5.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            content()
        }
    }
}

@Composable
fun StatBar(label: String, value: Int, fraction: Float, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            label,
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            modifier = Modifier.width(40.dp)
        )
        Spacer(Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .height(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF0D0D1A))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction.coerceIn(0f, 1f))
                    .clip(RoundedCornerShape(8.dp))
                    .background(color)
            )
        }
        Spacer(Modifier.width(8.dp))
        Text(
            "$value",
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier.width(30.dp),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun TopStatRow(label: String, name: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(label, color = Color(0xFF78909C), fontSize = 11.sp)
            Text(name,  color = Color.White,       fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }
        Text(value, color = Color(0xFFFFD700), fontSize = 18.sp, fontWeight = FontWeight.Black)
    }
}