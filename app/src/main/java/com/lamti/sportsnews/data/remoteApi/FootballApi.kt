package com.lamti.sportsnews.data.remoteApi

import com.lamti.sportsnews.data.models.PlayersResponse
import com.lamti.sportsnews.data.networkResult.ApiResult
import retrofit2.http.GET
import retrofit2.http.Query

interface FootballApi {

    @GET("players")
    suspend fun getPlayers(@Query("season") season: Int, @Query("team") teamID: Int): ApiResult<PlayersResponse>
}

