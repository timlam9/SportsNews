package com.lamti.sportsnews.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lamti.sportsnews.data.local.models.FootballRemoteKeys

@Dao
interface FootballRemoteKeysDao {

    @Query("SELECT * FROM football_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): FootballRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<FootballRemoteKeys>)

    @Query("DELETE FROM football_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

}
