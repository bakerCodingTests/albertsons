package com.example.albertsonscoding.model.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class VariationDTO(
    val lf: String,
    val freq: Int,
    val since: Int
)