package com.lamti.sportsnews.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lamti.sportsnews.data.local.models.LocalPlayer

@Dao
interface PlayerDao {

    @Query("SELECT * FROM players_table WHERE teamID LIKE :searchID")
    fun getAllPlayers(searchID: Int): PagingSource<Int, LocalPlayer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlayers(players: List<LocalPlayer>)

    @Query("DELETE FROM players_table")
    suspend fun deleteAllPlayers()

}
