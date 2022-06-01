package com.lamti.sportsnews.presentation.screens

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DetailsScreen(teamID: Int) {
    Scaffold(
        content = {
            Text(text = "ID: $teamID")
        }
    )
}
