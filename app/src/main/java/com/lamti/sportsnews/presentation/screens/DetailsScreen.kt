package com.lamti.sportsnews.presentation.screens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.lamti.sportsnews.presentation.DetailsViewModel
import com.lamti.sportsnews.ui.components.PlayerList

@Composable
fun DetailsScreen(teamID: Int, viewModel: DetailsViewModel = hiltViewModel()) {
    LaunchedEffect(key1 = teamID) { viewModel.setTeamID(teamID) }
    val list = viewModel.players.collectAsLazyPagingItems()

    Scaffold {
        PlayerList(items = list) {

        }
    }

}
