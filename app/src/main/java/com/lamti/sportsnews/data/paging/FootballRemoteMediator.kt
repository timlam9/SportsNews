package com.lamti.sportsnews.data.paging

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.lamti.sportsnews.data.local.FootballDatabase
import com.lamti.sportsnews.data.local.models.FootballRemoteKeys
import com.lamti.sportsnews.data.local.models.LocalPlayer
import com.lamti.sportsnews.data.models.player.Player.Companion.toLocalPlayer
import com.lamti.sportsnews.data.models.player.Response
import com.lamti.sportsnews.data.networkResult.ApiError
import com.lamti.sportsnews.data.networkResult.ApiException
import com.lamti.sportsnews.data.networkResult.ApiSuccess
import com.lamti.sportsnews.data.remoteApi.FootballApi

class FootballRemoteMediator(
    private val footballApi: FootballApi,
    private val footballDatabase: FootballDatabase,
    private val teamID: Int,
    private val season: Int,
) : RemoteMediator<Int, LocalPlayer>() {

    private val playerDao = footballDatabase.playerDao()
    private val remoteKeysDao = footballDatabase.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalPlayer>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }
            when (
                val response = footballApi.getPlayers(
                    season = season,
                    teamID = teamID,
                    page = currentPage
                )
            ) {
                is ApiError -> {
                    Log.e("TAGARA", "${response.code}, ${response.message}")
                    return MediatorResult.Error(throwable = NetworkErrorException())
                }
                is ApiException -> {
                    Log.e("TAGARA", "${response.e.message}")
                    return MediatorResult.Error(response.e)
                }
                is ApiSuccess -> {
                    val playersResponses: List<Response> = response.data.response
                    val endOfPaginationReached = playersResponses.isEmpty()
                    val prevPage = if (currentPage == 1) null else currentPage - 1
                    val nextPage = if (endOfPaginationReached) null else currentPage + 1

                    footballDatabase.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            playerDao.deleteAllPlayers()
                            remoteKeysDao.deleteAllRemoteKeys()
                        }
                        val keys = playersResponses.map { playerResponse ->
                            FootballRemoteKeys(
                                id = playerResponse.player.id,
                                prevPage = prevPage,
                                nextPage = nextPage
                            )
                        }
                        remoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                        playerDao.addPlayers(
                            players = playersResponses.map {
                                it.player.toLocalPlayer(teamID)
                            }
                        )
                    }
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
            }
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, LocalPlayer>
    ): FootballRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, LocalPlayer>
    ): FootballRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                remoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, LocalPlayer>
    ): FootballRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                remoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

}
