package com.lamti.sportsnews.di

import android.content.Context
import androidx.room.Room
import com.lamti.sportsnews.data.local.FootballDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val FOOTBALL_DATABASE = "football_database"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): FootballDatabase {
        return Room.databaseBuilder(
            context,
            FootballDatabase::class.java,
            FOOTBALL_DATABASE
        ).build()
    }

}
