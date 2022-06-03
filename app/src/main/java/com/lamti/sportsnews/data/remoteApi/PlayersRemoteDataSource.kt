package com.lamti.sportsnews.data.remoteApi

import com.lamti.sportsnews.data.models.player.ApiResponse
import com.lamti.sportsnews.data.networkResult.ApiResult

class PlayersRemoteDataSource(private val footballApi: FootballApi) {

    suspend operator fun invoke(season: Int, teamID: Int): ApiResult<ApiResponse> =
        footballApi.getPlayers(season, teamID, 1)
}

