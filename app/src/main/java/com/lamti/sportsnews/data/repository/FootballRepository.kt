package com.lamti.sportsnews.data.repository

import com.lamti.sportsnews.data.models.player.Response.Companion.toData
import com.lamti.sportsnews.data.models.team.Response.Companion.toData
import com.lamti.sportsnews.data.networkResult.onError
import com.lamti.sportsnews.data.networkResult.onException
import com.lamti.sportsnews.data.networkResult.onSuccess
import com.lamti.sportsnews.data.remoteApi.PlayersRemoteDataSource
import com.lamti.sportsnews.data.remoteApi.TeamsRemoteDataSource
import com.lamti.sportsnews.presentation.models.UiState
import kotlinx.coroutines.flow.flow

class FootballRepository(
    private val playersRemoteDataSource: PlayersRemoteDataSource,
    private val teamsRemoteDataSource: TeamsRemoteDataSource
) {

    suspend fun getPlayers(season: Int, teamID: Int) = flow {
        emit(UiState(data = emptyList(), errorMessage = null, loading = true))

        playersRemoteDataSource(season, teamID)
            .onSuccess { response ->
                emit(UiState(data = response.response.map { it.toData() }, errorMessage = null, loading = false))
            }.onError { code, message ->
                emit(UiState(data = emptyList(), errorMessage = "$code, $message", loading = false))
            }.onException {
                emit(UiState(data = emptyList(), errorMessage = it.message, loading = false))
            }
    }

    suspend fun getTeams(country: String) = flow {
        emit(UiState(data = emptyList(), errorMessage = null, loading = true))

        teamsRemoteDataSource(country)
            .onSuccess { response ->
                emit(UiState(data = response.response.map { it.toData() }, errorMessage = null, loading = false))
            }.onError { code, message ->
                emit(UiState(data = emptyList(), errorMessage = "$code, $message", loading = false))
            }.onException {
                emit(UiState(data = emptyList(), errorMessage = it.message, loading = false))
            }
    }
}

