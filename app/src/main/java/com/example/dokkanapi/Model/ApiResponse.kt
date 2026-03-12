package com.tuapp.dokkanbattle.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: String? = null,
    val count: Int? = null,
    val message: String? = null
)