package com.lamti.sportsnews.presentation.models

data class UiState(
    val data: List<PlayerData> = emptyList(),
    val errorMessage: String? = null,
    val loading: Boolean = false
)
