package com.lamti.sportsnews.data.remoteApi

import com.lamti.sportsnews.data.models.PlayersResponse
import com.lamti.sportsnews.data.networkResult.ApiResult

class PlayersRemoteDataSource(private val footballApi: FootballApi) {

    suspend operator fun invoke(season: Int, teamID: Int): ApiResult<PlayersResponse> =
        footballApi.getPlayers(season, teamID)
}
