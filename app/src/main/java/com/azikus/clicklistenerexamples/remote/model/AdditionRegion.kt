package com.azikus.clicklistenerexamples.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdditionRegion(
    @SerialName("iso") val iso: String,
    @SerialName("name") val name: String,
    @SerialName("province") val province: String,
    @SerialName("lat") val latitude: String?,
    @SerialName("long") val longitude: String?,
    @SerialName("cities") val cities: List<City>
)
