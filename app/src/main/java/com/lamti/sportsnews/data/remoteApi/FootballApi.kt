package com.lamti.sportsnews.data.remoteApi

import com.lamti.sportsnews.data.models.player.ApiResponse
import com.lamti.sportsnews.data.models.team.TeamResponse
import com.lamti.sportsnews.data.networkResult.ApiResult
import retrofit2.http.GET
import retrofit2.http.Query

interface FootballApi {

    @GET("players")
    suspend fun getPlayers(
        @Query("season") season: Int,
        @Query("team") teamID: Int,
        @Query("page") page: Int
    ): ApiResult<ApiResponse>

    @GET("teams")
    suspend fun getTeams(@Query("country") country: String): ApiResult<TeamResponse>
}

