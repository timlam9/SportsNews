package com.lamti.sportsnews.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lamti.sportsnews.presentation.TeamsViewModel
import com.lamti.sportsnews.presentation.models.TeamData

@Composable
fun TeamsList(teamsViewModel: TeamsViewModel = viewModel(), onItemClicked: (Int) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AnimatedVisibility(teamsViewModel.uiState.loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .wrapContentSize()
            )
        }
        AnimatedVisibility(!teamsViewModel.uiState.loading) {
            val list = remember { teamsViewModel.uiState.data }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Text(
                        modifier = Modifier.padding(20.dp),
                        text = "Greek Teams: ",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
                items(items = list) { item ->
                    when (item) {
                        is TeamData -> TeamItem(team = item, onClick = { onItemClicked(item.id) })
                        else -> Unit
                    }
                }
            }
        }
    }
}
