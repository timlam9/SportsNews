package com.lamti.sportsnews.data.models.player

import com.lamti.sportsnews.data.local.models.LocalBirth

data class Birth(
    val country: String?,
    val date: String?,
    val place: String?
) {

    companion object {

        fun Birth.toLocalBirth() = LocalBirth(
            country = country,
            date = date,
            place = place
        )
    }
}
