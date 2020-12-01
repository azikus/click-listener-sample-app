package com.azikus.taplistenerexamples.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class City(
    @SerialName("name") val name: String,
    @SerialName("date") val date: String,
    @SerialName("fips") val fips: Long?,
    @SerialName("lat") val latitude: String?,
    @SerialName("long") val longitude: String?,
    @SerialName("confirmed") val confirmed: Int,
    @SerialName("deaths") val deaths: Int,
    @SerialName("confirmed_diff") val confirmedDiff: Int,
    @SerialName("deaths_diff") val deathsDiff: Int,
    @SerialName("last_update") val lastUpdate: String
)
