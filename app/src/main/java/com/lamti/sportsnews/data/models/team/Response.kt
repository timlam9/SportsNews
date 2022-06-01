package com.lamti.sportsnews.data.models.team

import com.lamti.sportsnews.presentation.models.TeamData

data class Response(
    val team: Team,
    val venue: Venue?
) {

    companion object {

        fun Response.toData() = TeamData(
            name = team.name,
            logo = team.logo,
            stadium = venue?.name ?: "no stadium",
            stadiumPhoto = venue?.image ?: team.logo,
        )
    }
}
