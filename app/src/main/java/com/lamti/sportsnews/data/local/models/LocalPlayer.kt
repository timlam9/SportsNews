package com.lamti.sportsnews.data.local.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lamti.sportsnews.presentation.models.PlayerData

private const val PLAYERS_TABLE = "players_table"

@Entity(tableName = PLAYERS_TABLE)
data class LocalPlayer(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val teamID: Int,
    val age: Int?,
    @Embedded
    val birth: LocalBirth?,
    val firstname: String?,
    val height: String?,
    val injured: Boolean?,
    val lastname: String?,
    val name: String?,
    val nationality: String?,
    val photo: String?,
    val weight: String?
) {

    companion object {

        fun LocalPlayer.toData() = PlayerData(
            id = id,
            name = name ?: "null",
            photo = photo ?: "null",
            age = age ?: 0
        )
    }
}
