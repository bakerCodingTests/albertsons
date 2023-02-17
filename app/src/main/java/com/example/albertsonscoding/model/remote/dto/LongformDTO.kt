package com.example.albertsonscoding.model.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LongformDTO(
    val lf: String,
    val freq: Int,
    val since: Int,
    val vars: List<VariationDTO>
)