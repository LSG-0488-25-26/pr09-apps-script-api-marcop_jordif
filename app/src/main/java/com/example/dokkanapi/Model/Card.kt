package com.tuapp.dokkanbattle.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val id: String,
    val title: String,
    val name: String,
    val type: String,        // STR, AGL, TEQ, INT, PHY
    val rarity: String,       // LR, UR, SSR, SR, R, N
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val cost: Int,
    val leaderSkill: String,
    val superAttack: String,
    val superAttackMultiplier: String? = null,
    val passiveSkill: String,
    val linkSkills: List<String> = emptyList(),
    val categories: List<String> = emptyList(),
    val jpRelease: String? = null,
    val globalRelease: String? = null,
    val imageUrl: String? = null,
    val isFavorite: Boolean = false
)