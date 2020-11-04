package com.azikus.clicklistenerexamples.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Region(
    @SerialName("iso") val iso: String?,
    @SerialName("name") val name: String?
)
