package com.tuapp.dokkanbattle.data.model

enum class CardRarity(val level: Int, val maxLevel: Int) {
    N(1, 20),
    R(2, 40),
    SR(3, 60),
    SSR(4, 80),
    UR(5, 100),
    TUR(6, 120),
    LR(7, 150),
    UNKNOWN(0, 0);

    companion object {
        fun fromString(rarity: String): CardRarity {
            return when (rarity.uppercase()) {
                "N" -> N
                "R" -> R
                "SR" -> SR
                "SSR" -> SSR
                "UR" -> UR
                "TUR" -> TUR
                "LR" -> LR
                else -> UNKNOWN
            }
        }
    }
}