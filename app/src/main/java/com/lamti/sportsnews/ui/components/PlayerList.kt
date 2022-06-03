package com.lamti.sportsnews.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.lamti.sportsnews.data.local.models.LocalPlayer
import com.lamti.sportsnews.data.local.models.LocalPlayer.Companion.toData
import com.lamti.sportsnews.presentation.models.PlayerData

@Composable
fun PlayerList(items: LazyPagingItems<LocalPlayer>, onItemClicked: (Int) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = "Players: ",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    ),
                )
            }
            items(
                items = items,
                key = { it.id }
            ) { player ->
                player?.let {
                    PlayerItem(player = it.toData(), onClick = { onItemClicked(it.id) })
                }
            }
        }
    }
}
