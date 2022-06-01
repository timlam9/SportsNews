package com.lamti.sportsnews.data.remoteApi

import com.lamti.sportsnews.data.models.team.TeamResponse
import com.lamti.sportsnews.data.networkResult.ApiResult

class TeamsRemoteDataSource(private val footballApi: FootballApi) {

    suspend operator fun invoke(country: String): ApiResult<TeamResponse> = footballApi.getTeams(country)
}
