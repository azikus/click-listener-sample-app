package com.azikus.taplistenerexamples.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Province(
    @SerialName("iso") val iso: String,
    @SerialName("name") val name: String,
    @SerialName("province") val province: String,
    @SerialName("lat") val latitude: String?,
    @SerialName("long") val longitude: String?
)
