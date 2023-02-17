package com.example.albertsonscoding.model.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO(
    val sf: String,
    val lfs: List<LongformDTO>
)