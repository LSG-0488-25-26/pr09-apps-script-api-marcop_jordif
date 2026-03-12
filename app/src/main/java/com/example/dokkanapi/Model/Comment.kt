package com.tuapp.dokkanbattle.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id: String,
    val cardId: String,
    val user: String,
    val comment: String,
    val timestamp: String
)