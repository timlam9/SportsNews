package com.lamti.sportsnews.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lamti.sportsnews.presentation.PlayersViewModel

@Composable
fun PlayersList(playersViewModel: PlayersViewModel = viewModel()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AnimatedVisibility(playersViewModel.uiState.loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .wrapContentSize()
            )
        }
        AnimatedVisibility(!playersViewModel.uiState.loading) {
            val list = remember { playersViewModel.uiState.data }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(items = list) { item ->
                    PlayerItem(player = item) {}
                }
            }
        }
    }
}
