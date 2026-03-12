package com.tuapp.dokkanbattle.data.model

enum class CardType(val displayName: String, val color: String) {
    STR("STR", "Rojo"),
    AGL("AGL", "Azul"),
    TEQ("TEQ", "Verde"),
    INT("INT", "Violeta"),
    PHY("PHY", "Amarillo"),
    UNKNOWN("Desconocido", "Gris");

    companion object {
        fun fromString(type: String): CardType {
            return values().find { it.displayName.equals(type, ignoreCase = true) } ?: UNKNOWN
        }
    }
}