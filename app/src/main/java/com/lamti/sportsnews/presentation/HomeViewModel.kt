package com.lamti.sportsnews.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.imageloading.ImageLoadState
import com.lamti.sportsnews.data.repository.FootballRepository
import com.lamti.sportsnews.presentation.models.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: FootballRepository) : ViewModel() {

    var uiState: UiState by mutableStateOf(UiState())
        private set

    init {
        getTeams()
    }

    private fun getTeams() {
        viewModelScope.launch {
            repo.getTeams("Greece").onEach {
                uiState = uiState.copy(
                    data = it.data,
                    errorMessage = it.errorMessage,
                    loading = it.loading
                )
            }.launchIn(viewModelScope)
        }
    }
}
