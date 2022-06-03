package com.lamti.sportsnews.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lamti.sportsnews.data.models.player.Player
import com.lamti.sportsnews.data.networkResult.ApiError
import com.lamti.sportsnews.data.networkResult.ApiException
import com.lamti.sportsnews.data.networkResult.ApiSuccess
import com.lamti.sportsnews.data.remoteApi.FootballApi

class PlayersPagingSource(
    private val footballApi: FootballApi,
    private val season: Int,
    private val teamID: Int,
) : PagingSource<Int, Player>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Player> {
        val currentPage = params.key ?: 1
        return try {
            when (val response = footballApi.getPlayers(season = season, teamID = teamID, page = currentPage)) {
                is ApiError -> TODO()
                is ApiException -> TODO()
                is ApiSuccess -> {
                    val endOfPaginationReached = response.data.response.isEmpty()
                    if (response.data.response.isNotEmpty()) {
                        LoadResult.Page(
                            data = response.data.response.map { it.player },
                            prevKey = if (currentPage == 1) null else currentPage - 1,
                            nextKey = if (endOfPaginationReached) null else currentPage + 1
                        )
                    } else {
                        LoadResult.Page(
                            data = emptyList(),
                            prevKey = null,
                            nextKey = null
                        )
                    }
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Player>): Int? {
        return state.anchorPosition
    }
}
