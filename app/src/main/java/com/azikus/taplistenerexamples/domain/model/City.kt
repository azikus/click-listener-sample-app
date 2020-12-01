package com.azikus.taplistenerexamples.domain.model

data class City(
     val name: String,
     val date: String,
     val fips: Long?,
     val latitude: String?,
     val longitude: String?,
     val confirmed: Int,
     val deaths: Int,
     val confirmedDiff: Int,
     val deathsDiff: Int,
     val lastUpdate: String
)
