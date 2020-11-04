package com.azikus.clicklistenerexamples.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    @SerialName("data") var data: T
)
