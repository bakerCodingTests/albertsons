package com.example.albertsonscoding.view.search

import androidx.annotation.StringRes
import com.example.albertsonscoding.R

data class SearchState(
    val isLoading: Boolean = false,
    val query: String = "",
    val longforms: List<String> = emptyList(),
    @StringRes val errorStringId: Int = R.string.generic_error,
    val showResultCount: Boolean = false,
    val showError: Boolean = false,
)
