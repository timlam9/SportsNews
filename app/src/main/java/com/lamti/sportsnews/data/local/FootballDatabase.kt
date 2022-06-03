package com.lamti.sportsnews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lamti.sportsnews.data.local.dao.FootballRemoteKeysDao
import com.lamti.sportsnews.data.local.dao.PlayerDao
import com.lamti.sportsnews.data.local.models.FootballRemoteKeys
import com.lamti.sportsnews.data.local.models.LocalPlayer

@Database(entities = [LocalPlayer::class, FootballRemoteKeys::class], version = 1)
abstract class FootballDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao
    abstract fun remoteKeysDao(): FootballRemoteKeysDao

}
