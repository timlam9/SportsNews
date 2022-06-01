package com.lamti.sportsnews.presentation.screens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.lamti.sportsnews.presentation.TeamsViewModel
import com.lamti.sportsnews.ui.components.TeamsList

@Composable
fun HomeScreen(teamsViewModel: TeamsViewModel = hiltViewModel(), onItemClicked: (Int) -> Unit) {
    Scaffold(
        content = {
            TeamsList(
                teamsViewModel = teamsViewModel,
                onItemClicked = onItemClicked
            )
        }
    )
}
