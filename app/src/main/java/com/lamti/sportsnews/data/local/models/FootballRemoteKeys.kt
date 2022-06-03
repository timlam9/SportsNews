package com.lamti.sportsnews.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

private const val FOOTBALL_REMOTE_KEYS_TABLE = "football_remote_keys_table"

@Entity(tableName = FOOTBALL_REMOTE_KEYS_TABLE)
data class FootballRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)
