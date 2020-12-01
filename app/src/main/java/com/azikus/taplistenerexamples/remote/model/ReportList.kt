package com.azikus.taplistenerexamples.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportList(
    @SerialName("date") var date: String,
    @SerialName("confirmed") var confirmed: Long,
    @SerialName("deaths") var deaths: Long,
    @SerialName("recovered") var recovered: Long,
    @SerialName("confirmed_diff") var confirmedDiff: Long,
    @SerialName("deaths_diff") var deathsDiff: Long,
    @SerialName("recovered_diff") var recoveredDiff: Long,
    @SerialName("last_update") var lastUpdate: String,
    @SerialName("active") var active: Long,
    @SerialName("active_diff") var activeDiff: Long,
    @SerialName("fatality_rate") var fatalityRate: Double,
    @SerialName("region") var region: AdditionRegion
)
