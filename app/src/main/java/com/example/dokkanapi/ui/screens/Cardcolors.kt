package com.example.dokkanapi.ui.screens

import androidx.compose.ui.graphics.Color

private const val BASE_URL = "https://script.google.com/macros/s/AKfycbxLAvVdpj-bsPdg5VtYxPX0bS5CuygQOcp2Tc81SfE4ddag9smXF8WBAGcQXssYsESHsg/"

fun typeColor(type: String): Color = when (type.uppercase()) {
    "STR" -> Color(0xFFE53935)
    "AGL" -> Color(0xFF42A5F5)
    "TEQ" -> Color(0xFF66BB6A)
    "INT" -> Color(0xFF7E57C2)
    "PHY" -> Color(0xFFFF8F00)
    else  -> Color(0xFF78909C)
}

fun rarityColor(rarity: String): Color = when (rarity.uppercase()) {
    "LR"  -> Color(0xFFFFD700)
    "UR"  -> Color(0xFFFF6F00)
    "SSR" -> Color(0xFFE040FB)
    "SR"  -> Color(0xFF29B6F6)
    "R"   -> Color(0xFF66BB6A)
    else  -> Color(0xFF78909C)
}