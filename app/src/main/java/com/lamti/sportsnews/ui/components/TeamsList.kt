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
import com.lamti.sportsnews.presentation.HomeViewModel
import com.lamti.sportsnews.presentation.models.TeamData

@Composable
fun TeamsList(viewModel: HomeViewModel, onItemClicked: (Int) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AnimatedVisibility(viewModel.uiState.loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .wrapContentSize()
            )
        }
        AnimatedVisibility(!viewModel.uiState.loading && !viewModel.uiState.errorMessage.isNullOrEmpty()) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Something went wrong"
            )
        }
        AnimatedVisibility(!viewModel.uiState.loading && viewModel.uiState.data.isNotEmpty()) {
            val list = remember { viewModel.uiState.data }
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
