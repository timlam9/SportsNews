package com.lamti.sportsnews.presentation

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.lamti.sportsnews.data.local.models.LocalPlayer
import com.lamti.sportsnews.data.repository.FootballRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repo: FootballRepository) : ViewModel() {

    private val teamID = MutableStateFlow(0)

    val players: Flow<PagingData<LocalPlayer>> = teamID.flatMapLatest { repo.getPlayers(2021, it) }

    fun setTeamID(id: Int) {
        teamID.value = id
    }
}
