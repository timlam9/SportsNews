package com.lamti.sportsnews.data.repository

import com.lamti.sportsnews.data.models.Response.Companion.toData
import com.lamti.sportsnews.data.networkResult.onError
import com.lamti.sportsnews.data.networkResult.onException
import com.lamti.sportsnews.data.networkResult.onSuccess
import com.lamti.sportsnews.data.remoteApi.PlayersRemoteDataSource
import com.lamti.sportsnews.presentation.models.UiState
import kotlinx.coroutines.flow.flow

class PlayersRepository(private val playersRemoteDataSource: PlayersRemoteDataSource) {

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
}

