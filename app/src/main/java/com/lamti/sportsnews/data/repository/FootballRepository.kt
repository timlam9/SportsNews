package com.lamti.sportsnews.data.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.lamti.sportsnews.data.local.FootballDatabase
import com.lamti.sportsnews.data.local.models.LocalPlayer
import com.lamti.sportsnews.data.models.team.Response.Companion.toData
import com.lamti.sportsnews.data.networkResult.onError
import com.lamti.sportsnews.data.networkResult.onException
import com.lamti.sportsnews.data.networkResult.onSuccess
import com.lamti.sportsnews.data.paging.FootballRemoteMediator
import com.lamti.sportsnews.data.remoteApi.FootballApi
import com.lamti.sportsnews.data.remoteApi.TeamsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FootballRepository(
    private val teamsRemoteDataSource: TeamsRemoteDataSource,
    private val footballApi: FootballApi,
    private val footballDatabase: FootballDatabase
) {

    fun getPlayers(season: Int, teamID: Int): Flow<PagingData<LocalPlayer>> {
        val pagingSourceFactory: () -> PagingSource<Int, LocalPlayer> = { footballDatabase.playerDao().getAllPlayers(teamID) }
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = FootballRemoteMediator(
                footballApi = footballApi,
                footballDatabase = footballDatabase,
                season = season,
                teamID = teamID
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun getTeams(country: String): Flow<Result> = flow {
        emit(Result(data = emptyList(), errorMessage = null, loading = true))

        teamsRemoteDataSource(country)
            .onSuccess { response ->
                emit(Result(data = response.response.map { it.toData() }, errorMessage = null, loading = false))
            }.onError { code, message ->
                emit(Result(data = emptyList(), errorMessage = "$code, $message", loading = false))
            }.onException {
                emit(Result(data = emptyList(), errorMessage = it.message, loading = false))
            }
    }
}

