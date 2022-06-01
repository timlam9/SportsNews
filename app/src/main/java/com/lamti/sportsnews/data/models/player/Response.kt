package com.lamti.sportsnews.data.models.player

import com.lamti.sportsnews.presentation.models.PlayerData

data class Response(
    val player: Player,
    val statistics: List<Statistic>
) {

    companion object {

        fun Response.toData() = PlayerData(
            name = player.name,
            photo = player.photo,
            goals = statistics.firstOrNull()?.goals?.total ?: 0
        )
    }
}
