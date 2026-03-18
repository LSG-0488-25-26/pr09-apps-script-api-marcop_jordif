package com.example.dokkanapi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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

@Composable
fun TypeBadge(type: String, color: Color, large: Boolean = false) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = 0.2f))
            .border(1.dp, color.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
            .padding(
                horizontal = if (large) 10.dp else 6.dp,
                vertical   = if (large) 4.dp  else 2.dp
            )
    ) {
        Text(
            type,
            color = color,
            fontSize = if (large) 13.sp else 9.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
        )
    }
}

@Composable
fun RarityBadge(rarity: String, color: Color, large: Boolean = false) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = 0.15f))
            .border(1.dp, color.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
            .padding(
                horizontal = if (large) 10.dp else 6.dp,
                vertical   = if (large) 4.dp  else 2.dp
            )
    ) {
        Text(
            rarity,
            color = color,
            fontSize = if (large) 13.sp else 9.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
fun MiniStat(label: String, value: Int, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color(0xFF546E7A), fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Text("$value", color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun BigStat(label: String, value: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("$value", color = color, fontSize = 26.sp, fontWeight = FontWeight.Black)
        Text(label, color = Color(0xFF78909C), fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun DetailSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E)),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                title,
                color = Color(0xFF78909C),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            content()
        }
    }
}

@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = Color(0xFFFFD700))
            Spacer(Modifier.height(12.dp))
            Text("Carregant cartes...", color = Color(0xFF78909C))
        }
    }
}

@Composable
fun ErrorView(error: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text("⚠️", fontSize = 48.sp)
            Spacer(Modifier.height(8.dp))
            Text("Error", color = Color(0xFFEF5350), fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(error, color = Color(0xFF78909C), textAlign = TextAlign.Center, fontSize = 13.sp)
        }
    }
}

@Composable
fun EmptyView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Cap carta trobada", color = Color(0xFF546E7A), fontSize = 16.sp)
    }
}