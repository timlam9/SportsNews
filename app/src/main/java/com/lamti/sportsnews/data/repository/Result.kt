package com.lamti.sportsnews.data.repository

data class Result(
    val data: List<Any> = emptyList(),
    val errorMessage: String? = null,
    val loading: Boolean = false
)
