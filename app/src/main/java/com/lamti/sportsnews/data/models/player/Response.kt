package com.lamti.sportsnews.data.models.player

import com.lamti.sportsnews.presentation.models.PlayerData

data class Response(
    val player: Player,
    val statistics: List<Statistic>
) {

    companion object {

        fun Response.toData() = PlayerData(
            id = player.id,
            name = player.name ?: "null",
            photo = player.photo ?: "null",
            age = player.age ?: 0
        )
    }
}
