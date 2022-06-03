package com.lamti.sportsnews.data.models.player

import com.lamti.sportsnews.data.local.models.LocalPlayer
import com.lamti.sportsnews.data.models.player.Birth.Companion.toLocalBirth

data class Player(
    val age: Int?,
    val birth: Birth?,
    val firstname: String?,
    val height: String?,
    val id: Int,
    val injured: Boolean?,
    val lastname: String?,
    val name: String?,
    val nationality: String?,
    val photo: String?,
    val weight: String?
) {

    companion object {

        fun Player.toLocalPlayer(teamID: Int) = LocalPlayer(
            id = id,
            teamID = teamID,
            age = age,
            birth = birth?.toLocalBirth(),
            firstname = firstname,
            height = height,
            injured = injured,
            lastname = lastname,
            name = name,
            nationality = nationality,
            photo = photo,
            weight = weight
        )
    }
}
